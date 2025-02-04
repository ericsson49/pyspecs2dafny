import ast

from ..ast_utils import get_arg_names, replace_func_body
from .builders import PyASTBBlockFactory
from .cfg import CFGBuilder, IndexingLabeler
from .ssa import make_SSA


def split_var_ranges(f: ast.FunctionDef) -> ast.FunctionDef:
    args = get_arg_names(f.args)
    cfg = CFGBuilder(IndexingLabeler(), PyASTBBlockFactory()).make_cfg(f.body, set(args))
    blocks, phi_funcs = make_SSA(cfg)

    mp = {}
    for to, phis in phi_funcs.items():
        for phi in phis:
            for fr, v in phi.uses.items():
                if fr not in mp:
                    mp[fr] = []
                mp[fr].append((phi.vdef, v))

    def extract_expr(idx):
        instrs = blocks[idx]
        assert len(instrs) == 1
        assert isinstance(instrs[0], ast.Expr)
        return instrs[0].value

    def extract_target(idx):
        instrs = blocks[idx]
        assert len(instrs) == 1
        assert isinstance(instrs[0], ast.Assign)
        assert len(instrs[0].targets) == 1
        return instrs[0].targets[0]

    def mk_phi_copies(idx):
        res = []
        if idx in mp:
            for to, fr in sorted(mp[idx]):
                res.append(ast.Assign([ast.Name(to)], ast.Name(fr)))
        return res

    def process_stmts(prefix, stmts):
        res = []
        for i, st in enumerate(stmts):
            match st:
                case ast.If(_, body, orelse):
                    test_ = extract_expr(prefix + (i, 'if-test'))
                    body_ = process_stmts(prefix + (i, 'if-then',), body)
                    if len(orelse) != 0:
                        orelse_ = process_stmts(prefix + (i, 'if-else',), orelse)
                    else:
                        if prefix + (i, 'if-else', 0) in mp:
                            orelse_ = mk_phi_copies(prefix + (i, 'if-else', 0))
                        else:
                            orelse_ = []
                    res.append(ast.If(test_, body_, orelse_))
                case ast.While(_, body, []):
                    test_ = extract_expr(prefix + (i, 'while-test'))
                    body_ = process_stmts(prefix + (i, 'while-body',), body)
                    res.append(ast.While(test_, body_, []))
                case ast.For(_, _, body, []):
                    res.extend(mk_phi_copies(prefix + (i, 'for-pre-head')))
                    target = extract_target(prefix + (i, 'for-pre-body'))
                    iter_ = extract_expr(prefix + (i, 'for-pre-head'))
                    body_ = process_stmts(prefix + (i, 'for-body'), body)
                    res.append(ast.For(target, iter_, body_, []))
                case ast.Break():
                    res.append(ast.Break())
                case ast.Continue():
                    res.extend(mk_phi_copies(prefix + (i,)))
                    res.append(ast.Continue())
                case ast.Return():
                    res.append(ast.Return(extract_expr(prefix + (i,))))
                case _:
                    idx = prefix + (i,)
                    instrs = blocks[idx]
                    assert len(instrs) == 1
                    res.append(instrs[0])
                    res.extend(mk_phi_copies(idx))
        return res

    body_ = process_stmts((), f.body)
    return replace_func_body(f, body_)




