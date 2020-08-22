package nl.vindh.monadexample

import cats.free.Free
import cats.free.Free.liftF
import cats.implicits._
import nl.vindh.monadexample.domain._

object ResumeService {
  // All side effects we want to do, encoded as sealed trait:
  sealed trait ResumeAction[A]
  case object GetFrontendDevelopers extends ResumeAction[Seq[Developer]]
  case object GetBackendDevelopers extends ResumeAction[Seq[Developer]]
  case class GetEducation(educationId: EducationId) extends ResumeAction[Education]
  case class GetProjectsByName(githubHandle: GithubHandle) extends ResumeAction[Set[PullRequest]]
  case class Failure[A](e: Throwable) extends ResumeAction[A]

  // Type alias:
  type ResumeActionF[A] = Free[ResumeAction, A]

  // Boilerplate:
  def getFrontendDevelopers =
    liftF[ResumeAction, Seq[Developer]](GetFrontendDevelopers)

  def getBackendDevelopers =
    liftF[ResumeAction, Seq[Developer]](GetBackendDevelopers)

  def getEducation(educationId: EducationId) =
    liftF[ResumeAction, Education](GetEducation(educationId))

  def getProjectsByName(githubHandle: GithubHandle) =
    liftF[ResumeAction, Set[PullRequest]](GetProjectsByName(githubHandle))

  def failure[A](t: Throwable) =
    liftF[ResumeAction, A](Failure(t))

  def getResume(requiredEducation: Int)(developer: Developer): ResumeActionF[Option[Resume]] =
    for {
      education <- developer.education.toList.traverse(getEducation)
      prSet <- getProjectsByName(developer.githubHandle)
    } yield Option.when(education.exists(_.level.id >= requiredEducation)) {
      Resume(
        developer.name,
        developer.age,
        developer.experience.toSeq.sortBy(_.startYear),
        education,
        prSet.toSeq
      )
    }

  // No more dependencies, no more implicit ExecutionContext
  // No more implicit trace id:
  def getResumes(req: ResumeRequest): ResumeActionF[Seq[Resume]] =
    for {
      developers <- req.tpe match {
        case "FRONTEND" => getFrontendDevelopers.map(_.toList)
        case "BACKEND"  => getBackendDevelopers.map(_.toList)
        case unknown    => failure[List[Developer]](new Exception(s"Unknown developer type requested: $unknown"))
      }
      resumes <- developers.traverse(getResume(req.educationLevel))
    } yield resumes.flatten

}
