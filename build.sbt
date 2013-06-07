name := "ClusterExample"

organization := "janrain.jedi"

version := "0.1.0"

scalaVersion := "2.10.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.2-M3",
  "com.typesafe.akka" %% "akka-cluster-experimental" % "2.2-M3",
  "com.typesafe.akka" %% "akka-slf4j" % "2.2-M3",
  "ch.qos.logback" % "logback-classic" % "1.0.7"
)
