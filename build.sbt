name := "akka_http"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.3",
  "com.typesafe.akka" %% "akka-http" % "10.0.7",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.7",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.7",
  "com.typesafe.slick" %% "slick" % "3.2.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0",
  "com.h2database" % "h2" % "1.0.60"

)