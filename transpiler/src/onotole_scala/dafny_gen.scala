package onotole_scala

import ast.Stmt.*
import ast.{Stmt, TExpr}
import ast.TExpr.*

import java.io.{PrintWriter, Writer}


def genDafnyTL(tl: ast.TopLevelDef)(using gen: CodeGen): Unit = tl match
  case f: FunctionDef => gen.genTLFunction(f)
  case cl: ClassDef => gen.genTLClass(cl)

def genDafny(s: Stmt)(using gen: CodeGen): Unit = s match
  case VarDecl(v, t, e) => gen.genVarDecl(v, t, e)
  case Assert(test, msg) =>
    if msg.isInstanceOf[Some[TExpr]] then ???
    gen.genAssert(test)
  case Expr(e) =>
    gen.genExpr(e, false)
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
      cg => { body.foreach(genDafny(_)(using cg)) },
      cg => { orelse.foreach(genDafny(_)(using cg)) }
    )
  case While(test, body, anno) =>
    gen.genWhile(test, anno, cg => { body.foreach(genDafny(_)(using cg)) })
  case Break() => gen.genBreak
  case Continue() => gen.genContinue
  case r: Return => gen.getReturn(r.value)

object DafnyExprGen:
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
    case "*" => "pymul"
    case "%" => "pymod"
    case "//" => "pyfloordiv"
    case "/" => ???
    case "==" => "pyeq"
    case "!=" => "pyne"
    case "<=" => "pyle"
    case "<" => "pylt"
    case ">=" => "pyge"
    case ">" => "pygt"
    case "in" => "pyin"
    case "is" => "pyis"
    case _ => op
  def genExpr(e: TExpr): String = e match
    case NameConstant(Some(true)) => "new_pybool(true)"
    case NameConstant(Some(false)) => "new_pybool(false)"
    case NameConstant(None) => "null"
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

case class CodeGen(indent: String, writer: Writer):
  val outW = PrintWriter(writer)
  def out(s: String): Unit = outW.println(indent + s)
  def genTLFunction(f: FunctionDef): Unit =
    val funcAnnos = f.body.takeWhile(s => s.isInstanceOf[Annotation] && s.asInstanceOf[Annotation].s.startsWith("@@"))
    val body = f.body.slice(funcAnnos.length, f.body.length)
    val args = f.args.map(a => s"${a._1}: ${DafnyExprGen.genTypeExpr(a._2)}")
    val retType = f.returns.getOrElse(NameConstant(None)) match
      case NameConstant(None) => "()"
      case ret => s"Outcome<${DafnyExprGen.genTypeExpr(ret)}>"
    out(s"method ${f.name}(${args.mkString(", ")}) returns (ret_: $retType)")
    funcAnnos.foreach(s =>
      val anno = s.asInstanceOf[Annotation].s
      out(anno.substring(2, anno.length))
    )
    out("{")
    val bodyGen = this.copy(indent = indent + "  ")
    bodyGen.genVarAssign("ret_", Call(Name("Result"), List(PyTuple(elems = List()))))
    body.foreach(genDafny(_)(using bodyGen))
    out("}")

  def genTLClass(c: ClassDef): Unit =
    out(s"trait ${c.name} extends ${DafnyExprGen.genTypeExpr(c.base)} {")
    val bg = this.copy(indent = this.indent + "  ")
    c.fields.foreach((f, t, i) =>
      bg.out(s"var ${f}: ${DafnyExprGen.genTypeExpr(t)};")
    )
    out("}")

  def genAssgnSuffix(excn: Boolean, e: TExpr): String =
    val assgOp = if (excn) ":-" else ":="
    s"$assgOp ${DafnyExprGen.genExpr(e)}"

  def genAssgnSuffix(e: TExpr): String =
    genAssgnSuffix(DafnyExprGen.throwsExcn(e), e)

  def genVarDecl(v: String, t: Option[TExpr], e: Option[TExpr]): Unit =
    val prefix = s"var $v"
    val anno = t.map(t => s": ${DafnyExprGen.genTypeExpr(t)}").getOrElse("")
    val init = e.map(e => s" ${genAssgnSuffix(e)}").getOrElse("")
    out(prefix + anno + init + ";")
  def genVarAssign(v: String, e: TExpr): Unit = out(s"$v ${genAssgnSuffix(e)};")
  def genAttrAssign(tgt: TExpr, attr: String, v: TExpr): Unit =
    out(s"${DafnyExprGen.genExpr(tgt)}.$attr ${genAssgnSuffix(v)};")
  def genIdxAssign(tgt: TExpr, idx: List[TExpr], v: TExpr): Unit =
    val callIdxSet = Call(Attribute(tgt, "update"), idx.appended(v))
    genExpr(callIdxSet, true)
  def genAssert(test: TExpr): Unit =
    genExpr(Call(Name("pyassert"), List(test), List()), true)
  def genTExpr(e: TExpr): Unit = out(s"${DafnyExprGen.genExpr(e)};")
  def genExpr(e: TExpr, checkExcn: Boolean): Unit =
    e match
      case _: Str => // todo
      case _ => out(s"var _ ${genAssgnSuffix(checkExcn, e)};")
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
  def genContinue: Unit = out("continue;")
  def getReturn(retVal: Option[TExpr]): Unit =
    retVal match
      case None => out("return;")
      case Some(value) => out(s"return ${DafnyExprGen.genExpr(value)};")
