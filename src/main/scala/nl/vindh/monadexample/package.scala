package nl.vindh

import io.circe.Encoder
import nl.vindh.monadexample.domain.EducationLevel

package object monadexample {
  implicit val levelEncoder: Encoder[EducationLevel.Value] = Encoder.encodeEnumeration(EducationLevel)
}
