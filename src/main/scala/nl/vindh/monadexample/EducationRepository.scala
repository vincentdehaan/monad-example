package nl.vindh.monadexample

import nl.vindh.monadexample.domain._
import org.scalacheck.Arbitrary

import scala.concurrent.{ExecutionContext, Future}
import org.scalacheck.ScalacheckShapeless._

trait EducationRepository{
  def getEducation(id: EducationId): Future[Education]
}

class RandomEducationRepository(implicit ec: ExecutionContext) extends EducationRepository with Generators {
  def getEducation(id: EducationId): Future[Education] = Future {
    implicitly[Arbitrary[Education]].arbitrary.sample.get
  }
}
