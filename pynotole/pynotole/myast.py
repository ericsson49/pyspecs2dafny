import ast
from dataclasses import dataclass
from typing import Sequence, Set, Optional, Tuple, Dict

@dataclass(frozen=True)
class AST(object):
    pass


@dataclass(frozen=True)
class Expr(AST):
    pass

@dataclass(frozen=True)
class Num(Expr):
    n: int

@dataclass(frozen=True)
class Str(Expr):
    s: str

@dataclass(frozen=True)
class Bytes(Expr):
    b: bytes

@dataclass(frozen=True)
class NameConst(Expr):
    n: Optional[bool]

@dataclass(frozen=True)
class Name(Expr):
    id: str

@dataclass(frozen=True)
class Attribute(Expr):
    value: Expr
    attr: str

@dataclass(frozen=True)
class Slice(AST):
    lower: Optional[Expr]
    upper: Optional[Expr]
    step: Optional[Expr]

@dataclass(frozen=True)
class Subscript(Expr):
    value: Expr
    slice: Tuple[Expr | None, Expr | None, Expr | None] | Expr


@dataclass(frozen=True)
class BinOp(Expr):
    left: Expr
    op: ast.operator
    right: Expr

@dataclass(frozen=True)
class Compare(Expr):
    left: Expr
    ops: Sequence[str]
    comparators: Sequence[Expr]

@dataclass(frozen=True)
class BoolOp(Expr):
    op: str
    values: Sequence[Expr]

@dataclass(frozen=True)
class UnaryOp(Expr):
    op: str
    value: Expr

@dataclass(frozen=True)
class FuncCall(Expr):
    func: Expr
    args: Sequence[Expr]
    kwargs: Sequence[Tuple[str,Expr]]

@dataclass(frozen=True)
class IfExp(Expr):
    test: Expr
    body: Expr
    orelse: Expr

@dataclass(frozen=True)
class Comprehension(AST):
    target: Expr
    iter: Expr
    comp_if: Optional[Expr]

@dataclass(frozen=True)
class GeneratorExpr(Expr):
    expr: Expr
    compr: Comprehension

@dataclass(frozen=True)
class ListCompr(Expr):
    expr: Expr
    compr: Comprehension

@dataclass(frozen=True)
class SetCompr(Expr):
    expr: Expr
    compr: Comprehension

@dataclass(frozen=True)
class DictCompr(Expr):
    key: Expr
    value: Expr
    compr: Comprehension

@dataclass(frozen=True)
class PyList(Expr):
    values: Sequence[Expr]

@dataclass(frozen=True)
class PySet(Expr):
    values: Sequence[Expr]

@dataclass(frozen=True)
class PyTuple(Expr):
    values: Sequence[Expr]

@dataclass(frozen=True)
class PyDict(Expr):
    keys: Sequence[Expr]
    values: Sequence[Expr]

@dataclass(frozen=True)
class Lambda(Expr):
    args: Sequence[str]
    body: Expr

@dataclass(frozen=True)
class Starred(Expr):
    value: Expr

# statements


@dataclass(frozen=True)
class Stmt(AST):
    pass

@dataclass(frozen=True)
class Block(AST):
    stmts: Sequence[Stmt]


@dataclass(frozen=True)
class Assert(Stmt):
    test: Expr
    msg: Optional[Expr] = None

@dataclass(frozen=True)
class ExprStmt(Stmt):
    value: Expr

@dataclass(frozen=True)
class VarDecl(Stmt):
    id: str
    typ: Optional[str]
    init: Optional[Expr]

@dataclass(frozen=True)
class AssignStmt(Stmt):
    target: Expr
    value: Expr

@dataclass(frozen=True)
class AugAssign(Stmt):
    target: Expr
    op: str
    value: Expr

@dataclass(frozen=True)
class AnnAssign(Stmt):
    target: Expr
    anno: Expr
    value: Optional[Expr]
@dataclass(frozen=True)
class IfStmt(Stmt):
    test: Expr
    body: Block
    orElse: Block


@dataclass(frozen=True)
class WhileStmt(Stmt):
    test: Expr
    body: Block

@dataclass(frozen=True)
class ForStmt(Stmt):
    target: Expr
    iter: Expr
    body: Block

@dataclass(frozen=True)
class Return(Stmt):
    value: Optional[Expr]

@dataclass(frozen=True)
class Break(Stmt):
    pass

@dataclass(frozen=True)
class Continue(Stmt):
    pass

@dataclass(frozen=True)
class Pass(Stmt):
    pass