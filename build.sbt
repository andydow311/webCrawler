name := "WebCrawlerForHorses"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.6.15",
   "com.typesafe.akka" %% "akka-stream-testkit" % "2.6.15" % Test
)

libraryDependencies += "org.jsoup" % "jsoup" % "1.11.3"
libraryDependencies += "au.com.bytecode" % "opencsv" % "2.4"

