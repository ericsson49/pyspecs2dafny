import ast
import myast

from typing import Tuple

from type_parser import parse_type, Cls

def parse_name(name: ast.Name) -> str:
    return name.id


def parse_expr(node: ast.expr) -> myast.Expr:
    if isinstance(node, ast.Constant):
        if node.value is None:
            return myast.NameConst(None)
        if isinstance(node.value, str):
            return myast.Str(node.value)
        elif isinstance(node.value, bytes):
            return myast.Bytes(node.value)
        elif isinstance(node.value, int):
            return myast.Num(int(node.value))
        elif node.value is Ellipsis:
            return myast.NameConst(node.value)
        else:
            assert False, f"{type(node.value)}"
    elif isinstance(node, ast.Name):
        return myast.Name(parse_name(node))
    elif isinstance(node, ast.BinOp):
        return myast.BinOp(parse_expr(node.left), node.op, parse_expr(node.right))
    elif isinstance(node, ast.Compare):
        return myast.Compare(parse_expr(node.left), node.comparators, [parse_expr(comp) for comp in node.comparators])
    elif isinstance(node, ast.BoolOp):
        return myast.BoolOp(node.op, [parse_expr(v) for v in node.values])
    elif isinstance(node, ast.UnaryOp):
        return myast.UnaryOp(node.op, parse_expr(node.operand))
    elif isinstance(node, ast.Call):
        def parse_keyword(node: ast.keyword) -> Tuple[str, myast.Expr]:
            return node.arg, parse_expr(node.value)
        return myast.FuncCall(parse_expr(node.func), [parse_expr(arg) for arg in node.args], [parse_keyword(kwd) for kwd in node.keywords])
    elif isinstance(node, ast.Attribute):
        return myast.Attribute(parse_expr(node.value), node.attr)
    elif isinstance(node, ast.ListComp):
        assert len(node.generators) == 1
        compr = parse_compr(node.generators[0])
        return myast.ListCompr(parse_expr(node.elt), compr)
    elif isinstance(node, ast.GeneratorExp):
        assert len(node.generators) == 1
        compr = parse_compr(node.generators[0])
        return myast.GeneratorExpr(parse_expr(node.elt), compr)
    elif isinstance(node, ast.Subscript):
        if isinstance(node.slice, ast.Slice):
            slice = parse_slice(node.slice)
        else:
            slice = parse_expr(node.slice)
        return myast.Subscript(parse_expr(node.value), slice)
    elif isinstance(node, ast.IfExp):
        return myast.IfExp(parse_expr(node.test), parse_expr(node.body), parse_expr(node.orelse))
    elif isinstance(node, ast.List):
        return myast.PyList([parse_expr(v) for v in node.elts])
    elif isinstance(node, ast.Tuple):
        return myast.PyTuple([parse_expr(e) for e in node.elts])
    elif isinstance(node, ast.Set):
        return myast.PySet([parse_expr(e) for e in node.elts])
    elif isinstance(node, ast.Dict):
        return myast.PyDict([parse_expr(e) for e in node.keys], [parse_expr(e) for e in node.values])
    elif isinstance(node, ast.Lambda):
        assert len(node.args.posonlyargs) == 0
        assert len(node.args.kwonlyargs) == 0
        assert len(node.args.kw_defaults) == 0
        assert node.args.vararg is None
        assert node.args.kwarg is None
        return myast.Lambda([arg.arg for arg in node.args.args], parse_expr(node.body))
    elif isinstance(node, ast.Starred):
        return myast.Starred(parse_expr(node.value))
    assert False, f"{node}"

def parse_opt_expr(node: ast.expr|None) -> myast.Expr | None:
    if node is None:
        return None
    else:
        return parse_expr(node)

def parse_slice(node: ast.Slice) -> Tuple[myast.Expr, myast.Expr, myast.Expr]:
    return parse_opt_expr(node.lower), parse_opt_expr(node.upper), parse_opt_expr(node.step)

def parse_compr(node: ast.comprehension) -> myast.Comprehension:
    assert len(node.ifs) <= 1
    ifs = parse_expr(node.ifs[0]) if len(node.ifs) == 1 else None
    return myast.Comprehension(parse_expr(node.target), parse_expr(node.iter), ifs)

def parse_classdef(node: ast.ClassDef):
    bases = []
    for base in node.bases:
        bases.append(parse_name(base))
    fields = []
    doc = None
    for f in node.body:
        if isinstance(f, ast.AnnAssign):
            fields.append((parse_name(f.target), parse_type(f.annotation), parse_opt_expr(f.value)))
        elif isinstance(f, ast.Expr):
            doc = f.value.value
        else:
            assert False, f"{f}"
    return 'class', node.name, bases, fields

def parse_args(node: ast.arguments):
    def parse_arg(node: ast.arg):
        return node.arg, parse_type(node.annotation)
    assert len(node.posonlyargs) == 0
    assert len(node.kwonlyargs) == 0
    assert node.vararg is None
    assert node.kwarg is None
    assert len(node.kw_defaults) == 0
    return [parse_arg(ch) for ch in node.args]

def parse_stmts(nodes: list[ast.stmt]) -> myast.Block:
    return myast.Block([parse_stmt(ch) for ch in nodes])
def parse_stmt(node: ast.stmt) -> myast.Stmt:
    if isinstance(node, ast.FunctionDef):
        return myast.ExprStmt(myast.Str(f"local func {node.name} TODO"))
    elif isinstance(node, ast.Expr):
        return myast.ExprStmt(parse_expr(node.value))
    elif isinstance(node, ast.Assert):
        return myast.Assert(parse_expr(node.test), parse_opt_expr(node.msg))
    elif isinstance(node, ast.Assign):
        assert len(node.targets) == 1
        return myast.AssignStmt(parse_expr(node.targets[0]), parse_expr(node.value))
    elif isinstance(node, ast.AnnAssign):
        return myast.AnnAssign(parse_expr(node.target), parse_type(node.annotation), parse_opt_expr(node.value))
    elif isinstance(node, ast.AugAssign):
        return myast.AugAssign(parse_expr(node.target), node.op, parse_expr(node.value))
    elif isinstance(node, ast.If):
        return myast.IfStmt(parse_expr(node.test), parse_stmts(node.body), parse_stmts(node.orelse))
    elif isinstance(node, ast.While):
        return myast.WhileStmt(parse_expr(node.test), parse_stmts(node.body))
    elif isinstance(node, ast.For):
        return myast.ForStmt(parse_expr(node.target), parse_expr(node.iter), parse_stmts(node.body))
    elif isinstance(node, ast.Return):
        return myast.Return(parse_opt_expr(node.value))
    elif isinstance(node, ast.Break):
        return myast.Break()
    elif isinstance(node, ast.Continue):
        return myast.Continue()
    else:
        assert False, f"{node}"
def parse_functiondef(node: ast.FunctionDef):
    return ("func", node.name, parse_args(node.args), parse_type(node.returns), parse_stmts(node.body))
def parse_top_level(node: ast.stmt):
    if isinstance(node, ast.Assign):
        tgt, = node.targets
        value = node.value
        return parse_name(tgt), value
    elif isinstance(node, ast.ClassDef):
        return parse_classdef(node)
    elif isinstance(node, ast.FunctionDef):
        return parse_functiondef(node)
    else:
        raise ValueError(f"unknown {node}")

def process_module(node: ast.Module):
    return [parse_top_level(ch) for ch in node.body]

def parse_py2(code):
    node = ast.parse(code)
    return process_module(node)
