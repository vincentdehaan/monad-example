package nl.vindh.monadexample

import nl.vindh.monadexample.domain._
import org.scalacheck.Arbitrary

import scala.concurrent.{ExecutionContext, Future}

trait GithubClient {
  def getPrsByName(handle: GithubHandle): Future[Set[PullRequest]]
}

class RandomGithubClient(implicit ec: ExecutionContext) extends GithubClient with Generators {
  def getPrsByName(handle: GithubHandle): Future[Set[PullRequest]] = Future {
    implicitly[Arbitrary[Seq[PullRequest]]].arbitrary.sample.get
  }
}
