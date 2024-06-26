from lark import Lark, Transformer
from .constr_solver import Atom, Var


constraints_grammar = r"""
    constrs: constr ("," constr)*
    constr: term "=:=" term -> eqc
          | term "<:" term  -> stc
          | var "<-" rexp -> exc
    
    rexp: "op" "(" term ("," term)* ")" -> opex
        | var "." NAME -> lfex
    
    term: var -> tv
        | NAME ["[" term ("," term)* "]"] -> atom

    var: "?" NAME
    
    NAME: LETTER+
    %import common.LETTER
    %import common.WS
    %ignore WS
    """


constrs_parser = Lark(constraints_grammar, start='constrs')
term_parser = Lark(constraints_grammar, start='term')


class ConstrTransformer(Transformer):
    def constrs(self, vs):
        return list(vs)

    def stc(self, v):
        a, b = v
        return ('<:', a, b)

    def eqc(self, v):
        a, b = v
        return ('=:=', a, b)

    def exc(self, vs):
        tgt, *rest = vs
        return ("exc", tgt) + tuple(rest)

    def opex(self, vs):
        return ("op",) + tuple(vs)

    def lfex(self, vs):
        (var, fld) = vs
        return ("lf", var, str(fld))

    def tv(self, v):
        (v,) = v
        return v

    def atom(self, ps):
        match ps:
            case [name, None]:
                return Atom(str(name), ())
            case [name, *args]:
                return Atom(str(name), tuple(args))

    def var(self, v):
        (v,) = v
        return Var(str(v))


class TermTransformer(Transformer):
    def tv(self, v):
        (v,) = v
        return v

    def atom(self, ps):
        match ps:
            case [name, None]:
                return Atom(str(name), ())
            case [name, *args]:
                return Atom(str(name), tuple(args))

    def var(self, v):
        (v,) = v
        return Var(str(v))


def parse_constrs(text):
    tree = constrs_parser.parse(text)
    return ConstrTransformer().transform(tree)


def parse_term(txt):
    return TermTransformer().transform(term_parser.parse(txt))


def parse_terms(txts):
    return [parse_term(t) for t in txts]
