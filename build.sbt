import sbt._
import Dependencies._
organization := "io.radanalytics"
name := "tutorial-sparkpi-scala-scalatra"
version := "0.0.1-SNAPSHOT"
scalaVersion in ThisBuild := "2.11.11"

// 1. This is where SBT can reach out to resolve dependencies. SBT uses Apache Ivy to resolve dependencies by default, but can work with Maven repositories as well
resolvers += Resolver.sbtPluginRepo( "releases" )
resolvers += Classpaths.typesafeReleases
resolvers in ThisBuild ++= Seq( "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases",
                                "Spray IO Repository" at "http://repo.spray.io/",
                                "Maven Central" at "https://repo1.maven.org/maven2/",
                                "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/" )

// 2. Define the class to run when calling "java -jar ..."
mainClass in(Compile, run) := Some( "io.radanalytics.examples.scalatra.sparkpi.Main" )

// 3. Build the project to Java JAR conventions and add metadata to make Scalatra run
enablePlugins( JavaAppPackaging )
enablePlugins( ScalatraPlugin )


// 4. Add the project dependencies, see project/Dependencies.scala for dependency management
libraryDependencies ++= slf4j ++ logback ++ scalatra ++ scalaTest ++ spark ++ sparkTestBase


// 5. Deployment of this artifact should be part of a CI/CD pipeline. Running the unit tests while building the "fat jar" is very expensive,
//    therefore, don't do it during the "assembly" phase (which will be run on Openshift).
test in assembly := {}

// 6. Resolve any conflicts when merging into a "fat jar"
assemblyMergeStrategy in assembly := {
    case PathList( "META-INF", "MANIFEST.MF" ) => MergeStrategy.discard
    case PathList( "reference.conf" ) => MergeStrategy.concat
    case x => MergeStrategy.last
}
