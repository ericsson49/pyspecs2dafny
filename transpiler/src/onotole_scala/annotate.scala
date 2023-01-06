package onotole_scala


import ast.{Stmt, TopLevelDef}
import ast.Stmt.*


def annotate(cs: List[Stmt]): List[Stmt] = cs.map(annotate(_))
def annotate(s: Stmt): Stmt = s match
  case wS: While =>
    val annos = wS.body.takeWhile(_.isInstanceOf[Annotation]).map(_.asInstanceOf[Annotation])
    val bodyWithoutAnnos = wS.body.dropWhile(_.isInstanceOf[Annotation])
    val newBody = annotate(bodyWithoutAnnos)
    wS.copy(body = newBody, anno = wS.anno ++ annos)
  case ifS: If =>
    ifS.copy(body = annotate(ifS.body), orelse = annotate(ifS.orelse))
  case forS: For =>
    forS.copy(body = annotate(forS.body))
  case _ => s

def annotateTopLevel(tl: TopLevelDef): TopLevelDef = tl match
  case f: FunctionDef => f.copy(body = annotate(f.body))
  case _ => tl