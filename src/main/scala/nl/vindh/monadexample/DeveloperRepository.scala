package nl.vindh.monadexample

import nl.vindh.monadexample.domain._
import org.scalacheck.Arbitrary

import scala.concurrent.{ExecutionContext, Future}
import org.scalacheck.ScalacheckShapeless._

trait DeveloperRepository {
  def getDevelopers: Future[Seq[Developer]]
}

class RandomDeveloperRepository(implicit ec: ExecutionContext)
  extends DeveloperRepository with Generators {
  def getDevelopers: Future[Seq[Developer]] = Future {
    implicitly[Arbitrary[Seq[Developer]]].arbitrary.sample.get
  }
}
