grammar Python3;

tokens { INDENT, DEDENT }

@lexer::header {
import com.yuvalshavit.antlr4.DenterHelper;
}

@lexer::members {
private final DenterHelper denter = new DenterHelper(NL, Python3Parser.INDENT, Python3Parser.DEDENT)
{
    @Override
    public Token pullToken() {
        return Python3Lexer.super.nextToken();
    }
};

@Override
public Token nextToken() {
    return denter.nextToken();
}
}

file_input: (NL | top_level_stmt)* EOF;

top_level_stmt: class_def | func_def;

decorator: '@' dotted_name ( '(' (arglist)? ')' )? NL;
dotted_name: NAME ('.' NAME)*;

func_def: decorator* 'def' name=NAME '(' (typedargslist)? ')' ('->' type_expr)? ':' suite;
typedargslist: tfparg (',' tfparg)*;
tfparg: tfpdef ('=' test)?;
tfpdef: NAME (':' type_expr);

class_def: decorator* 'class' name=NAME ('(' (type_expr_list)? ')')? ':' class_suite;
class_suite: INDENT class_fields DEDENT;
class_fields: (field_def NL)+ # Fields
            | ('pass' NL) # EmptyClass;
field_def: NAME (':' type_expr)? ('=' test)?;

type_expr: expr;
type_expr_list: type_expr (',' type_expr)*;

varargslist: vfpdef (',' vfpdef)*;
vfpdef: NAME;

stmt: simple_stmt | compound_stmt | annotation;
simple_stmt: small_stmt (';' small_stmt)* (';')? NL;
small_stmt:
    test_list # ExprStmt
    | target=test_list '=' value=test_list # Assign
    | target=test_list ':' type=type_expr ('=' value=test_list)? # AnnAssign
    | test_list aug_assign test_list # AugAssign
    | 'del' expr_list # Del
    | 'pass' # Pass
    | 'break' # Break
    | 'continue' # Continue
    | 'return' (test_list)? # Return
    | 'assert' value=test (',' msg=test)? # Assert
    ;
aug_assign: ('+=' | '-=' | '*=' | '@=' | '/=' | '%=' | '&=' | '|=' | '^=' |
            '<<=' | '>>=' | '**=' | '//=');

compound_stmt:
    'if' tst=test ':' body=suite /*('elif' test ':' suite)* */ ('else' ':' orelse=suite)? # If
    | 'while' tst=expr ':' body=suite # While
    | 'for' tgt=expr_list 'in' iter=test_list ':' body=suite # For
    | func_def # FuncDef
    | class_def # ClassDef
    ;

annotation: ANNO_COMMENT NL;

suite: simple_stmt # SimpleStmt
    | INDENT body+=stmt+ DEDENT # Block;

comp_op: '<'|'>'|'=='|'>='|'<='|'<>'|'!='|'in'|'not' 'in'|'is'|'is' 'not';
test: test 'if' test 'else' test
        | 'lambda' (varargslist)? ':' test
        | expr;

expr: exprs+=expr ('or' exprs+=expr)+ # OrExpr
    | exprs+=expr ('and' exprs+=expr)+ # AndExpr
    | 'not' operand=expr # NotExpr
    | left=expr (ops+=comp_op comparators+=expr)+ # Compare
    | '*' value+=expr # StarExpr
    | left=expr op='|' right=expr # BitOr
    | left=expr op='^' right=expr # BitXor
    | left=expr op='&' right=expr # BitAnd
    | left=expr op=('<<'|'>>') right=expr # ShiftExpr
    | left=expr op=('+'|'-') right=expr # PlusMinus
    | left=expr op=('*'|'@'|'/'|'%'|'//') right=expr # Term
    | op=('+'|'-'|'~') operand=expr # Factor
    | left=atom_expr op='**' right=expr # Power
    | value=atom_expr # AtomExpr;
atom_expr: atom trailer*;
atom: '(' test ')' # ParensExpr
    | '(' test_list? ')' # Tuple
    | '[' test_list? ']' # ListLit
    | '{' (keys+=test ':' values+=test (',' keys+=test ':' values+=test)? (',')?)? '}' # DictLit
    | '{' test_list? '}' # SetLit
    | id=NAME # Name
    | n=NUMBER # Number
    | str+=STRING+ # Str
    | '...' # Ellipsis
    | 'None' # None
    | 'True' # True
    | 'False' # False
    ;
trailer: '(' (arglist)? ')' # CallTrailer
    | '[' subscriptlist ']' # SubscrTrailer
    | '.' NAME              # AttrTrailer
    ;
subscriptlist: subscript (',' subscript)* (',')?;
subscript: expr | (expr)? ':' (expr)? (sliceop)?;
sliceop: ':' (expr)?;
expr_list: elems+=expr (',' elems+=expr)* (',')?;
test_list: elems+=test (',' elems+=test)* (',')?;


arglist: argument (',' argument)*  (',')?;

argument: ( expr (comp_for)? |
            expr ':=' expr |
            expr '=' expr |
            '**' expr |
            '*' expr );

comp_iter: comp_for | comp_if;
sync_comp_for: 'for' expr_list 'in' expr (comp_iter)?;
comp_for: sync_comp_for;
comp_if: 'if' expr (comp_iter)?;


WS: [ \t]+ -> skip;
ANNO_COMMENT: '#@' ~[\r\n]*;
LINE_COMMENT: '#' (~[@\r\n]* | (~[@\r\n] ~[\r\n]*)) -> skip;
NAME: [a-zA-Z][a-zA-Z0-9_]*;
NUMBER: [0-9]+;

NL: ('\r'? '\n' ' '*);