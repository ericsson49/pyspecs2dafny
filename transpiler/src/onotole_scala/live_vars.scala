package onotole_scala

import ast.TExpr._
import ast.{Stmt, TExpr}
import ast.Stmt._

def live_vars(e: TExpr): Set[String] = e match
  case _: NameConstant => Set()
  case _: Num => Set()
  case _: Str => Set()
  case Name(n) => Set(n)
  case Attribute(v, _) => live_vars(v)
  case Subscript(v, indices) => live_vars(v) ++ indices.flatMap(live_vars(_))
  case Call(f, args, kwds) => live_vars(f) ++ args.flatMap(live_vars) ++ kwds.map(_._2).flatMap(live_vars)
  case UnaryOp(_, operand) => live_vars(operand)
  case BinOp(left, _, right) => live_vars(left) ++ live_vars(right)
  case Compare(left, _, operands) => live_vars(left) ++ operands.flatMap(live_vars(_))
  case PyList(elems) => elems.flatMap(live_vars(_)).toSet
  case PySet(elems) => elems.flatMap(live_vars(_)).toSet
  case PyDict(keys, values) => (keys++values).flatMap(live_vars(_)).toSet
  case IfExp(test, body, orelse) => List(test, body, orelse).flatMap(live_vars(_)).toSet

def getTargetVars(e: TExpr): Set[String] = e match
  case Name(n) => Set(n)
  case _: Attribute => Set()
  case _: Subscript => Set()

def getReadVarsInTgt(e: TExpr): Set[String] = e match
  case Name(_) => Set()
  case Attribute(t, _) => live_vars(t)
  case Subscript(v, indices) => live_vars(v) ++ indices.flatMap(live_vars(_))

def live_vars(cs: List[Stmt], out: Set[String]): Set[String] = cs match
  case Nil => out
  case ::(head, next) =>
    val value = live_vars(next, out)
    live_vars(head, value)

def live_vars(s: Stmt, out: Set[String]): Set[String] = s match
  case Expr(e) => out ++ live_vars(e)
  case Assert(test, msg) =>
    live_vars(test) ++ msg.map(live_vars(_)).getOrElse(Set.empty)
  case Assign(tgt, vl) =>
    val kill = getTargetVars(tgt)
    val gen = getReadVarsInTgt(tgt) ++ live_vars(vl)
    (out -- kill) ++ gen
  case AnnAssign(tgt, _, value) =>
    val kill = getTargetVars(tgt)
    val gen = getReadVarsInTgt(tgt) ++ value.map(live_vars(_)).getOrElse(Set.empty)
    (out -- kill) ++ gen
  case If(test, body, orelse) =>
    val bodyLVs = live_vars(body, out)
    val orelseLVs = live_vars(orelse, out)
    live_vars(test) ++ bodyLVs ++ orelseLVs
  case While(test, body, _) =>
    var curr: Set[String] = out
    while {
      val prev = curr
      val bodyLVs = live_vars(body, curr)
      curr = bodyLVs ++ out ++ live_vars(test)
      prev != curr
    } do {}
    curr
  case _: Break => out // todo
