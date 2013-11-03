val akkaVersion = "2.2.3"

name := "ClusterExample"

organization := "lockney.net"

version := "0.1.0"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.0.7"
)

packagerSettings

packageArchetype.java_application

mapGenericFilesToLinux

mappings in Universal <++= (resourceDirectory in Compile) map { (resDir) ⇒
  resDir.listFiles.toSeq.collect {
    case f: File if f.getName.endsWith(".xml") ⇒ f
    case f: File if f.getName.endsWith(".conf") ⇒ f
    case f: File if f.getName.endsWith(".properties") ⇒ f
  }.map { f ⇒ f → ("conf/" + f.getName) }
}
