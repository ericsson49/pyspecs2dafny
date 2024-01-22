package onotole_scala

import java.io.FileWriter
import java.nio.file.{Path, Paths}
import ast.{TopLevelVisitor, TExpr, Stmt}
import org.antlr.v4.runtime.CharStreams
import util.parser

def mkFV(): (String) => String =
  var counters: Map[String, Int] = Map()
  (prefix) =>
    val num = counters.getOrElse(prefix, 0)
    val vn = prefix + "_" + num
    counters = counters.updated(prefix, num + 1)
    vn

def compile(inp: Path, out: Path) =
  val p1 = parser(CharStreams.fromPath(inp))
  val tlDefs = TopLevelVisitor.visitFile_input(p1.file_input())
  val tr = tlDefs.map(semTL2(_)(using mkFV()))
  val vd = tr.map(inferVarDeclsTL(_))
  val fw = new FileWriter(out.toFile)
  val gen = CodeGen("", fw)
  gen.out("include \"pylib.dfy\"")
  gen.out("")
  vd.foreach(genDafnyTL(_)(using gen))
  fw.close()

@main def main(inp: String, out: String) =
  compile(Paths.get(inp), Paths.get(out))