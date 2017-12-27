import sbt.Keys.libraryDependencies

lazy val server = (project in file("server")).settings(commonSettings).settings(
    scalaJSProjects := Seq(client),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    pipelineStages := Seq(digest, gzip),
    // triggers scalaJSPipeline when using compile or continuous compilation
    compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
    libraryDependencies ++= Seq(
        "com.vmunier" %% "scalajs-scripts" % "1.1.1",
        guice,
        specs2 % Test
    ),
    // Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
    EclipseKeys.preTasks := Seq(compile in Compile)
).enablePlugins(PlayScala).
        dependsOn(sharedJvm, discord)

lazy val client = (project in file("client")).settings(commonSettings).settings(
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.4"
    )
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
        dependsOn(sharedJs)

lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared")).settings(commonSettings)
lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val commonSettings = Seq(
    scalaVersion := "2.12.4",
    organization := "com.kuzlam"
)

// loads the server project at sbt startup
onLoad in Global := (onLoad in Global).value andThen { s: State => "project server" :: s }

lazy val discord = (project in file("discord")).settings(commonSettings).settings(
    // Discord4J
    // https://github.com/austinv11/Discord4J
    // https://discord4j.readthedocs.io/en/latest/
    resolvers += "jitpack" at "https://jitpack.io",
    libraryDependencies += "com.github.austinv11" % "Discord4J" % "2.9.3",

    // Lavaplayer https://github.com/sedmelluq/lavaplayer
    resolvers += "bintray" at "http://jcenter.bintray.com",
    libraryDependencies += "com.sedmelluq" % "lavaplayer" % "1.2.45",

    // scalikejdbc
    libraryDependencies ++= Seq(
        "org.scalikejdbc" %% "scalikejdbc"       % "3.1.0",
        "com.h2database"  %  "h2"                % "1.4.196",
        "ch.qos.logback"  %  "logback-classic"   % "1.2.3"
    )
)


