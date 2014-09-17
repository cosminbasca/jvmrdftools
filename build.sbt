import AssemblyKeys._

// ---------------------------------------------------------------------------------------------------------------------
//
// assembly setup
//
// ---------------------------------------------------------------------------------------------------------------------
assemblySettings

name := "jvmrdftools"

version := "0.1.0"

scalaVersion := "2.11.2"

scalacOptions ++= Seq("-optimize", "-Ydelambdafy:inline", "-Yclosure-elim", "-Yinline-warnings", "-Ywarn-adapted-args", "-Ywarn-inaccessible", "-feature", "-deprecation")

// ---------------------------------------------------------------------------------------------------------------------
//
// build info setup
//
// ---------------------------------------------------------------------------------------------------------------------
buildInfoSettings

sourceGenerators in Compile <+= buildInfo

buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)

buildInfoPackage := "com.avalanche"

// ---------------------------------------------------------------------------------------------------------------------
//
// dependencies
//
// ---------------------------------------------------------------------------------------------------------------------

libraryDependencies ++= Seq()

libraryDependencies += ("org.scalatest" %% "scalatest" % "2.1.7" % "test")

libraryDependencies += ("com.simplehttp" %% "simplehttp" % "0.2.6")

libraryDependencies += ("com.sparqlclient" %% "sparqlclient" % "0.2.5")

libraryDependencies += ("com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2") //.exclude("org.scala-lang", "scala-reflect")

libraryDependencies += ("log4j" % "log4j" % "1.2.17")

libraryDependencies += ("org.slf4j" % "slf4j-log4j12" % "1.7.7")

libraryDependencies += ("com.github.scopt" %% "scopt" % "3.2.0")