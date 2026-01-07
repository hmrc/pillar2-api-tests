import sbt.*

object Dependencies {

  val guiceVersion = "7.20.1"
  val circeVersion = "0.14.10"

  val test: Seq[ModuleID] = (Seq(
    "uk.gov.hmrc"       %% "api-test-runner"         % "0.8.0",
    "com.google.inject"  % "guice"                   % "7.0.0",
    "junit"              % "junit"                   % "4.13.2",
    "com.github.sbt"     % "junit-interface"         % "0.13.3",
    "org.scalatest"     %% "scalatest"               % "3.2.19",
    "com.typesafe.play" %% "play-json"               % "2.10.6",
    "io.circe"          %% "circe-json-schema"       % "0.2.0" cross CrossVersion.for3Use2_13,
    "io.circe"          %% "circe-core"              % circeVersion cross CrossVersion.for3Use2_13,
    "io.circe"          %% "circe-generic"           % circeVersion cross CrossVersion.for3Use2_13,
    "io.circe"          %% "circe-parser"            % circeVersion cross CrossVersion.for3Use2_13,
    "uk.gov.hmrc"       %% "http-verbs-test-play-30" % "15.1.0"
  ) ++ Seq("slf4j", "serialization-jackson", "actor-typed", "protobuf-v3", "stream")
    .map(lib => "org.apache.pekko" %% s"pekko-$lib" % "1.1.2"))
    .map(_ % Test)
}