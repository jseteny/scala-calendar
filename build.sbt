name := "jse-scala-calendar"

version := "1.0"

scalaVersion := "2.11.7"


//http://www.scala-sbt.org/0.13.5/docs/Getting-Started/Multi-Project.html


lazy val root =
  project.in( file(".") )
    .aggregate(util, core)

lazy val core = project

lazy val util = project.dependsOn(core)
