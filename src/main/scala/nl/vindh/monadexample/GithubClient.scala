package nl.vindh.monadexample

import nl.vindh.monadexample.domain._
import org.scalacheck.Arbitrary

import scala.concurrent.{ExecutionContext, Future}

trait GithubClient {
  def getPrsByName(handle: GithubHandle)(implicit traceId: String): Future[Set[PullRequest]]
}

class RandomGithubClient(implicit ec: ExecutionContext)
  extends GithubClient with Generators with TraceableLogging {
  def getPrsByName(handle: GithubHandle)(implicit traceId: String): Future[Set[PullRequest]] = Future {
    val prs = implicitly[Arbitrary[Set[PullRequest]]].arbitrary.sample.get.take(5)
    logger.info(s"Returning ${prs.size} random PRs for Github user $handle")
    prs
  }
}
