package nl.vindh.monadexample.domain

import org.scalacheck.{Arbitrary, Gen}
import shapeless.tag

trait Generators {
  val genInt: Gen[Int] = implicitly[Arbitrary[Int]].arbitrary
  implicit val arbName: Arbitrary[Name] = Arbitrary(Gen.oneOf("Donald Trump", "Rijkman Groenink", "Donna Rotunno")
    .map(tag[NameTag][String]))
  implicit val arbAge: Arbitrary[Age] = Arbitrary(genInt.map(_ % 40 + 25).map(tag[AgeTag][Int]))
  implicit val arbYear: Arbitrary[Year] = Arbitrary(genInt.map(_ % 30 + 1990).map(tag[YearTag][Int]))
  implicit val arbYears: Arbitrary[Years] = Arbitrary(genInt.map(_ % 10).map(tag[YearsTag][Int]))
  implicit val arbCompanyName: Arbitrary[CompanyName] = Arbitrary(Gen.oneOf("Acme Inc.", "Mossack Fonseca", "Abercrombie & Fitch")
    .map(tag[CompanyNameTag][String]))
  implicit val arbSchoolName: Arbitrary[SchoolName] = Arbitrary(Gen.oneOf("University of Amsterdam", "Coursera", "Cambridge University")
    .map(tag[SchoolNameTag][String]))
  implicit val arbCourseTitle: Arbitrary[CourseTitle] = Arbitrary(Gen.oneOf("Functional programming in Scala", "The mystery method to seduction", "Brainfuck 101")
    .map(tag[CourseTitleTag][String]))
  implicit val arbRoleName: Arbitrary[RoleName] = Arbitrary(Gen.oneOf("CTO", "Developer", "Software architect")
    .map(tag[RoleNameTag][String]))
  implicit val arbEducationId: Arbitrary[EducationId] = Arbitrary(Gen.listOfN(10, genInt.map(i => (i % 10).abs)).map(_.mkString(""))
    .map(tag[EducationIdTag][String]))
  implicit val arbGithubHandle: Arbitrary[GithubHandle] = Arbitrary(Gen.oneOf("scroogemcduck", "foobar1998", "modersky")
    .map(tag[GithubHandleTag][String]))
  implicit val arbPullRequest: Arbitrary[PullRequest] =
    Arbitrary {
      for {
        repo <- Gen.oneOf("scala/scala", "akka/akka-http", "circe/circe", "scala/scala-swing", "tpolecat/atto")
        num <- genInt.map(_ % 200)
      } yield tag[PullRequestTag][String](s"github.com/$repo/pull/$num")
    }
}
