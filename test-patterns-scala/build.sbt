name := """test-patterns-scala"""

version := "1.0"

scalaVersion := "2.10.2"

autoCompilerPlugins := true

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += Resolver.sonatypeRepo("snapshots")

unmanagedJars in Compile += Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/jfxrt.jar"))

libraryDependencies ++= Seq(
	"org.scalatest"						%		"scalatest_2.10" 	%	"2.0"				%	"test",
	"org.specs2" 							%%	"specs2" 					%	"2.1.1"			%	"test",
	"org.seleniumhq.selenium"	%		"selenium-java" 	%	"2.33.0"		%	"test",
	"org.testng"							%		"testng"					%	"5.14.9"		%	"test",
	"com.typesafe.akka"				%%	"akka-testkit"		%	"2.2.3"			%	"test",
	"com.typesafe.akka"				%%	"akka-actor"			%	"2.2.3",
	"com.typesafe.akka" 			%%	"akka-dataflow"		% "2.2.3",
	"net.databinder.dispatch" %%	"dispatch-core"		% "0.11.0",
	"com.scalarx" 						%		"scalarx_2.10" 		% "0.1",
	"io.spray" 								% 	"spray-can" 			% "1.2-RC2",
	"io.spray" 								% 	"spray-routing" 	% "1.2-RC2",
	"io.spray" 								% 	"spray-client"		% "1.2-RC2",
	"com.typesafe.slick"			%% 	"slick" 					% "1.0.1",
  "org.slf4j" 							% 	"slf4j-nop" 			% "1.6.4",
  "com.h2database" 					% 	"h2" 							% "1.3.166",
  "junit" % "junit" % "4.11" % "test" 
)


//-oD to show test duration
testOptions in Test += Tests.Argument("-oD")

//run tests in parallel?
parallelExecution in Test := false

//to enable dataflow

libraryDependencies +=
  compilerPlugin("org.scala-lang.plugins" % "continuations" % "2.10.2")

scalacOptions += "-P:continuations:enable"

