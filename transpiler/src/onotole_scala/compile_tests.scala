package onotole_scala

import ast.{Stmt, TExpr, TopLevelVisitor}
import ast.Stmt.*
import ast.TExpr.*
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import python3_grammar.{Python3Lexer, Python3Parser}
import util.*

import java.io.{File, FileFilter, FileWriter, FilenameFilter}
import java.nio.file.Paths

@main def testtt() =
  val tests = Paths.get("lang_tests").toFile.listFiles(f =>
    f.isFile && f.getName.endsWith(".py")
  ).map(f => f.getName.substring(0, f.getName.length()-3)).toList
  for testName <- tests do
    println(s"processing $testName")
    transpileTest(testName)

def transpileTest(testName: String): Unit =
  compile(Paths.get("lang_tests", s"$testName.py"), Paths.get("dafny", s"$testName.dfy"))

