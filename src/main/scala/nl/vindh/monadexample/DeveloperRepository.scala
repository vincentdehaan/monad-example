package nl.vindh.monadexample

import nl.vindh.monadexample.domain._
import org.scalacheck.Arbitrary

import scala.concurrent.{ExecutionContext, Future}
import org.scalacheck.ScalacheckShapeless._

trait DeveloperRepository {
  def getDevelopers(implicit traceId: String): Future[Seq[Developer]]
}

class RandomDeveloperRepository(implicit ec: ExecutionContext)
  extends DeveloperRepository with Generators with TraceableLogging {
  def getDevelopers(implicit traceId: String): Future[Seq[Developer]] = Future {
    val developers = implicitly[Arbitrary[Seq[Developer]]].arbitrary.sample.get.take(5)
    logger.info(s"Created ${developers.size} random developers")
    developers
  }
}
