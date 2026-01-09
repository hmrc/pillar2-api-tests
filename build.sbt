import org.typelevel.scalacoptions.ScalacOptions

ThisBuild / scalaVersion := "3.3.6"

ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision

lazy val testSuite = (project in file("."))
  .disablePlugins(
    JUnitXmlReportPlugin
  ) // Required to prevent https://github.com/scalatest/scalatest/issues/1427
  .enablePlugins(ScalafixPlugin, TpolecatPlugin)
  .settings(
    name := "pillar2-api-tests",
    version := "0.1.0",
    libraryDependencies ++= Dependencies.test,
    resolvers += MavenRepository("HMRC-open-artefacts-maven2", "https://open.artefacts.tax.service.gov.uk/maven2"),
    compilerSettings
  )

lazy val compilerSettings = Seq(
  scalacOptions ~= (_.distinct),
  Test / tpolecatExcludeOptions += ScalacOptions.warnNonUnitStatement
)

addCommandAlias(
  "prePrChecks",
  ";scalafmtCheckAll;scalafmtSbtCheck;scalafixAll --check"
)
addCommandAlias("lint", ";scalafmtAll;scalafmtSbt;scalafixAll")
