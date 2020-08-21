package nl.vindh.monadexample

import ResumeService._
import cats.{Id, ~>}
import nl.vindh.monadexample.domain._
import shapeless.tag
import nl.vindh.monadexample.domain.Generators

object ResumeServiceTest extends Generators {

  val business = tag[EducationIdTag][String]("business")
  val leisure = tag[EducationIdTag][String]("leisure")

  val educations = Map(
    business -> Education(
      tag[SchoolNameTag][String]("Some School"),
      tag[CourseTitleTag][String]("Business"),
      EducationLevel.Bachelor
    ),
    leisure -> Education(
      tag[SchoolNameTag][String]("Some School"),
      tag[CourseTitleTag][String]("Leisure"),
      EducationLevel.Master
    )
  )

  val backendDeveloper = Developer(
    tag[NameTag][String]("Mr. Educated"),
    tag[AgeTag][Int](12),
    tag[GithubHandleTag][String]("mr-educated"),
    Set.empty,
    Seq(business, leisure)
  )

  // "Mocking".
  // You'll only need to specify what's actually used in your class.
  def testInterpreter: ResumeAction ~> Id =
    new (ResumeAction ~> Id) {
      override def apply[A](fa: ResumeAction[A]): Id[A] =
        fa match {
          case GetFrontendDevelopers =>
            Seq.empty[Developer]
          case GetBackendDevelopers =>
            Seq(backendDeveloper)
          case GetEducation(educationId) =>
            educations(educationId)
          case GetProjectsByName(githubHandle) =>
            Set(tag[PullRequestTag][String]("#1"), tag[PullRequestTag][String]("#2"))
          case Failure(e) =>
            throw e
        }
    }

  def main(args: Array[String]): Unit = {
    println(ResumeService.getResumes(ResumeRequest("BACKEND", 4)).foldMap(testInterpreter))
  }
}
