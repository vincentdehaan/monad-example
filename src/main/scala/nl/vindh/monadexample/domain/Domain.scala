package nl.vindh.monadexample.domain

case class Developer(name: Name, age: Age, githubHandle: GithubHandle, experience: Set[Experience], education: Seq[EducationId])
case class Experience(startYear: Year, duration: Years, company: CompanyName, role: RoleName)
case class Education(school: SchoolName, course: CourseTitle, level: EducationLevel.Value)
object EducationLevel extends Enumeration {
  type EducationLevel = Value
  val NONE, Bachelor, Master = Value
}
case class Resume(name: Name, age: Age, experience: Seq[Experience], education: Seq[Education], prs: Seq[PullRequest])

case class ResumeRequest(
  tpe: String, /* "FRONTEND" or "BACKEND" */
  educationLevel: Int /* 0, 1, 2 */
)