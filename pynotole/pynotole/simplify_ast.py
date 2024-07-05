import ast
import astor
from functools import partial
from .simplify import FreshVars, Rewriting, rewrite


def _flatten(elems):
    res = []
    for elem in elems:
        if isinstance(elem, list):
            res.extend(elem)
        else:
            res.append(elem)
    return res


class AstRewriting(Rewriting):
    @classmethod
    def compare_nodes(self, a, b) -> bool:
        return astor.dump_tree(a) == astor.dump_tree(b)

    @classmethod
    def _compare_seq(cls, ls1: list, ls2: list) -> bool:
        if len(ls1) != len(ls2):
            return False
        for e1, e2 in zip(ls1, ls2):
            if not cls.compare_nodes(e1, e2):
                return False
        return True

    @classmethod
    def some(cls, rule):
        def res(s):
            match s:
                case [stmt, *stmts]:
                    stmt_ = rule(stmt)
                    stmts_ = res(stmts)
                    if stmt_ is not None or stmts_ is not None:
                        r1 = stmt_ if stmt_ is not None else stmt
                        r2 = stmts_ if stmts_ is not None else stmts
                        stmts__ = _flatten([r1] + r2)
                        if not cls._compare_seq([stmt] + stmts, stmts__):
                            return stmts__
                case ast.If(test, body, orelse):
                    body_ = res(body)
                    orelse_ = res(orelse)
                    if body_ is not None or orelse_ is not None:
                        return ast.If(test=test,
                                      body=body_ if body_ is not None else body,
                                      orelse=orelse_ if orelse_ is not None else orelse)
                case ast.While(test, body, []):
                    body_ = res(body)
                    if body_ is not None:
                        return ast.While(test=test, body=body_, orelse=[])
        return res

    @classmethod
    def all(cls, rule):
        def f(s):
            match s:
                case [*stmts]:
                    stmts_ = list(map(rule, stmts))
                    if all(st is not None for st in stmts_):
                        return _flatten(stmts_)
                case ast.While(test, body, []):
                    body_ = f(body)
                    if body_ is not None:
                        return ast.While(test, body_, [])
                case ast.If(test, body, orelse):
                    body_ = f(body)
                    orelse_ = f(orelse)
                    if body_ is not None or orelse_ is not None:
                        return ast.If(test,
                                      body_ if body_ is not None else body,
                                      orelse_ if orelse_ is not None else orelse)
                case _:
                    return s
        return f


def simplify_ast_rule(fvs, s):
    def is_func_like(s):
        return isinstance(s, (ast.Call, ast.BinOp, ast.Attribute, ast.Subscript))

    def extract_func_like_args(s):
        match s:
            case ast.Call(ast.Attribute(value, _), args, kwargs):
                return [value] + list(args) + [kw.value for kw in kwargs]
            case ast.Call(_, args, kwargs):
                return list(args) + [kw.value for kw in kwargs]
            case ast.BinOp(left, _, right):
                return [left, right]
            case ast.Attribute(value, _):
                return [value]
            case ast.Subscript(value, idx):
                return [value, idx]
            case _:
                assert False

    def replace_func_like_args(s, args):
        match s:
            case ast.Call(ast.Attribute(_, attr)) as call:
                assert len(args) == 1 + len(call.args) + len(call.keywords)
                value_ = args[0]
                args_ = args[1:1 + len(call.args)]
                kwds_ = [ast.keyword(kw.arg, arg) for arg, kw in zip(args[1 + len(call.args):])]
                return ast.Call(func=ast.Attribute(value_, attr), args=args_, keywords=kwds_)
            case ast.Call() as call:
                assert len(args) == len(call.args) + len(call.keywords)
                args_ = args[:len(call.args)]
                kwds_ = [ast.keyword(kw.arg, arg) for arg, kw in zip(args[len(call.args):])]
                return ast.Call(func=call.func, args=args_, keywords=kwds_)
            case ast.BinOp(_, op, _):
                assert len(args) == 2
                return ast.BinOp(args[0], op, args[1])
            case ast.Attribute(_, attr, ctx):
                assert len(args) == 1
                return ast.Attribute(args[0], attr, ctx)
            case ast.Subscript(_, _):
                assert len(args) == 2
                return ast.Subscript(args[0], args[1])
            case _:
                assert False

    match s:
        case ast.Assign([tgt], value) if not isinstance(tgt, ast.Name) and not isinstance(value, ast.Name):
            fn = fvs.fresh()
            return [
                ast.Assign([ast.Name(fn,ast.Store())], value),
                ast.Assign([tgt], ast.Name(fn, ast.Load()))
            ]
        case ast.Assign([ast.Name() as tgt], value) \
            if is_func_like(value) \
               and any(not isinstance(arg, ast.Name) for arg in extract_func_like_args(value)):
            assns = []

            def process_arg(arg):
                if not isinstance(arg, ast.Name):
                    fn = fvs.fresh()
                    assns.append(ast.Assign([ast.Name(fn, ast.Store())], arg))
                    return ast.Name(fn, ast.Load())
                else:
                    return arg

            args_ = list(map(process_arg, extract_func_like_args(value)))
            return assns + [
                ast.Assign([tgt], replace_func_like_args(value, args_))
            ]
        case ast.Assign([ast.Name() as tgt], ast.IfExp(test, body, orelse)) if not isinstance(test, ast.Name):
            fn = fvs.fresh()
            return [
                ast.Assign([ast.Name(fn, ast.Store())], test),
                ast.Assign([tgt], ast.IfExp(ast.Name(fn, ast.Load()), body, orelse))
            ]
        case ast.Assign([ast.Name() as tgt], ast.IfExp(ast.Name() as test, body, orelse)):
            return ast.If(test,
                          [ast.Assign([tgt], body)],
                          [ast.Assign([tgt], orelse)])
        case ast.Assign([ast.Attribute(value, attr)], ast.Name() as v) if not isinstance(value, ast.Name):
            fn = fvs.fresh()
            return [
                ast.Assign([ast.Name(fn, ast.Store())], value),
                ast.Assign([ast.Attribute(ast.Name(fn, ast.Load()), attr)], v)
            ]
        case ast.Assign([ast.Subscript(value, index)], ast.Name() as v) if not isinstance(value, ast.Name):
            fn = fvs.fresh()
            return [
                ast.Assign([ast.Name(fn, ast.Store())], value),
                ast.Assign([ast.Subscript(ast.Name(fn, ast.Load()), index)], v)
            ]
        case ast.Assign([ast.Subscript(ast.Name() as value, index)], ast.Name() as v) if not isinstance(index, ast.Name):
            fn = fvs.fresh()
            return [
                ast.Assign([ast.Name(fn, ast.Store())], index),
                ast.Assign([ast.Subscript(value, ast.Name(fn, ast.Load()))], v)
            ]
        case ast.Expr(value) \
            if is_func_like(value) \
               and any(map(lambda arg: not isinstance(arg, ast.Name), extract_func_like_args(value))):
            assns = []

            def process_arg(arg):
                if not isinstance(arg, ast.Name):
                    fn = fvs.fresh()
                    assns.append(ast.Assign([ast.Name(fn, ast.Store())], arg))
                    return ast.Name(fn, ast.Load())
                else:
                    return arg

            args_ = list(map(process_arg, extract_func_like_args(value)))
            return assns + [
                ast.Expr(replace_func_like_args(value, args_))
            ]


def simplify_ast(n):
    fvs = FreshVars()
    rule = AstRewriting.reduce(partial(simplify_ast_rule, fvs))
    return rewrite(rule, n)
