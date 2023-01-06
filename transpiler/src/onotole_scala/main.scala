package onotole_scala

import ast.{Stmt, TExpr, TopLevelVisitor}
import ast.Stmt.*
import ast.TExpr.*
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import python3_grammar.{Python3Lexer, Python3Parser}
import util.*

import java.nio.file.Paths

@main def testtt() =
  val p1 = parser(CharStreams.fromPath(Paths.get("lang_tests", "test0003.py")))
  val tlDefs = TopLevelVisitor.visitFile_input(p1.file_input())
  val annotated = tlDefs.map(annotateTopLevel(_))
  var num = 0
  val freshV = () =>
    val vn = "tmp_" + num
    num += 1
    vn
  val tr = annotated.map(semTL(_)(using freshV))
  val vd = tr.map(tl => tl match
    case f: Stmt.FunctionDef => inferVarDecls(f)
    case _ => tl
  )
  val gen = CodeGen("")
  vd.foreach(genDafnyTL(_)(using gen))

def genDafnyTL(tl: ast.TopLevelDef)(using gen: CodeGen): Unit = tl match
  case f: FunctionDef => gen.genTLFunction(f)
  case cl: ClassDef => gen.genTLClass(cl)

def genDafny(s: Stmt)(using gen: CodeGen): Unit = s match
  case VarDecl(v, t, e) => gen.genVarDecl(v, t, e)
  case Assert(test, msg) =>
    if msg.isInstanceOf[Some[TExpr]] then ???
    gen.genAssert(test)
  case Expr(e) =>
    gen.genExpr(e)
  case Assign(Name(v), value) =>
    gen.genVarAssign(v, value)
  case AnnAssign(Name(v), t, value) =>
    gen.genVarDecl(v, Some(t), value)
  case If(test, body, orelse) =>
    gen.genIf(test,
      cg => { body.foreach(genDafny(_)(using cg)) },
      cg => { orelse.foreach(genDafny(_)(using cg)) }
    )
  case While(test, body, anno) =>
    gen.genWhile(test, anno, cg => { body.foreach(genDafny(_)(using cg)) })
  case Break() => gen.genBreak

object DafnyExprGen:
  def opToName(op: String): String = op match
    case "not" => "pynot"
    case "+" => "pyplus"
    case "-" => "pyminus"
    case "==" => "pyeq"
    case "!=" => "pyne"
    case "<=" => "pyle"
    case "<" => "pylt"
    case ">=" => "pyge"
    case ">" => "pygt"
    case _ => op
  def genExpr(e: TExpr): String = e match
    case NameConstant(Some(true)) => "new_pybool(true)"
    case NameConstant(Some(false)) => "new_pybool(false)"
    case NameConstant(None) => ???
    case Num(n) => s"new_pyint($n)"
    case Name(id) => id
    case Attribute(v, attr) => s"${genExpr(v)}.$attr"
    case Subscript(v, indices) => s"${genExpr(v)}[${indices.map(genExpr).mkString(", ")}]"
    case UnaryOp(op, operand) => s"${opToName(op)}(${genExpr(operand)})"
    case BinOp(left, op, right) => s"${opToName(op)}(${genExpr(left)}, ${genExpr(right)})"
    case Compare(left, ops, operands) =>
      if ops.length != 1 || operands.length != 1 then ???
      s"${opToName(ops(0))}(${genExpr(left)}, ${genExpr(operands(0))})"
    case Call(Name(func), args_, kwds_) =>
      val args = args_.map(genExpr(_))
      val kwds = kwds_.map((k,v) => s"$k := ${genExpr(v)}")
      s"$func(${(args ++ kwds).mkString(", ")})"
    case PyList(elems) => s"new_pylist(${elems.map(genExpr(_)).mkString("[", ", ", "]")})"
    case PySet(elems) => s"new_pyset(${elems.map(genExpr(_)).mkString("{", ", ", "}")})"
    case PyDict(keys, values) =>
      val kvPairs = keys.zip(values).map((k,v) => s"${genExpr(k)} := ${genExpr(v)}")
      s"new_pydict(${kvPairs.mkString("map[", ", ", "]")})"

  def clsNameMap(cls: String): String = cls match
    case "object" => "pyobject"
    case "int" => "pyint"
    case "bool" => "pybool"
    case "str" => "pystr"
    case _ => cls
  def genTypeExpr(t: TExpr): String = t match
    case Name(cls) => clsNameMap(cls)
    case Subscript(v, tps) => s"${genTypeExpr(v)}<${tps.map(genTypeExpr(_)).mkString(",")}>"

case class CodeGen(indent: String):
  def out(s: String): Unit = println(indent + s)
  def genTLFunction(f: FunctionDef): Unit =
    out(s"method ${f.name}() returns (ret_: Outcome<()>)")
    out("{")
    val bodyGen = this.copy(indent = indent + "  ")
    f.body.foreach(genDafny(_)(using bodyGen))
    out("}")

  def genTLClass(c: ClassDef): Unit =
    out(s"trait ${c.name} extends ${DafnyExprGen.genTypeExpr(c.base)} {")
    val bg = this.copy(indent = this.indent + "  ")
    c.fields.foreach((f, t, i) =>
      bg.out(s"var ${f}: ${DafnyExprGen.genTypeExpr(t)};")
    )
    out("}")

  def genVarDecl(v: String, t: Option[TExpr], e: Option[TExpr]): Unit =
    val prefix = s"var $v"
    val anno = t.map(t => s": ${DafnyExprGen.genTypeExpr(t)}").getOrElse("")
    val init = e.map(e => s" := ${DafnyExprGen.genExpr(e)}").getOrElse("")
    out(prefix + anno + init + ";")
  def genVarAssign(v: String, e: TExpr): Unit = out(s"$v := ${DafnyExprGen.genExpr(e)};")
  def genAssert(test: TExpr): Unit =
    genExpr(Call(Name("var _ :- pyassert"), List(test), List()))
  def genExpr(e: TExpr): Unit = out(s"${DafnyExprGen.genExpr(e)};")
  def genIf(e: TExpr, bg: CodeGen => Unit, orelse: CodeGen => Unit): Unit =
    out(s"if is_true(${DafnyExprGen.genExpr(e)}) {")
    bg(this.copy(indent = indent + "  "))
    out("} else {")
    orelse(this.copy(indent = indent + "  "))
    out("}")
  def genWhile(e: TExpr, anno: List[Annotation], bg: CodeGen => Unit): Unit =
    val testE = e match
      case NameConstant(Some(b)) => b.toString
      case Call(Name("has_next"), List(_), Nil) => DafnyExprGen.genExpr(e)
    out(s"while $testE")
    anno.foreach(a =>
      out(a.s.substring(1))
    )
    out("{")
    bg(this.copy(indent = indent + "  "))
    out("}")

  def genBreak: Unit = out("break;")
