package onotole_scala

import ast.TExpr
import ast.TExpr._
import utest._
object FlattenExprsTests extends TestSuite {

  val tests = Tests {
    def mkFreshVarsF(): () => String =
      var num: Int = 0
      () =>
        val name = "tmp_" + num
        num = num + 1
        name

    test("simple call") {
      val expr0 = Call(Name("a"), List(Num(1), Num(2)))
      val expectedAssgns0 = List(("tmp_0", Num(1)), ("tmp_1", Num(2)))
      val expectedRes0 = Call(Name("a"), List(Name("tmp_0"), Name("tmp_1")))
      val (assgns0, res0) = flattenExprs(expr0)(using mkFreshVarsF())
      assgns0 ==> expectedAssgns0
      res0 ==> expectedRes0
    }
    test("nested calls") {
      val expr0 = Call(Name("a"), List(Call(Name("b"), List(Num(1))), Num(2)))
      val expectedAssgns0 = List(
        ("tmp_2", Num(1)),
        ("tmp_0", Call(Name("b"), List(Name("tmp_2")))),
        ("tmp_1", Num(2)))
      val expectedRes0 = Call(Name("a"), List(Name("tmp_0"), Name("tmp_1")))
      val (assgns0, res0) = flattenExprs(expr0)(using mkFreshVarsF())
      res0 ==> expectedRes0
      assgns0 ==> expectedAssgns0
      val assgnAcc = scala.collection.mutable.ListBuffer[(String, TExpr)]()
      val res = flattenExprs2(expr0, assgnAcc)(using mkFreshVarsF())
      assgnAcc.toList ==> List(
        "tmp_0" -> Num(1),
        "tmp_1" -> Call(Name("b"),List(Name("tmp_0"))),
        "tmp_2" -> Num(2)
      )
      res ==> Call(Name("a"),List(Name("tmp_1"),Name("tmp_2")))
    }

  }
}
