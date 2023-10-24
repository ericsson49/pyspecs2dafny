from myast import Stmt, Expr, Block, Comprehension
from myast import (Assert, VarDecl, AnnAssign, AssignStmt, AugAssign, ExprStmt, IfStmt, WhileStmt, ForStmt, Return, Break,
                   Continue, Pass)
from myast import NameConst, Num, Str,Bytes, Name, FuncCall, GeneratorExpr, ListCompr, SetCompr, \
    BinOp, Compare, BoolOp, UnaryOp, \
    Attribute, Subscript, PyTuple, PyList, PySet, PyDict, IfExp, Lambda, Starred


def expr_to_str(e: Expr) -> str:
    match e:
        case NameConst(v):
            return f"{v}"
        case Num(n):
            return str(n)
        case Str(s):
            return '"""' + s + '"""'
        case Bytes(b):
            return str(b)
        case Name(v):
            return v
        case Attribute(value, attr):
            return f"{expr_to_str(value)}.{attr}"
        case Subscript(value, (lower, upper, step)):
            l = expr_to_str(lower) if lower is not None else ''
            u = expr_to_str(upper) if upper is not None else ''
            st = f":{expr_to_str(step)}" if step is not None else ''
            return f"{expr_to_str(value)}[{l}:{u}{st}]"
        case Subscript(value, idx):
            return f"{expr_to_str(value)}[{expr_to_str(idx)}]"
        case BinOp(left, op, right):
            return f"{expr_to_str(left)}{op}{expr_to_str(right)}"
        case BoolOp(op, values):
            return (f" {op} ").join(expr_to_str(v) for v in values)
        case Compare(left, ops, rights):
            return expr_to_str(left) + (''.join([f" {op} {expr_to_str(right)}" for op, right in zip(ops, rights)]))
        case UnaryOp(op, value):
            return f"{op} {expr_to_str(value)}"
        case FuncCall(func, args, kwds):
            _args = [expr_to_str(a) for a in args]
            _kwds = [f"{k}={expr_to_str(v)}" for k, v in kwds]
            return f"{expr_to_str(func)}({', '.join(_args + _kwds)})"
        case GeneratorExpr(v,Comprehension(iter, coll, comp_if)):
            comp_if_ = f" if {expr_to_str(comp_if)}" if comp_if else ""
            return f"{expr_to_str(v)} for {expr_to_str(iter)} in {expr_to_str(coll)}{comp_if_}"
        case ListCompr(v, compr):
            return f"[{expr_to_str(GeneratorExpr(v, compr))}]"
        case SetCompr(v, compr):
            return f"{{{expr_to_str(GeneratorExpr(v, compr))}}}"
        case PyTuple(elts):
            return f"({','.join([expr_to_str(elt) for elt in elts])})"
        case PyList(elts):
            return '[' + (', '.join(expr_to_str(elt) for elt in elts)) + ']'
        case PyDict(keys, values):
            return '{' + (', '.join(f"{expr_to_str(k)}: {expr_to_str(v)}" for k, v in zip(keys, values))) + '}'
        case IfExp(test, body, orelse):
            return f"{expr_to_str(body)} if {expr_to_str(test)} else {expr_to_str(orelse)}"
        case Lambda(args, body):
            return f"lambda {','.join(args)}: {expr_to_str(body)}"
        case Starred(value):
            return f"*{expr_to_str(value)}"
        case _:
            assert False, e


def print_st(st: Stmt|Block, indent=''):
    match st:
        case Assert(test, None):
            print(f"{indent}assert {expr_to_str(test)}")
        case Assert(test, msg):
            print(f"{indent}assert {expr_to_str(test)}, {expr_to_str(msg)}")
        case AssignStmt(Name(v), value):
            print(f"{indent}{v} = {expr_to_str(value)}")
        case AssignStmt(t, v):
            print(f"{indent}{expr_to_str(t)} = {expr_to_str(v)}")
        case AnnAssign(Name(t), anno, None):
            print(f"{indent}{t}: {anno}")
        case AnnAssign(Name(t), anno, value):
            print(f"{indent}{t}: {anno} = {expr_to_str(value)}")
        case ExprStmt(val):
            print(f"{indent}{expr_to_str(val)}")
        case VarDecl(vt, t, None):
            print(f"{indent}{vt}: {t or '_'} # var_decl")
        case VarDecl(vt, t, v):
            print(f"{indent}{vt}: {t or '_'} = {expr_to_str(v)} # var_decl")
        case AugAssign(t, op, v):
            print(f"{indent}{expr_to_str(t)} {op}= {expr_to_str(v)}")
        case IfStmt(t, b, e):
            print(f"{indent}if {expr_to_str(t)}:")
            print_st(b, indent + '  ')
            if len(e.stmts) > 0:
                print(f"{indent}else:")
                print_st(e, indent + '  ')
        case WhileStmt(t, b):
            print(f"{indent}while {expr_to_str(t)}:")
            print_st(b, indent + '  ')
        case ForStmt(iter, coll, body):
            print(f"{indent}for {expr_to_str(iter)} in {expr_to_str(coll)}:")
            print_st(body, indent + '  ')
        case Return(None):
            print(f"{indent}return")
        case Return(v):
            print(f"{indent}return {expr_to_str(v)}")
        case Block(stmts):
            for st in stmts:
                print_st(st, indent)
        case Break():
            print(f"{indent}break")
        case Continue():
            print(f"{indent}continue")
        case Pass():
            print(f"{indent}pass")
        case _:
            assert False, st

