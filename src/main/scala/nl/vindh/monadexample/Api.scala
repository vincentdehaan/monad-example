package nl.vindh.monadexample

import akka.http.scaladsl.server.Directives._
import cats.implicits._
import cats.~>
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import nl.vindh.monadexample.ResumeService._
import nl.vindh.monadexample.domain.{Education, EducationId, ResumeRequest}
import io.circe.generic.auto._
import io.circe.shapes._

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

// Only here do we require these dependencies:
class Api(
  frontendRepo: DeveloperRepository,
  backendRepo: DeveloperRepository,
  educationRepo: EducationRepository,
  githubClient: GithubClient
)(implicit ec: ExecutionContext)
  extends FailFastCirceSupport {

  def interpreter(implicit traceId: String): ResumeAction ~> Future = {
    val cache = mutable.Map.empty[EducationId, Future[Education]]
    new (ResumeAction ~> Future) {
      override def apply[A](fa: ResumeAction[A]): Future[A] =
        fa match {
          case GetFrontendDevelopers =>
            frontendRepo.getDevelopers
          case GetBackendDevelopers =>
            backendRepo.getDevelopers
          case GetEducation(educationId) =>
            cache.get(educationId) match {
              case Some(existingRequest) =>
                existingRequest
              case None =>
                val request = educationRepo.getEducation(educationId)
                cache += (educationId -> request)
                request
            }

          case GetProjectsByName(githubHandle) =>
            githubClient.getPrsByName(githubHandle)
          case Failure(e) =>
            Future.failed(e)
        }
    }
  }

  val route = path("resume") {
    // TODO: openapi generation using Tapir
    headerValueByName("X-TraceId") { traceId =>
      {
        entity(as[ResumeRequest]) { req =>
          complete {
            ResumeService.getResumes(req).foldMap(interpreter(traceId))
          }
        }
      }
    }
  }
}
