import sbt.*

object Dependencies {

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"   %% "api-test-runner" % "0.7.0" % Test,
    "io.cucumber"   %% "cucumber-scala"  % "8.20.0",
    "io.cucumber"    % "cucumber-junit"  % "7.15.0",
    "junit"          % "junit"           % "4.13.2",
    "org.scalatest" %% "scalatest"       % "3.2.17"
  )
}
