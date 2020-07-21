package nl.vindh.monadexample

import akka.http.scaladsl.server.Directives._

class Api(resumeService: ResumeService) {
  val resumeRoute = get {
    path("resume") {
      // TODO: traceable logging
      // TODO: swagger generation
    }
  }
}
