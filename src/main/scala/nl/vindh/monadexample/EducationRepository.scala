package nl.vindh.monadexample

import nl.vindh.monadexample.domain._
import org.scalacheck.Arbitrary

import scala.concurrent.{ExecutionContext, Future}
import org.scalacheck.ScalacheckShapeless._

trait EducationRepository{
  def getEducation(id: EducationId)(implicit traceId: String): Future[Education]
}

class RandomEducationRepository(implicit ec: ExecutionContext)
  extends EducationRepository with Generators with TraceableLogging {
  def getEducation(id: EducationId)(implicit traceId: String): Future[Education] = Future {
    logger.info(s"Returning an education with id $id")
    implicitly[Arbitrary[Education]].arbitrary.sample.get
  }
}
