package onotole_scala

import ast.Stmt
import ast.TExpr

def new_defs_and_updates(cs: List[Stmt], knownVars: Set[String], outLVs: Set[String]): (Set[String], Set[String]) = cs match
  case Nil => (Set(), Set())
  case ::(s, rest) =>
    val lvsAfter = live_vars(rest, outLVs)
    val (sNewDefs, sUpdates) = new_defs_and_updates(s, knownVars, lvsAfter)
    val (restNewDefs, restUpdates) = new_defs_and_updates(rest, knownVars ++ sNewDefs, outLVs)
    ((sNewDefs ++ restNewDefs) & outLVs, sUpdates ++ (restUpdates -- sNewDefs))

def new_defs_and_updates(s: Stmt, knownVars: Set[String], outLVs: Set[String]): (Set[String], Set[String]) =
  s match
    case _: (Stmt.Annotation|Stmt.Expr|Stmt.Assert|Stmt.Break|Stmt.Return) => (Set.empty, Set.empty)
    case Stmt.Assign(tgt, _) =>
      val updatedLVs = getTargetVars(tgt)
      val newDes = updatedLVs -- knownVars
      val updated = updatedLVs.intersect(knownVars)
      (newDes, updated)
    case Stmt.AnnAssign(tgt, _, _) =>
      val updatedLVs = getTargetVars(tgt)
      val newDes = updatedLVs -- knownVars
      val updated = updatedLVs.intersect(knownVars)
      (newDes, updated)
    case Stmt.If(_, body, orelse) =>
      val (bodyDefs, bodyUpdates) = new_defs_and_updates(body, knownVars, outLVs)
      val (orelseDefs, orelseUpdates) = new_defs_and_updates(orelse, knownVars, outLVs)
      if (bodyDefs != orelseDefs)
        val (bodyDefs, bodyUpdates) = new_defs_and_updates(body, knownVars, outLVs)
        ???
      (bodyDefs, bodyUpdates ++ orelseUpdates)
    case Stmt.While(_, body, _) =>
      val (bodyDefs, bodyUpdates) = new_defs_and_updates(body, knownVars, outLVs)
      if (bodyDefs.nonEmpty) ???
      (Set(), bodyUpdates)

def inferVarDecls(cs: List[Stmt], knownVars: Set[String], outLVs: Set[String]): List[Stmt] = cs match
  case Nil => Nil
  case ::(s, rest) =>
    val liveVarsAfterS = live_vars(rest, outLVs)
    val (varDecls, resStmt) = inferVarDecls(s, knownVars, liveVarsAfterS)
    varDecls ++ (resStmt ++ inferVarDecls(rest, knownVars ++ varDecls.map(_.v), outLVs))

def inferVarDecls(s: Stmt, knownVars: Set[String], outLVs: Set[String]): (List[Stmt.VarDecl], List[Stmt]) =
  val (newVs, _) = new_defs_and_updates(s, knownVars, outLVs)
  val varDecls: List[Stmt.VarDecl] = newVs.toList.map(v => Stmt.VarDecl(v, None, None))
  val knownVars_ = knownVars ++ newVs
  s match
    case Stmt.Assign(TExpr.Name(n), v) if newVs == Set(n) =>
      List(Stmt.VarDecl(n, None, Some(v)).asInstanceOf[Stmt.VarDecl]) -> List.empty
    case Stmt.Assign(TExpr.Name(_), _) => List.empty -> List(s)
    case _: Stmt.Assign => List.empty -> List(s)
    case Stmt.AnnAssign(TExpr.Name(n), anno, v) if newVs == Set(n) =>
      List(Stmt.VarDecl(n, Some(anno), v).asInstanceOf[Stmt.VarDecl]) -> List.empty
    case _: (Stmt.Assign|Stmt.AnnAssign) =>
      ???
    case ifS: Stmt.If =>
      varDecls -> List(ifS.copy(
        body = inferVarDecls(ifS.body, knownVars_, outLVs),
        orelse = inferVarDecls(ifS.orelse, knownVars_, outLVs)))
    case whS: Stmt.While => varDecls -> List(whS.copy(body = inferVarDecls(whS.body, knownVars_, outLVs)))
    case _ => varDecls -> List(s)

def inferVarDecls(f: Stmt.FunctionDef): Stmt.FunctionDef =
  f.copy(body = inferVarDecls(f.body, Set(), Set()))

def inferVarDeclsTL(tl: ast.TopLevelDef): ast.TopLevelDef = tl match
  case f: Stmt.FunctionDef => inferVarDecls(f)
  case _ => tl