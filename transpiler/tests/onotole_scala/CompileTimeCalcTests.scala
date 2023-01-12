package onotole_scala

import ast.TExpr
import ast.CTVal
import utest._

object CompileTimeCalcTests extends TestSuite {
  val tests = utest.Tests {
    val ctxData = Map(
      "pylib" -> CTVal.PkgVal("pylib"),
      "pylib.int" -> CTVal.ClassVal("pylib.int")
    )
    val ctx = SimpleNameCtx(ctxData, Set("a"))
    given NameCtx = ctx
    test("package") {
      assert(check_ctv(TExpr.Name("pylib")) contains CTVal.PkgVal("pylib"))
    }
    test("package_class") {
      val expr = TExpr.Attribute(TExpr.Name("pylib"), "int")
      assert(check_ctv(expr) contains CTVal.ClassVal("pylib.int"))
    }
    test("package_class_func") {
      val expr = TExpr.Attribute(TExpr.Attribute(TExpr.Name("pylib"), "int"), "from_bytes")
      assert(check_ctv(expr)  contains CTVal.FuncInst("pylib.int::from_bytes"))
    }
    test("var") {
      assert(check_ctv(TExpr.Name("a")).isEmpty)
    }
    test("missing_var") {
      intercept[NoSuchElementException](check_ctv(TExpr.Name("aa")))
    }
  }
}