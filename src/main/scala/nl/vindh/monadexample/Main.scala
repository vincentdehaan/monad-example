package nl.vindh.monadexample

import akka.actor.ActorSystem
import akka.http.scaladsl.Http

import scala.concurrent.ExecutionContext

object Main extends App {
  implicit val system: ActorSystem = ActorSystem("monad-system")
  implicit val executionContext: ExecutionContext = system.dispatcher

  val frontendRepo = new RandomDeveloperRepository
  val backendRepo = new RandomDeveloperRepository
  val educationRepo = new RandomEducationRepository
  val githubClient = new RandomGithubClient

  val resumeService = new ResumeService(frontendRepo, backendRepo, educationRepo, githubClient)

  val api = new Api(resumeService)

  val bindingFuture = Http().bindAndHandle(api.route, "localhost", 8080)
  println(s"Server online at http://localhost:8080/")

}
