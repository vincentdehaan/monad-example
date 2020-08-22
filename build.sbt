name := "monad-example"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http"   % "10.1.12",
  "com.typesafe.akka" %% "akka-stream" % "2.6.8",
  "com.chuusai" %% "shapeless" % "2.3.3",
  "org.typelevel" %% "cats-core" % "2.1.1",
  "org.typelevel" %% "cats-free" % "2.1.1",
  "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.5",
  "de.heikoseeberger" %% "akka-http-circe" % "1.33.0",
  "io.circe" %% "circe-core" % "0.14.0-M1", // We need this version because of the tagged types
  "io.circe" %% "circe-shapes" % "0.14.0-M1",
  "io.circe" %% "circe-generic" % "0.14.0-M1"
)

mainClass in (Compile, run) := Some("nl.vindh.monadexample.ResumeServiceTest")