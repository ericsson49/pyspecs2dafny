package onotole_scala

import ast.{Stmt, TExpr}
import ast.Stmt.*
import ast.TExpr.*

import scala.annotation.targetName
import scala.collection.immutable.List


def semTL2(tl: ast.TopLevelDef)(using fv: (String) => String): ast.TopLevelDef =
  val pRule: PartialFunction[Stmt, List[Stmt]] = orderedChoice(List(
    annoRule,
    forRule,
    mkRuleP(simplifyExprRule),
    mkRuleP(generatorRule),
    mkPartial(mkRule(inlineFuncs)),
    flattenStmtRule(_)
  ))
  evalTL(tl, pRule.orElse(s => List(s)))

def evalTL(tl: ast.TopLevelDef, rule: Stmt => List[Stmt]): ast.TopLevelDef = tl match
  case f: FunctionDef => f.copy(body = f.body.flatMap(eval(_, rule)))
  case _ => tl

def eval(s: Stmt, rule: Stmt => List[Stmt]): List[Stmt] =
  val res = evalStep(s, rule)
  if res == List(s) then
    res
  else
    res.flatMap(eval(_, rule))

def evalStep(s: Stmt, rule: Stmt => List[Stmt]): List[Stmt] =
  val head = rule(s)
  if head != List(s) then
    head
  else
    List(evalChildren(s, s => evalStep(s, rule)))

def evalChildren(s: Stmt, rule: Stmt => List[Stmt]): Stmt =
  s match
    case ifS: If =>
      ifS.copy(body = ifS.body.flatMap(rule), orelse = ifS.orelse.flatMap(rule))
    case whS: While =>
      whS.copy(body = whS.body.flatMap(rule))
    case forS: For =>
      forS.copy(body = forS.body.flatMap(rule))
    case _ => s

def orderedChoice(rules: List[PartialFunction[Stmt, List[Stmt]]]): PartialFunction[Stmt, List[Stmt]] = rules match
  case rule :: Nil => rule
  case rule :: rest => rule.orElse(orderedChoice(rest))
def mkRule(rule: PartialFunction[Stmt, List[Stmt]])(s: Stmt): List[Stmt] =
  rule.applyOrElse(s, s => List(s))

def mkRuleP(rule: PartialFunction[TExpr, TExpr]): PartialFunction[Stmt, List[Stmt]] =
  val ruleF: TExpr => TExpr = e => rule.applyOrElse(e, identity)
  val rule2 = mkRuleF(ruleF)
  mkPartial(rule2)

def mkPartial(rule: Stmt => List[Stmt]): PartialFunction[Stmt, List[Stmt]] =
  val optRule: Stmt => Option[List[Stmt]] = s =>
    val res = rule.apply(s)
    if res == List(s) then None
    else Some(res)
  optRule.unlift

def mkRule(rule: PartialFunction[TExpr, TExpr]): Stmt => List[Stmt] =
  val ruleF: TExpr => TExpr = e => rule.applyOrElse(e, identity)
  mkRuleF(ruleF)

def mkRuleF(rule: TExpr => TExpr): Stmt => List[Stmt] =
  mkRuleF(e => List() -> rule(e))

@targetName("mkRuleExpr")
def mkRule(rule: PartialFunction[TExpr, (List[Stmt], TExpr)])(s: Stmt): List[Stmt] =
  val (subExprs, assembler) = getSubExpressions(s)
  val (stmts, resExprs) = subExprs.map(e => rule.applyOrElse(e, _ => List() -> e)).unzip
  stmts.flatten ++ List(assembler.apply(resExprs))

def mkRuleF(rule: TExpr => (List[Stmt], TExpr))(s: Stmt): List[Stmt] =
  val (subExprs, assembler) = getSubExpressions(s)
  val (stmts, resExprs) = subExprs.map(rule).unzip
  stmts.flatten ++ List(assembler.apply(resExprs))
