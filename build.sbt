name := "lamdaPractice"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-lambda-java-core"   % "1.1.0" % Provided,
  "com.amazonaws" % "aws-lambda-java-events" % "1.1.0" % Provided
)

val json4sVersion = "3.5.0"

libraryDependencies ++= Seq("org.json4s" %% "json4s-jackson").map(_ % json4sVersion)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % Test
libraryDependencies += "org.mockito" % "mockito-core" % "2.1.0"