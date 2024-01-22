grammar Python3;

tokens { INDENT, DEDENT }

@lexer::header {
import com.yuvalshavit.antlr4.DenterHelper;
}

@lexer::members {
private int parens = 0;
private final DenterHelper denter = new DenterHelper(NL, Python3Parser.INDENT, Python3Parser.DEDENT)
{
    @Override
    public Token pullToken() {
        Token token = Python3Lexer.super.nextToken();
        var type = token.getType();
        if (type == Python3Parser.LPAR || type == Python3Parser.LSQB || type == Python3Parser.LBRACE) {
            parens++;
        } else if (type == Python3Parser.RPAR || type == Python3Parser.RSQB || type == Python3Parser.RBRACE) {
            parens--;
        } else if (type == Python3Parser.NL) {
            if (parens > 0) {
                return pullToken();
            }
        }
        return token;
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
typedargslist: targ+=tfparg (',' targ+=tfparg)*;
tfparg: tfpdef ('=' test)?;
tfpdef: argName=NAME (':' argType=type_expr);

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
    'if' tst=test ':' body=suite ('elif' elif_tsts+=test ':' elif_bodies+=suite)* ('else' ':' orelse=suite)? # If
    | 'while' tst=expr ':' body=suite # While
    | 'for' tgt=expr_list 'in' iter=test_list ':' body=suite # For
    | func_def # FuncDef
    | class_def # ClassDef
    ;

annotation: ANNO_COMMENT NL;

suite: simple_stmt # SimpleStmt
    | INDENT body+=stmt+ DEDENT # Block;

comp_op: '<'|'>'|'=='|'>='|'<='|'<>'|'!='|'in'|'not' 'in'|'is'|'is' 'not';
test: body=test 'if' tst=test 'else' orelse=test # IfExp
        | 'lambda' (varargslist)? ':' test # Lambda
        | expr # TExpr
        ;

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
    | '[' expr comp_for ']' # ListComp
    | '{' (keys+=test ':' values+=test (',' keys+=test ':' values+=test)? (',')?)? '}' # DictLit
    | '{' key=expr ':' value=expr comp_for '}' # DictComp
    | '{' test_list? '}' # SetLit
    | '{' expr comp_for '}' # SetComp
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


arglist: arg+=argument (',' arg+=argument)*  (',')?;

argument: test # TestArg
        | expr comp_for # GeneratorArg
        | NAME '=' test # NamedArg
        | '*' expr # StarArg
        ;

name_list: n+=NAME (',' n+=NAME)* (',')?;
comp_for: 'for' name_list 'in' coll=expr ('if' ifs+=expr)*;


FALSE    : 'False';
AWAIT    : 'await';
ELSE     : 'else';
IMPORT   : 'import';
PASS     : 'pass';
NONE     : 'None';
BREAK    : 'break';
EXCEPT   : 'except';
IN       : 'in';
RAISE    : 'raise';
TRUE     : 'True';
CLASS    : 'class';
FINALLY  : 'finally';
IS       : 'is';
RETURN   : 'return';
AND      : 'and';
CONTINUE : 'continue';
FOR      : 'for';
LAMBDA   : 'lambda';
TRY      : 'try';
AS       : 'as';
DEF      : 'def';
FROM     : 'from';
NONLOCAL : 'nonlocal';
WHILE    : 'while';
ASSERT   : 'assert';
DEL      : 'del';
GLOBAL   : 'global';
NOT      : 'not';
WITH     : 'with';
ASYNC    : 'async';
ELIF     : 'elif';
IF       : 'if';
OR       : 'or';
YIELD    : 'yield';

LPAR             : '(';  // LPAR
LSQB             : '[';  // LSQB
LBRACE           : '{';  // LBRACE
RPAR             : ')';  // RPAR
RSQB             : ']';  // RSQB
RBRACE           : '}';  // RBRACE
COLON            : ':';
COMMA            : ',';
SEMI             : ';';
PLUS             : '+';
MINUS            : '-';
STAR             : '*';
SLASH            : '/';
VBAR             : '|';
AMPER            : '&';
LESS             : '<';
GREATER          : '>';
EQUAL            : '=';
DOT              : '.';
PERCENT          : '%';
EQEQUAL          : '==';
INEQUAL          : '<>'; // <> isn't actually a valid comparison operator in Python. It's here for the sake of a __future__ import described in PEP 401 (which really works :-)
NOTEQUAL         : '!=';
LESSEQUAL        : '<=';
GREATEREQUAL     : '>=';
TILDE            : '~';
CIRCUMFLEX       : '^';
LEFTSHIFT        : '<<';
RIGHTSHIFT       : '>>';
DOUBLESTAR       : '**';
PLUSEQUAL        : '+=';
MINEQUAL         : '-=';
STAREQUAL        : '*=';
SLASHEQUAL       : '/=';
PERCENTEQUAL     : '%=';
AMPEREQUAL       : '&=';
VBAREQUAL        : '|=';
CIRCUMFLEXEQUAL  : '^=';
LEFTSHIFTEQUAL   : '<<=';
RIGHTSHIFTEQUAL  : '>>=';
DOUBLESTAREQUAL  : '**=';
DOUBLESLASH      : '//';
DOUBLESLASHEQUAL : '//=';
AT               : '@';
ATEQUAL          : '@=';
RARROW           : '->';
ELLIPSIS         : '...';
COLONEQUAL       : ':=';

//NAME
//    : ID_START ID_CONTINUE*
//    ;

//NUMBER
//    : INTEGER
//    | FLOAT_NUMBER
//    | IMAG_NUMBER
//    ;

STRING
    : STRING_LITERAL
//    | BYTES_LITERAL
    ;

//TYPE_COMMENT
//    : '#' WS? 'type:' WS? ~[\r\n\f]*
//    ;
//
//NEWLINE
//    : OS_INDEPEND_NL
//    ;
//
//COMMENT      : '#' ~[\r\n\f]* -> channel(HIDDEN);
//WS           : [ \t]+         -> channel(HIDDEN);
//LINE_JOINING : '\\' NEWLINE   -> channel(HIDDEN);

WS: [ \t]+ -> skip;
ANNO_COMMENT: '#@' ~[\r\n]*;
LINE_COMMENT: '#' (~[@\r\n]* | (~[@\r\n] ~[\r\n]*)) -> skip;
NAME: [a-zA-Z][a-zA-Z0-9_]*;
NUMBER: [0-9]+;

NL: ('\r'? '\n' ' '*);

fragment STRING_LITERAL : STRING_PREFIX? (SHORT_STRING | LONG_STRING);
fragment STRING_PREFIX  : 'r' | 'u' | 'R' | 'U' | 'f' | 'F' | 'fr' | 'Fr' | 'fR' | 'FR' | 'rf' | 'rF' | 'Rf' | 'RF';
fragment SHORT_STRING   : '\'' SHORT_STRING_ITEM_FOR_SINGLE_QUOTE* '\'' | '"' SHORT_STRING_ITEM_FOR_DOUBLE_QUOTE* '"';
fragment LONG_STRING    : '\'\'\'' LONG_STRING_ITEM*? '\'\'\'' | '"""' LONG_STRING_ITEM*? '"""';

fragment SHORT_STRING_ITEM_FOR_SINGLE_QUOTE : SHORT_STRING_CHAR_NO_SINGLE_QUOTE | STRING_ESCAPE_SEQ;
fragment SHORT_STRING_ITEM_FOR_DOUBLE_QUOTE : SHORT_STRING_CHAR_NO_DOUBLE_QUOTE | STRING_ESCAPE_SEQ;

fragment LONG_STRING_ITEM : LONG_STRING_CHAR | STRING_ESCAPE_SEQ;

fragment SHORT_STRING_CHAR_NO_SINGLE_QUOTE : ~[\\\r\n'];       // <any source character except "\" or newline or singel quote>
fragment SHORT_STRING_CHAR_NO_DOUBLE_QUOTE : ~[\\\r\n"];       // <any source character except "\" or newline or double quote>
fragment LONG_STRING_CHAR  : ~'\\';                            // <any source character except "\">
fragment STRING_ESCAPE_SEQ : '\\' (OS_INDEPEND_NL | .);        // <any source character>

fragment OS_INDEPEND_NL : '\r'? '\n';