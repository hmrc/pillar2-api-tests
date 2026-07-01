import sbt.*

object Dependencies {

  val test: Seq[ModuleID] = (Seq(
    "com.networknt"      % "json-schema-validator"   % "1.5.9",
    "com.typesafe.play" %% "play-json"               % "2.10.8",
    "com.google.inject"  % "guice"                   % "7.0.0",
    "org.scalatest"     %% "scalatest"               % "3.2.20",
    "uk.gov.hmrc"       %% "api-test-runner"         % "0.10.0",
    "uk.gov.hmrc"       %% "http-verbs-test-play-30" % "15.8.0"
  ) ++ Seq("slf4j", "serialization-jackson", "actor-typed", "protobuf-v3", "stream")
    .map(lib => "org.apache.pekko" %% s"pekko-$lib" % "1.6.0"))
    .map(_ % Test)
}
