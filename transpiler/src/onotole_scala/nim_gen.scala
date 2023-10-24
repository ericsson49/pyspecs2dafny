package onotole_scala

import ast.{TExpr, Stmt}
import ast.Stmt.*
import ast.TExpr.*

import java.io.{PrintWriter, Writer}


def genNimTL(tl: ast.TopLevelDef)(using gen: NimCodeGen): Unit = tl match
  case f: FunctionDef => gen.genTLFunction(f)
  case cl: ClassDef => gen.genTLClass(cl)

def genNim(s: Stmt)(using gen: NimCodeGen): Unit = s match
  case VarDecl(v, t, e) => gen.genVarDecl(v, t, e)
  case Assert(test, msg) =>
    if msg.isInstanceOf[Some[TExpr]] then ???
    gen.genAssert(test)
  case Expr(e) =>
    gen.genExpr(e)
  case Assign(Name(v), value) =>
    gen.genVarAssign(v, value)
  case Assign(Attribute(tgt, attr), value) =>
    gen.genAttrAssign(tgt, attr, value)
  case Assign(Subscript(tgt, idx), value) =>
    gen.genIdxAssign(tgt, idx, value)
  case AnnAssign(Name(v), t, value) =>
    gen.genVarDecl(v, Some(t), value)
  case If(test, body, orelse) =>
    gen.genIf(test,
      cg => { body.foreach(genNim(_)(using cg)) },
      cg => { orelse.foreach(genNim(_)(using cg)) }
    )
  case While(test, body, anno) =>
    gen.genWhile(test, anno, cg => { body.foreach(genNim(_)(using cg)) })
  case Break() => gen.genBreak

object NimExprGen:
  val noExcnMethods = Set(
    "Result", "testStepper", "iter", "len"
  )
  def throwsExcn(e: TExpr): Boolean = e match
    case Subscript(_, _) => true
    case Call(Name(n), _, _) => !noExcnMethods.contains(n)
    case _ => false

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
    case Subscript(v, indices) => s"${genExpr(v)}.get(${indices.map(genExpr).mkString(", ")})"
    case UnaryOp(op, operand) => s"${opToName(op)}(${genExpr(operand)})"
    case BinOp(left, op, right) => s"${opToName(op)}(${genExpr(left)}, ${genExpr(right)})"
    case Compare(left, ops, operands) =>
      if ops.length != 1 || operands.length != 1 then ???
      s"${opToName(ops(0))}(${genExpr(left)}, ${genExpr(operands(0))})"
    case Call(func_, args_, kwds_) =>
      val func = genExpr(func_)
      val args = args_.map(genExpr(_))
      val kwds = kwds_.map((k,v) => s"$k := ${genExpr(v)}")
      s"$func(${(args ++ kwds).mkString(", ")})"
    case PyTuple(elems) => elems.map(genExpr(_)).mkString("(", ",", ")")
    case PyList(elems) => s"new_pylist(${elems.map(genExpr(_)).mkString("[", ", ", "]")})"
    case PySet(elems) => s"new_pyset(${elems.map(genExpr(_)).mkString("{", ", ", "}")})"
    case PyDict(keys, values) =>
      val kvPairs = keys.zip(values).map((k,v) => s"(${genExpr(k)}, ${genExpr(v)})")
      s"new_pydict(${kvPairs.mkString("[", ", ", "]")})"
  def clsNameMap(cls: String): String = cls match
    case "object" => "pyobject"
    case "int" => "pyint"
    case "bool" => "pybool"
    case "str" => "pystr"
    case _ => cls
  def genTypeExpr(t: TExpr): String = t match
    case Name(cls) => clsNameMap(cls)
    case Subscript(v, tps) => s"${genTypeExpr(v)}<${tps.map(genTypeExpr(_)).mkString(",")}>"


case class NimCodeGen(indent: String, writer: Writer):
  val outW = PrintWriter(writer)
  def out(s: String): Unit = outW.println(indent + s)

  def genTLFunction(f: FunctionDef): Unit =
    val funcAnnos = f.body.takeWhile(s => s.isInstanceOf[Annotation] && s.asInstanceOf[Annotation].s.startsWith("@@"))
    val body = f.body.slice(funcAnnos.length, f.body.length)
    out(s"proc ${f.name}(): tuple[] =")
    //funcAnnos.foreach(s =>
    //  val anno = s.asInstanceOf[Annotation].s
    //  out(anno.substring(2, anno.length))
    //)
    val bodyGen = this.copy(indent = indent + "  ")
    bodyGen.genVarAssign("result", PyTuple(elems = List()))
    body.foreach(genNim(_)(using bodyGen))

  def genTLClass(c: ClassDef): Unit =
    out(s"type ${c.name} = ref object by ${NimExprGen.genTypeExpr(c.base)}")
    val bg = this.copy(indent = this.indent + "  ")
    c.fields.foreach((f, t, i) =>
      bg.out(s"${f}: ${NimExprGen.genTypeExpr(t)}")
    )

  def genVarDecl(v: String, t: Option[TExpr], e: Option[TExpr]): Unit =
    val prefix = s"var $v"
    val anno = t.map(t => s": ${NimExprGen.genTypeExpr(t)}").getOrElse("")
    val init = e.map(e => s" = ${NimExprGen.genExpr(e)}").getOrElse("")
    out(prefix + anno + init)
  def genVarAssign(v: String, e: TExpr): Unit = out(s"$v = ${NimExprGen.genExpr(e)}")
  def genAttrAssign(tgt: TExpr, attr: String, v: TExpr): Unit =
    out(s"${NimExprGen.genExpr(tgt)}.$attr = ${NimExprGen.genExpr(v)}")
  def genIdxAssign(tgt: TExpr, idx: List[TExpr], v: TExpr): Unit =
    val callIdxSet = Call(Attribute(tgt, "update"), idx.appended(v))
    genExpr(callIdxSet)
  def genAssert(test: TExpr): Unit =
    genExpr(Call(Name("pyassert"), List(test), List()))
  def genExpr(e: TExpr): Unit =
    out(s"discard ${NimExprGen.genExpr(e)}")
  def genIf(e: TExpr, bg: NimCodeGen => Unit, orelse: NimCodeGen => Unit): Unit =
    out(s"if is_true(${NimExprGen.genExpr(e)}):")
    bg(this.copy(indent = indent + "  "))
    out("else:")
    orelse(this.copy(indent = indent + "  "))

  def genWhile(e: TExpr, anno: List[Annotation], bg: NimCodeGen => Unit): Unit =
    val testE = e match
      case NameConstant(Some(b)) => b.toString
      case Call(Name("has_next"), List(_), Nil) => NimExprGen.genExpr(e)
    out(s"while $testE:")
    bg(this.copy(indent = indent + "  "))

  def genBreak: Unit = out("break")
