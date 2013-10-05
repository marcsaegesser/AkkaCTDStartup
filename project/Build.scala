import sbt._
import Keys._
import org.ensime.sbt.Plugin.Settings.ensimeConfig
import org.ensime.sbt.util.SExp._

object BuildSettings {
  val buildOrganization = "saegeser.org"
  val buildVersion = "1.0.0"
  val buildScalaVersion = "2.10.2"

  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization := buildOrganization,
    version := buildVersion,
    scalaVersion := buildScalaVersion,
    unmanagedBase <<= baseDirectory { base => base / "../../lib" },
    ensimeConfig := sexp(
      key(":compiler-args"), sexp("-Ywarn-dead-code", "-Ywarn-shadowing"),
      key(":formatting-prefs"), sexp(
        key(":alignSingleLineCaseStatements"), true,
        key(":spaceInsideParentheses"), false
      )
    ),
    libraryDependencies += "com.novocode" % "junit-interface" % "0.10-M2" % "test"
  )
}

object BugDemoBuild extends Build
{
  import BuildSettings._

  lazy val demo = Project("demo",
    file("."),
    settings = buildSettings ++ Seq (
      libraryDependencies ++= List(
        "com.typesafe.akka" %% "akka-slf4j" % "2.2.1",
        "com.typesafe.akka" %% "akka-actor" % "2.2.1",
//        "com.typesafe.akka" %% "akka-slf4j" % "2.3-SNAPSHOT",
//        "com.typesafe.akka" %% "akka-actor" % "2.3-SNAPSHOT",
//        "com.typesafe.akka" %% "akka-testkit" % "2.3-SNAPSHOT" % "test",
        "com.typesafe.akka" %% "akka-testkit" % "2.2.1" % "test",
        "org.scalatest" %% "scalatest" % "1.9.1" % "test"
      )
    )
  )
}
