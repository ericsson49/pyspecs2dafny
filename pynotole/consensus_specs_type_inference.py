from typing import Sequence

import ast
from marko.ext.gfm import gfm
from marko.block import FencedCode
from glob import glob


from pynotole.pyparser2 import parse_py2, parse_top_level
from pynotole.pyprint import print_st
from pynotole.simplify import simplify_assignments
from pynotole.type_parser import get_module_type_defs, get_base_type_env
from pynotole.transform import infer_var_decls, gen_constraints

def parse_markup(data) -> Sequence[ast.stmt]:
    md = gfm.parse(data)
    res = []
    for ch in md.children:
        if isinstance(ch, FencedCode) and ch.lang == 'python':
            code = ch.children[0].children
            res.extend(ast.parse(code).body)
    return res


spec_dir = '/Users/avlasov/Documents/GitHub/consensus-specs/specs/'
phases = ['phase0', 'altair', 'bellatrix', 'capella', 'deneb']

if __name__ == '__main__':
    for phase in phases:
        type_env = get_base_type_env()

        phase_ast_defs = []
        files = glob(f"{spec_dir}{phase}/*.md")
        for fn in files:
            print('---------')
            print(fn[len(spec_dir):])
            with open(fn, 'r') as f:
                phase_ast_defs.extend(parse_markup(f.read()))

            type_env.update(get_module_type_defs(phase_ast_defs))

        for ast_def in phase_ast_defs:
            tl = parse_top_level(ast_def)
            match tl:
                case ('func', fn, args, _, stmts):
                    from pynotole.ssa import mk_cfg, resolve, place_phi_funcs, split_var_ranges
                    print('--')
                    print(fn, [a for a, _ in args])
                    cfg = mk_cfg(stmts)
                    #infer_var_decls()
                    stmts2 = simplify_assignments(stmts)
                    code = split_var_ranges(stmts2)
                    code2 = infer_var_decls(code, {a for a, _ in args}, set())
                    #print_st(code2, '  ')
                    print(gen_constraints(code2, type_env))


