from pynotole import myast
from pynotole.simplify import simpl
from pynotole.pyparser2 import parse_py2
from pynotole.pyprint import print_func


if __name__ == '__main__':
    code = '''
def f() -> None:
    a = b + c + d
    c = f(a+b)
    a = b[a+f]
    c = a.d[1]
    a.d.f
    a.f = c+b
    a.f.g = c+d
    a[v] = c+d
    a.f[v] = x
    a[x+d] = c
    '''

    tl_defs = parse_py2(code)
    for tl_def in tl_defs:
        match tl_def:
            case myast.FunctionDef(name, args, returns, body):
                simplified_stmts = simpl(body)
                f = myast.FunctionDef(name, args, returns, simplified_stmts)
                print_func(f)
