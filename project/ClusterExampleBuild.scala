import sbt._
import Keys._
import com.typesafe.sbt.SbtNativePackager._
import com.typesafe.sbt.packager.Keys._

object ClusterExampleBuild extends Build {

  val akkaVersion = "2.2.3"

  lazy val clusterExample = Project("cluster-example", file("."))
    .settings(packagerSettings ++ packageArchetype.java_server ++ mapGenericFilesToLinux:_*)
    .settings(
      name := "cluster",
      organization := "lockney.net",
      version := "0.1.0",
      scalaVersion := "2.10.3",
      libraryDependencies ++= Seq(
        "com.typesafe.akka" %% "akka-actor" % akkaVersion,
        "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
        "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
        "ch.qos.logback" % "logback-classic" % "1.0.7"
      ),
      name in Debian := "cluster-example",
      version in Debian := version.value,
      debianPackageDependencies in Debian ++= Seq("java2-runtime"),
      mappings in Universal <++= (resourceDirectory in Compile) map { (resDir) ⇒
        resDir.listFiles.toSeq.collect {
          case f: File if f.getName.endsWith(".xml") ⇒ f
          case f: File if f.getName.endsWith(".conf") ⇒ f
          case f: File if f.getName.endsWith(".properties") ⇒ f
        }.map { f ⇒ f → ("conf/" + f.getName) }
      },
      linuxPackageMappings in Debian <+= (sourceDirectory) map { bd =>
        (packageMapping(
          (bd / "debian/changelog") -> "/usr/share/doc/sbt/changelog.gz"
        ) withUser "root" withGroup "root" withPerms "0644" gzipped) asDocs()
      }
    )
}