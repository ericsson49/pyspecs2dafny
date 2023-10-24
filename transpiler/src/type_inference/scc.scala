package type_inference

import scala.collection.mutable


def findSCCsKosaraju[N](graph: Map[N, Set[N]]): Map[N, N] =
  val L = mutable.ListBuffer[N]()
  val visited = mutable.Set[N]()
  def visit(u: N): Unit =
    if !visited.contains(u) then
      visited.add(u)
      for v <- graph(u) do
        visit(v)
      L.prepend(u)
  val nodes = graph.keySet.union(graph.values.toSet.flatten)
  for n <- nodes do
    visit(n)
  val revGraph: Map[N,Set[N]] = graph.toSet.flatMap((k, vs) => vs.map(_ -> k)).groupMap(_._1)(_._2)
  val sccs = mutable.Map[N, N]()
  def assign(u: N, root: N): Unit =
    if !sccs.contains(u) then
      sccs(u) = root
      for v <- revGraph.getOrElse(u, Set.empty) do
        assign(v, root)
  for u <- L do
    assign(u, u)
  sccs.toMap


def findSCCsPathBased[N](graph: Map[N, Set[N]]): Map[N, N] =
  val S = mutable.Stack[N]()
  val P = mutable.Stack[N]()
  val preorder = mutable.Map[N,Int]()
  val sccs = mutable.Map[N, N]()
  def dfs(v: N): Unit =
    preorder(v) = preorder.size
    S.push(v)
    P.push(v)
    for w <- graph(v) do
      preorder.get(w) match
        case None => dfs(w)
        case Some(preW) if !sccs.contains(w) => // w in the SCC of v
          P.popWhile(preorder(_) > preW)
        case _ => // w belongs to another SCC
    if v == P.top then
      val newSCC = S.popWhile(_ != v) :+ S.pop()
      sccs.addAll(newSCC.map(_ -> v))
      P.pop()
  val nodes = graph.keySet //.union(graph.values.flatten)
  for n <- nodes if !preorder.contains(n) do
    dfs(n)
  sccs.toMap
@main def testFindSCCs() =
  val graph = Set(0 -> 1, 1 -> 2, 2 -> 3, 3 -> 4, 4 -> 2).groupMap(_._1)(_._2)
  println(findSCCsPathBased(graph))
  println(findSCCsKosaraju(graph))
  val graph2 = Set(0 -> 1, 1 -> 2, 2 -> 1, 1 -> 0).groupMap(_._1)(_._2)
  println(findSCCsPathBased(graph2))
  println(findSCCsKosaraju(graph2))
