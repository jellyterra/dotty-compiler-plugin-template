ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.4"

autoCompilerPlugins := true

lazy val root = (project in file(".")).settings(
  name := "PluginName",
  libraryDependencies += "org.scala-lang" %% "scala3-compiler" % scalaVersion.value % "provided",
)
