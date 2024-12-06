import sbt.*

object Dependencies {

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"       %% "api-test-runner"   % "0.7.0" % Test,
    "io.cucumber"       %% "cucumber-scala"    % "8.20.0",
    "io.cucumber"        % "cucumber-junit"    % "7.15.0",
    "junit"              % "junit"             % "4.13.2",
    "com.github.sbt"     % "junit-interface"   % "0.13.3",
    "org.scalatest"     %% "scalatest"         % "3.2.17",
    "com.typesafe.play" %% "play-json"         % "2.9.4",
    "io.circe"          %% "circe-json-schema" % "0.1.0",
    "io.circe"          %% "circe-core"        % "0.12.2",
    "io.circe"          %% "circe-generic"     % "0.12.2",
    "io.circe"          %% "circe-parser"      % "0.12.2"
  )
}
