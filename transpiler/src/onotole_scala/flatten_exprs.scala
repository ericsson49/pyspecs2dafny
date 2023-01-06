package onotole_scala

import ast.TExpr
import ast.TExpr.*

import scala.collection.mutable

def getShallowSubexprs(e: TExpr): List[TExpr] = e match
  case _ if simplifyStep.isDefinedAt(e) => getShallowSubexprs(simplifyStep(e))
  case _: (Num|Str|NameConstant|Name) => List.empty
  case Call(f, args, kwds) => List(f) ++ args ++ kwds.unzip._2
  case Tuple(elems) => elems
  case PyList(elems) => elems
  case PySet(elems) => elems
  case PyDict(keys, values) => keys ++ values

def getDeepSubexprs(e: TExpr): List[TExpr] =
  getShallowSubexprs(e).flatMap(it => it :: getDeepSubexprs(it))

def bottomUp(e: TExpr)(using transform: TExpr => TExpr): TExpr =
  val (subExprs, assembler) = destr(e)
  transform(assembler(subExprs.map(bottomUp(_))))

def topDown(e: TExpr)(using transform: TExpr => TExpr): TExpr =
  val (subExprs, assembler) = destr(transform(e))
  assembler(subExprs.map(topDown(_)))

def destr[T <: TExpr](e: T): (List[TExpr], List[TExpr] => T) = e match
  //case _ if simplifyStep.isDefinedAt(e) => ??? // simplify first?
  case _: (Num|Str|NameConstant|Name) => (List.empty, _ => e)
  case PyList(elems) => (elems, elems => PyList(elems).asInstanceOf[T])
  case PySet(elems) => (elems, elems => PySet(elems).asInstanceOf[T])
  case PyDict(keys, values) =>
    val elems = keys.zip(values).map((k,v) => List(k, v)).flatten
    (elems, elems => {
      val withIndex = elems.zipWithIndex
      val (keys, values) = withIndex.partition(_._2 % 2 == 0)
      PyDict(keys.unzip._1, values.unzip._1).asInstanceOf[T]
    })
  case UnaryOp(op, operand) =>
    (List(operand), elems => TExpr.UnaryOp(op, elems(0)).asInstanceOf[T])
  case BinOp(left, op, right) =>
    (List(left, right), elems => TExpr.BinOp(elems(0), op, elems(1)).asInstanceOf[T])
  case Compare(left, op :: Nil, right :: Nil) =>
    (List(left, right), elems => TExpr.Compare(elems(0), op :: Nil, elems(1) :: Nil).asInstanceOf[T])
  case Call(func, args, kwds) =>
    (List(func) ++ args ++ kwds.unzip._2, elems => {
      val funcR = elems.head
      val (argsR, kwdsR) = elems.tail.splitAt(args.length)
      TExpr.Call(funcR, argsR, kwds.map(_._1).zip(kwdsR)).asInstanceOf[T]
    })
  case Attribute(value, attr) =>
    (List(value), elems => TExpr.Attribute(elems(0), attr).asInstanceOf[T])
  case Subscript(value, indices) =>
    (List(value) ++ indices, elems => TExpr.Subscript(elems(0), elems.slice(1, elems.size)).asInstanceOf[T])

def introNewVar(e: TExpr)(using freshVarF: () => String): (List[(String, TExpr)], TExpr) =
  if e.isInstanceOf[Name] then
    (List.empty, e)
  else
    val fv = freshVarF()
    (List(fv -> e) -> Name(fv))

def flattenExprsStep(e: TExpr)(using freshVarF: () => String): (List[(String, TExpr)], TExpr) =
  val (subexprs, assembler) = destr(e)
  val (assgns2, res2) = subexprs.map(introNewVar(_)).unzip
  (assgns2.flatten, assembler.apply(res2))

def flattenExprs(e: TExpr)(using freshVarF: () => String): (List[(String, TExpr)], TExpr) =
  val (assgns, res) = flattenExprsStep(e)
  (assgns.flatMap((v, subExp) =>
    val (assgns, aaa) = flattenExprs(subExp)
    assgns ++ List(v -> aaa)
  ), res)

def introNewVar2(e: TExpr, acc: mutable.ListBuffer[(String, TExpr)])(using freshVarF: () => String): TExpr =
  if e.isInstanceOf[Name] then
    e
  else
    val fv = freshVarF()
    acc.append(fv -> e)
    Name(fv)

def flattenExprs2(e: TExpr, acc: mutable.ListBuffer[(String, TExpr)])(using freshVarF: () => String): TExpr =
  val (subExprs, assembler) = destr(e)
  val flattendSEs = subExprs.map(it => introNewVar2(flattenExprs2(it, acc), acc))
  assembler.apply(flattendSEs)