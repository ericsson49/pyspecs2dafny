from lark import Lark, Transformer
from lark.visitors import Interpreter
from lark.indenter import PythonIndenter
from myast import AST, Stmt, Expr, Block, Comprehension
from myast import IfStmt, WhileStmt, AssignStmt, VarDecl, ExprStmt
from myast import  NameConst, Num, Name, FuncCall, GeneratorExpr, ListCompr, SetCompr, DictCompr

kwargs = dict(postlex=PythonIndenter(), start='file_input')
python_parser3 = Lark.open_from_package('lark', 'python.lark', ['grammars'], parser='lalr', **kwargs)

class MyTransformer(Transformer):
    def const_true(self, _):
        return NameConst(True)
    def const_false(self, _):
        return NameConst(False)
    def const_none(self, _):
        return NameConst(None)
    def number(self, n):
        (n,) = n
        if n.startswith('0b'):
            radix = 2
        else:
            radix = 10
        return Num(int(n, radix))
    def name(self, n):
        (n,) = n
        return Name(str(n))
    def var(self, v):
        (v,) = v
        return v
    def argvalue(self, args):
        match args:
            case [Name(vn), vl]:
                return (vn, vl)
            case _:
                assert False
    def arguments(self, args):
        pos_args = []
        kw_args = []
        for a in args:
            match a:
                case (vn, vl):
                    kw_args.append(a)
                case v if len(kw_args) == 0:
                    pos_args.append(v)
        return tuple(pos_args), tuple(kw_args)
    def funccall(self, args):
        func, fargs = args
        match fargs:
            case None:
               return FuncCall(func, [], [])
            case (pargs, kwargs):
                return FuncCall(func, pargs, kwargs)
            case _:
                assert False
    def comp_for(self, args):
        (async_, iter, coll) = args
        assert async_ is None
        return Comprehension(iter, coll, None)
    def comp_fors(self, args):
        assert len(args) == 1
        return args[0]
    def comprehension(self, args):
        (expr, comp_for, comp_if) = args
        return GeneratorExpr(expr, Comprehension(comp_for.iter, comp_for.coll, comp_if))
    def list_comprehension(self, args):
        (gen_compr), = args
        return ListCompr(gen_compr.expr, gen_compr.compr)
    def set_comprehension(self, args):
        (gen_compr), = args
        return SetCompr(gen_compr.expr, gen_compr.compr)
    def key_value(self, args):
        key, value = args
        return key, value
    #def dict_comprehension(self, args):
    #    (gen_compr), = args
    #    key, value = gen_compr.expr
    #    return DictCompr(key, value, gen_compr.compr)

    def suite(self, args):
        return Block(args)
    def assign_stmt(self, args):
        (stmt,) = args
        return stmt
    def assign(self, args):
        tgt, val = args
        return AssignStmt(tgt, val)
    def annassign(self, args):
        (vn, vt, val) = args
        match vn:
            case Name(n):
                return VarDecl(n, vt, val)
            case _:
                assert False
    def expr_stmt(self, args):
        (v,) = args
        return ExprStmt(v)
    def elif_(self, args):
        (test, body) = args
        return test, body
    def if_stmt(self, args):
        (test, body, elifs, or_else) = args
        curr_else = or_else
        for elif_ in elifs.children:
            elif_test, elif_body = elif_
            curr_else = IfStmt(elif_test, elif_body, curr_else)
        return IfStmt(test,body, curr_else)
    def while_stmt(self, args):
        (test, body, or_else) = args
        assert or_else is None
        return WhileStmt(test, body)

    def classdef(self, args):
        (name, bases, body) = args
        print(name, bases, body)
        return None

    def file_input(self, args):
        return Block(args)


def parse_py(text):
    parsed_tree = python_parser3.parse(text)
    transformed = MyTransformer().transform(parsed_tree)
    return transformed
