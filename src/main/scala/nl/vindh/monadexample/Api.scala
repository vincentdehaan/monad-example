package nl.vindh.monadexample

import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import nl.vindh.monadexample.domain.ResumeRequest
import io.circe.generic.auto._
import io.circe.shapes._

class Api(resumeService: ResumeService) extends FailFastCirceSupport {
  val route = path("resume") {
    // TODO: openapi generation using Tapir
    headerValueByName("X-TraceId") {
      traceId => {
        entity(as[ResumeRequest]) {
          req =>
            complete {
              resumeService.getResumes(req)(traceId)
            }
        }
      }
    }
  }
}
