package nl.vindh.monadexample

import nl.vindh.monadexample.domain._

import scala.concurrent.{ExecutionContext, Future}
// TODO: use traverse
//import cats.implicits._

class ResumeService(frontendRepo: DeveloperRepository,
                    backendRepo: DeveloperRepository,
                    educationRepo: EducationRepository,
                    githubClient: GithubClient)(implicit ec: ExecutionContext) {
  def getResumes(req: ResumeRequest): Future[Seq[Resume]] =
    for {
      developers <- req.tpe match {
        case "FRONTEND" => frontendRepo.getDevelopers
        case "BACKEND" => backendRepo.getDevelopers
        case unknown => Future.failed(new Exception(s"Unknown developer type requested: $unknown"))
      }
      educations <- Future.sequence(developers.flatMap(_.education).distinct.map(eduId => educationRepo.getEducation(eduId).map((eduId, _)))).map(_.toMap)
      prSets <- Future.sequence(developers.map(_.githubHandle).map(githubClient.getPrsByName))
      resumes = for {
          (developer, prSet) <- developers.zip(prSets)
          education = developer.education.flatMap(educations.get)
        } yield Resume(
          developer.name,
          developer.age,
          developer.experience.toSeq.sortBy(_.startYear),
          education,
          prSet.toSeq
        )
    } yield resumes


}
