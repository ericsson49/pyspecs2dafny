package onotole_scala

import ast.{Stmt, TExpr, TopLevelVisitor}
import ast.Stmt.*
import ast.TExpr.*
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import python3_grammar.{Python3Lexer, Python3Parser}
import util.*

import java.io.{File, FileFilter, FileWriter, FilenameFilter}
import java.nio.file.Paths

def mkFV(): (String) => String =
  var counters: Map[String, Int] = Map()
  (prefix) =>
    val num = counters.getOrElse(prefix, 0)
    val vn = prefix + "_" + num
    counters = counters.updated(prefix, num + 1)
    vn

@main def testtt() =
  val tests = Paths.get("lang_tests").toFile.listFiles(f =>
    f.isFile && f.getName.endsWith(".py")
  ).map(f => f.getName.substring(0, f.getName.length()-3)).toList
  for testName <- tests do
    println(s"processing $testName")
    transpileTest(testName)

def transpileTest(testName: String): Unit =
  val p1 = parser(CharStreams.fromPath(Paths.get("lang_tests", s"$testName.py")))
  val tlDefs = TopLevelVisitor.visitFile_input(p1.file_input())
  val tr = tlDefs.map(semTL2(_)(using mkFV()))
  val vd = tr.map(inferVarDeclsTL(_))
  val outF = Paths.get("dafny", s"$testName.dfy")
  val fw = new FileWriter(outF.toFile)
  val gen = CodeGen("", fw)
  gen.out("include \"pylib.dfy\"")
  gen.out("")
  vd.foreach(genDafnyTL(_)(using gen))
  fw.close()

