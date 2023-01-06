package onotole_scala

import ast.{Stmt, TExpr}
import ast.Stmt.*
import ast.TExpr.*

import scala.collection.immutable.List

def sem(e: TExpr): (List[Stmt], TExpr) = e match
  case _: NameConstant => (List(), e)
  case _: Num => (List(), e)
  case _: Name => (List(), e)
  case UnaryOp(op, operand) =>
    val (assgns, res) = sem(operand)
    (assgns, UnaryOp(op, res))
  case BinOp(left, op, right) =>
    val (leftAssgns, leftE) = sem(left)
    val (rightAssgns, rightE) = sem(right)
    (leftAssgns ++ rightAssgns, BinOp(leftE, op, rightE))
  case Compare(left, ops, operands) =>
    val (leftAssgns, leftE) = sem(left)
    if ops.length != 1 || operands.length != 1 then ???
    val (operandAssgns, resOperands) = operands.map(sem(_)).unzip
    (leftAssgns ++ operandAssgns.flatten, Compare(leftE, ops, resOperands))
  case Call(func, args, kwds) =>
    val (fAssgns, fv) = sem(func)
    val (argsAssgns_, argVals) = args.map(sem).unzip
    val argsAssgns = argsAssgns_.flatten
    val (kwdAssgns_, kwdVals) = kwds.map(kwd => {
      val (assgns, res) = sem(kwd._2)
      (assgns, (kwd._1, res))
    }).unzip
    val kwdAssgns = kwdAssgns_.flatten
    (fAssgns ++ argsAssgns ++ kwdAssgns, Call(fv, argVals, kwdVals))
  case Attribute(value, attr) =>
    val (assgns, res) = sem(value)
    (assgns, Attribute(res, attr))
  case Subscript(value, indics) =>
    val (valAssgns, resV) = sem(value)
    val (idxAssgns, idxV) = indics.map(sem(_)).unzip
    (valAssgns ++ (idxAssgns.flatten), Subscript(resV, idxV))

def sem2(e: TExpr, newVar: Boolean)(using fv: () => String): (List[Stmt], TExpr) =
  val (assgns, resE) = flattenExprs(e)
  val (assgns2, resE2) = if newVar then introNewVar(resE) else (List.empty, resE)
  (assgns ++ assgns2).map((v,e) => Assign(Name(v), e)) -> resE2
def sem(cs: List[Stmt])(using fv: () => String): List[Stmt] = cs.flatMap(sem)
def sem(s: Stmt)(using fv: () => String): List[Stmt] = s match
  case Expr(e) =>
    val (assgns, res) = sem2(e, false)
    assgns ++ List(Expr(res))
  case Assert(test, msg) =>
    val (testAssgns, testE) = sem2(test, true)
    if (msg.isDefined) ???
    testAssgns ++ List(Assert(testE, msg))
  case Assign(tgt, vl) =>
    val (tgtAssgns, tgtE) = sem2(tgt, false)
    val (valAssgns, valE) = sem2(vl, false)
    tgtAssgns ++ valAssgns ++ List(Assign(tgtE, valE))
  case AnnAssign(tgt, anno, value) =>
    val (tgtAssgns, tgtE) = sem2(tgt, false)
    val (valAssgns, valE) =
      if value.isDefined then
        val (assgns, res) = sem2(value.get, false)
        assgns -> Some(res)
      else (List(), None)
    tgtAssgns ++ valAssgns ++ List(AnnAssign(tgtE, anno, valE))
  case If(test, body, orelse) =>
    val (testAssgns, testE) = sem2(test, true)
    testAssgns ++ List(If(testE, sem(body), sem(orelse)))
  case While(test, body, anno) =>
    val (testAssgns, testE) = sem2(test, true)
    val newBody = sem(body)
    if testAssgns.isEmpty then
      List(While(testE, newBody, anno))
    else
      List(While(NameConstant(Some(true)),
        testAssgns ++
        List(If(testE, newBody, List(Break()))),
        anno
      ))
  case For(Name(v), iter, body) =>
    val (iterAssgns, newIter) = sem2(iter, true)
    val iterVar = fv()
    val newBody = Assign(Name(v), Call(Name("next"), List(Name(iterVar)))) :: sem(body)
    val loop = While(Call(Name("has_next"), List(Name(iterVar))), newBody, List(Annotation(s"@decreases $iterVar.decreases_")))
    iterAssgns ++ List(Assign(Name(iterVar), Call(Name("iter"), List(newIter))), loop)

  case Return(None) => List(s)
  case Return(Some(v)) =>
    val (assgns, res) = sem2(v, true)
    assgns ++ List(Return(Some(res)))

def semTL(tl: ast.TopLevelDef)(using fv: () => String): ast.TopLevelDef = tl match
  case f: FunctionDef => f.copy(body = sem(f.body))
  case _ => tl