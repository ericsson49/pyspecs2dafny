ThisBuild / scalaVersion := "3.3.1"

lazy val py_grammar = project
  .in(file("py_grammar"))
  .settings(
    libraryDependencies ++= Seq(
      "org.antlr" % "antlr4-runtime" % "4.11.1",
      "com.yuvalshavit" % "antlr-denter" % "1.1"
    ),
    Compile / javaSource := baseDirectory.value / "src"
  )

lazy val transpiler = project
  .in(file("transpiler"))
  .dependsOn(py_grammar)
  .settings(
    name := "Py2Dafny",
    libraryDependencies += "io.github.java-diff-utils" % "java-diff-utils" % "4.12",
    Compile / scalaSource := baseDirectory.value / "src",
    mainClass in (Compile,run) := Some("onotole_scala.main")
  )

