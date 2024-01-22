// Generated from java-escape by ANTLR 4.11.1
package python3_grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class Python3Parser extends Parser {
	static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		FALSE=1, AWAIT=2, ELSE=3, IMPORT=4, PASS=5, NONE=6, BREAK=7, EXCEPT=8, 
		IN=9, RAISE=10, TRUE=11, CLASS=12, FINALLY=13, IS=14, RETURN=15, AND=16, 
		CONTINUE=17, FOR=18, LAMBDA=19, TRY=20, AS=21, DEF=22, FROM=23, NONLOCAL=24, 
		WHILE=25, ASSERT=26, DEL=27, GLOBAL=28, NOT=29, WITH=30, ASYNC=31, ELIF=32, 
		IF=33, OR=34, YIELD=35, LPAR=36, LSQB=37, LBRACE=38, RPAR=39, RSQB=40, 
		RBRACE=41, COLON=42, COMMA=43, SEMI=44, PLUS=45, MINUS=46, STAR=47, SLASH=48, 
		VBAR=49, AMPER=50, LESS=51, GREATER=52, EQUAL=53, DOT=54, PERCENT=55, 
		EQEQUAL=56, INEQUAL=57, NOTEQUAL=58, LESSEQUAL=59, GREATEREQUAL=60, TILDE=61, 
		CIRCUMFLEX=62, LEFTSHIFT=63, RIGHTSHIFT=64, DOUBLESTAR=65, PLUSEQUAL=66, 
		MINEQUAL=67, STAREQUAL=68, SLASHEQUAL=69, PERCENTEQUAL=70, AMPEREQUAL=71, 
		VBAREQUAL=72, CIRCUMFLEXEQUAL=73, LEFTSHIFTEQUAL=74, RIGHTSHIFTEQUAL=75, 
		DOUBLESTAREQUAL=76, DOUBLESLASH=77, DOUBLESLASHEQUAL=78, AT=79, ATEQUAL=80, 
		RARROW=81, ELLIPSIS=82, COLONEQUAL=83, STRING=84, WS=85, ANNO_COMMENT=86, 
		LINE_COMMENT=87, NAME=88, NUMBER=89, NL=90, INDENT=91, DEDENT=92;
	public static final int
		RULE_file_input = 0, RULE_top_level_stmt = 1, RULE_decorator = 2, RULE_dotted_name = 3, 
		RULE_func_def = 4, RULE_typedargslist = 5, RULE_tfparg = 6, RULE_tfpdef = 7, 
		RULE_class_def = 8, RULE_class_suite = 9, RULE_class_fields = 10, RULE_field_def = 11, 
		RULE_type_expr = 12, RULE_type_expr_list = 13, RULE_varargslist = 14, 
		RULE_vfpdef = 15, RULE_stmt = 16, RULE_simple_stmt = 17, RULE_small_stmt = 18, 
		RULE_aug_assign = 19, RULE_compound_stmt = 20, RULE_annotation = 21, RULE_suite = 22, 
		RULE_comp_op = 23, RULE_test = 24, RULE_expr = 25, RULE_atom_expr = 26, 
		RULE_atom = 27, RULE_trailer = 28, RULE_subscriptlist = 29, RULE_subscript = 30, 
		RULE_sliceop = 31, RULE_expr_list = 32, RULE_test_list = 33, RULE_arglist = 34, 
		RULE_argument = 35, RULE_name_list = 36, RULE_comp_for = 37;
	private static String[] makeRuleNames() {
		return new String[] {
			"file_input", "top_level_stmt", "decorator", "dotted_name", "func_def", 
			"typedargslist", "tfparg", "tfpdef", "class_def", "class_suite", "class_fields", 
			"field_def", "type_expr", "type_expr_list", "varargslist", "vfpdef", 
			"stmt", "simple_stmt", "small_stmt", "aug_assign", "compound_stmt", "annotation", 
			"suite", "comp_op", "test", "expr", "atom_expr", "atom", "trailer", "subscriptlist", 
			"subscript", "sliceop", "expr_list", "test_list", "arglist", "argument", 
			"name_list", "comp_for"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'False'", "'await'", "'else'", "'import'", "'pass'", "'None'", 
			"'break'", "'except'", "'in'", "'raise'", "'True'", "'class'", "'finally'", 
			"'is'", "'return'", "'and'", "'continue'", "'for'", "'lambda'", "'try'", 
			"'as'", "'def'", "'from'", "'nonlocal'", "'while'", "'assert'", "'del'", 
			"'global'", "'not'", "'with'", "'async'", "'elif'", "'if'", "'or'", "'yield'", 
			"'('", "'['", "'{'", "')'", "']'", "'}'", "':'", "','", "';'", "'+'", 
			"'-'", "'*'", "'/'", "'|'", "'&'", "'<'", "'>'", "'='", "'.'", "'%'", 
			"'=='", "'<>'", "'!='", "'<='", "'>='", "'~'", "'^'", "'<<'", "'>>'", 
			"'**'", "'+='", "'-='", "'*='", "'/='", "'%='", "'&='", "'|='", "'^='", 
			"'<<='", "'>>='", "'**='", "'//'", "'//='", "'@'", "'@='", "'->'", "'...'", 
			"':='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "FALSE", "AWAIT", "ELSE", "IMPORT", "PASS", "NONE", "BREAK", "EXCEPT", 
			"IN", "RAISE", "TRUE", "CLASS", "FINALLY", "IS", "RETURN", "AND", "CONTINUE", 
			"FOR", "LAMBDA", "TRY", "AS", "DEF", "FROM", "NONLOCAL", "WHILE", "ASSERT", 
			"DEL", "GLOBAL", "NOT", "WITH", "ASYNC", "ELIF", "IF", "OR", "YIELD", 
			"LPAR", "LSQB", "LBRACE", "RPAR", "RSQB", "RBRACE", "COLON", "COMMA", 
			"SEMI", "PLUS", "MINUS", "STAR", "SLASH", "VBAR", "AMPER", "LESS", "GREATER", 
			"EQUAL", "DOT", "PERCENT", "EQEQUAL", "INEQUAL", "NOTEQUAL", "LESSEQUAL", 
			"GREATEREQUAL", "TILDE", "CIRCUMFLEX", "LEFTSHIFT", "RIGHTSHIFT", "DOUBLESTAR", 
			"PLUSEQUAL", "MINEQUAL", "STAREQUAL", "SLASHEQUAL", "PERCENTEQUAL", "AMPEREQUAL", 
			"VBAREQUAL", "CIRCUMFLEXEQUAL", "LEFTSHIFTEQUAL", "RIGHTSHIFTEQUAL", 
			"DOUBLESTAREQUAL", "DOUBLESLASH", "DOUBLESLASHEQUAL", "AT", "ATEQUAL", 
			"RARROW", "ELLIPSIS", "COLONEQUAL", "STRING", "WS", "ANNO_COMMENT", "LINE_COMMENT", 
			"NAME", "NUMBER", "NL", "INDENT", "DEDENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "java-escape"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public Python3Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class File_inputContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(Python3Parser.EOF, 0); }
		public List<TerminalNode> NL() { return getTokens(Python3Parser.NL); }
		public TerminalNode NL(int i) {
			return getToken(Python3Parser.NL, i);
		}
		public List<Top_level_stmtContext> top_level_stmt() {
			return getRuleContexts(Top_level_stmtContext.class);
		}
		public Top_level_stmtContext top_level_stmt(int i) {
			return getRuleContext(Top_level_stmtContext.class,i);
		}
		public File_inputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_file_input; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterFile_input(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitFile_input(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitFile_input(this);
			else return visitor.visitChildren(this);
		}
	}

	public final File_inputContext file_input() throws RecognitionException {
		File_inputContext _localctx = new File_inputContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file_input);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CLASS || _la==DEF || _la==AT || _la==NL) {
				{
				setState(78);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NL:
					{
					setState(76);
					match(NL);
					}
					break;
				case CLASS:
				case DEF:
				case AT:
					{
					setState(77);
					top_level_stmt();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(82);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(83);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Top_level_stmtContext extends ParserRuleContext {
		public Class_defContext class_def() {
			return getRuleContext(Class_defContext.class,0);
		}
		public Func_defContext func_def() {
			return getRuleContext(Func_defContext.class,0);
		}
		public Top_level_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_top_level_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterTop_level_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitTop_level_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitTop_level_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Top_level_stmtContext top_level_stmt() throws RecognitionException {
		Top_level_stmtContext _localctx = new Top_level_stmtContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_top_level_stmt);
		try {
			setState(87);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(85);
				class_def();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(86);
				func_def();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DecoratorContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(Python3Parser.AT, 0); }
		public Dotted_nameContext dotted_name() {
			return getRuleContext(Dotted_nameContext.class,0);
		}
		public TerminalNode NL() { return getToken(Python3Parser.NL, 0); }
		public TerminalNode LPAR() { return getToken(Python3Parser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(Python3Parser.RPAR, 0); }
		public ArglistContext arglist() {
			return getRuleContext(ArglistContext.class,0);
		}
		public DecoratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decorator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterDecorator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitDecorator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitDecorator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecoratorContext decorator() throws RecognitionException {
		DecoratorContext _localctx = new DecoratorContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_decorator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			match(AT);
			setState(90);
			dotted_name();
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAR) {
				{
				setState(91);
				match(LPAR);
				setState(93);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((_la) & ~0x3f) == 0 && ((1L << _la) & 2306089781392050242L) != 0 || (((_la - 82)) & ~0x3f) == 0 && ((1L << (_la - 82)) & 197L) != 0) {
					{
					setState(92);
					arglist();
					}
				}

				setState(95);
				match(RPAR);
				}
			}

			setState(98);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Dotted_nameContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(Python3Parser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(Python3Parser.NAME, i);
		}
		public List<TerminalNode> DOT() { return getTokens(Python3Parser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(Python3Parser.DOT, i);
		}
		public Dotted_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dotted_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterDotted_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitDotted_name(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitDotted_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Dotted_nameContext dotted_name() throws RecognitionException {
		Dotted_nameContext _localctx = new Dotted_nameContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_dotted_name);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			match(NAME);
			setState(105);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(101);
				match(DOT);
				setState(102);
				match(NAME);
				}
				}
				setState(107);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Func_defContext extends ParserRuleContext {
		public Token name;
		public TerminalNode DEF() { return getToken(Python3Parser.DEF, 0); }
		public TerminalNode LPAR() { return getToken(Python3Parser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(Python3Parser.RPAR, 0); }
		public TerminalNode COLON() { return getToken(Python3Parser.COLON, 0); }
		public SuiteContext suite() {
			return getRuleContext(SuiteContext.class,0);
		}
		public TerminalNode NAME() { return getToken(Python3Parser.NAME, 0); }
		public List<DecoratorContext> decorator() {
			return getRuleContexts(DecoratorContext.class);
		}
		public DecoratorContext decorator(int i) {
			return getRuleContext(DecoratorContext.class,i);
		}
		public TypedargslistContext typedargslist() {
			return getRuleContext(TypedargslistContext.class,0);
		}
		public TerminalNode RARROW() { return getToken(Python3Parser.RARROW, 0); }
		public Type_exprContext type_expr() {
			return getRuleContext(Type_exprContext.class,0);
		}
		public Func_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterFunc_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitFunc_def(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitFunc_def(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Func_defContext func_def() throws RecognitionException {
		Func_defContext _localctx = new Func_defContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_func_def);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(108);
				decorator();
				}
				}
				setState(113);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(114);
			match(DEF);
			setState(115);
			((Func_defContext)_localctx).name = match(NAME);
			setState(116);
			match(LPAR);
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NAME) {
				{
				setState(117);
				typedargslist();
				}
			}

			setState(120);
			match(RPAR);
			setState(123);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==RARROW) {
				{
				setState(121);
				match(RARROW);
				setState(122);
				type_expr();
				}
			}

			setState(125);
			match(COLON);
			setState(126);
			suite();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypedargslistContext extends ParserRuleContext {
		public TfpargContext tfparg;
		public List<TfpargContext> targ = new ArrayList<TfpargContext>();
		public List<TfpargContext> tfparg() {
			return getRuleContexts(TfpargContext.class);
		}
		public TfpargContext tfparg(int i) {
			return getRuleContext(TfpargContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Python3Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Python3Parser.COMMA, i);
		}
		public TypedargslistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typedargslist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterTypedargslist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitTypedargslist(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitTypedargslist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypedargslistContext typedargslist() throws RecognitionException {
		TypedargslistContext _localctx = new TypedargslistContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_typedargslist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			((TypedargslistContext)_localctx).tfparg = tfparg();
			((TypedargslistContext)_localctx).targ.add(((TypedargslistContext)_localctx).tfparg);
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(129);
				match(COMMA);
				setState(130);
				((TypedargslistContext)_localctx).tfparg = tfparg();
				((TypedargslistContext)_localctx).targ.add(((TypedargslistContext)_localctx).tfparg);
				}
				}
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TfpargContext extends ParserRuleContext {
		public TfpdefContext tfpdef() {
			return getRuleContext(TfpdefContext.class,0);
		}
		public TerminalNode EQUAL() { return getToken(Python3Parser.EQUAL, 0); }
		public TestContext test() {
			return getRuleContext(TestContext.class,0);
		}
		public TfpargContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tfparg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterTfparg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitTfparg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitTfparg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TfpargContext tfparg() throws RecognitionException {
		TfpargContext _localctx = new TfpargContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_tfparg);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			tfpdef();
			setState(139);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EQUAL) {
				{
				setState(137);
				match(EQUAL);
				setState(138);
				test(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TfpdefContext extends ParserRuleContext {
		public Token argName;
		public Type_exprContext argType;
		public TerminalNode NAME() { return getToken(Python3Parser.NAME, 0); }
		public TerminalNode COLON() { return getToken(Python3Parser.COLON, 0); }
		public Type_exprContext type_expr() {
			return getRuleContext(Type_exprContext.class,0);
		}
		public TfpdefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tfpdef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterTfpdef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitTfpdef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitTfpdef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TfpdefContext tfpdef() throws RecognitionException {
		TfpdefContext _localctx = new TfpdefContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_tfpdef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			((TfpdefContext)_localctx).argName = match(NAME);
			{
			setState(142);
			match(COLON);
			setState(143);
			((TfpdefContext)_localctx).argType = type_expr();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Class_defContext extends ParserRuleContext {
		public Token name;
		public TerminalNode CLASS() { return getToken(Python3Parser.CLASS, 0); }
		public TerminalNode COLON() { return getToken(Python3Parser.COLON, 0); }
		public Class_suiteContext class_suite() {
			return getRuleContext(Class_suiteContext.class,0);
		}
		public TerminalNode NAME() { return getToken(Python3Parser.NAME, 0); }
		public List<DecoratorContext> decorator() {
			return getRuleContexts(DecoratorContext.class);
		}
		public DecoratorContext decorator(int i) {
			return getRuleContext(DecoratorContext.class,i);
		}
		public TerminalNode LPAR() { return getToken(Python3Parser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(Python3Parser.RPAR, 0); }
		public Type_expr_listContext type_expr_list() {
			return getRuleContext(Type_expr_listContext.class,0);
		}
		public Class_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_class_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterClass_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitClass_def(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitClass_def(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Class_defContext class_def() throws RecognitionException {
		Class_defContext _localctx = new Class_defContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_class_def);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(145);
				decorator();
				}
				}
				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(151);
			match(CLASS);
			setState(152);
			((Class_defContext)_localctx).name = match(NAME);
			setState(158);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAR) {
				{
				setState(153);
				match(LPAR);
				setState(155);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((_la) & ~0x3f) == 0 && ((1L << _la) & 2306089781391525954L) != 0 || (((_la - 82)) & ~0x3f) == 0 && ((1L << (_la - 82)) & 197L) != 0) {
					{
					setState(154);
					type_expr_list();
					}
				}

				setState(157);
				match(RPAR);
				}
			}

			setState(160);
			match(COLON);
			setState(161);
			class_suite();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Class_suiteContext extends ParserRuleContext {
		public TerminalNode INDENT() { return getToken(Python3Parser.INDENT, 0); }
		public Class_fieldsContext class_fields() {
			return getRuleContext(Class_fieldsContext.class,0);
		}
		public TerminalNode DEDENT() { return getToken(Python3Parser.DEDENT, 0); }
		public Class_suiteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_class_suite; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterClass_suite(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitClass_suite(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitClass_suite(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Class_suiteContext class_suite() throws RecognitionException {
		Class_suiteContext _localctx = new Class_suiteContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_class_suite);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			match(INDENT);
			setState(164);
			class_fields();
			setState(165);
			match(DEDENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Class_fieldsContext extends ParserRuleContext {
		public Class_fieldsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_class_fields; }
	 
		public Class_fieldsContext() { }
		public void copyFrom(Class_fieldsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FieldsContext extends Class_fieldsContext {
		public List<Field_defContext> field_def() {
			return getRuleContexts(Field_defContext.class);
		}
		public Field_defContext field_def(int i) {
			return getRuleContext(Field_defContext.class,i);
		}
		public List<TerminalNode> NL() { return getTokens(Python3Parser.NL); }
		public TerminalNode NL(int i) {
			return getToken(Python3Parser.NL, i);
		}
		public FieldsContext(Class_fieldsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterFields(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitFields(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitFields(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EmptyClassContext extends Class_fieldsContext {
		public TerminalNode PASS() { return getToken(Python3Parser.PASS, 0); }
		public TerminalNode NL() { return getToken(Python3Parser.NL, 0); }
		public EmptyClassContext(Class_fieldsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterEmptyClass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitEmptyClass(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitEmptyClass(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Class_fieldsContext class_fields() throws RecognitionException {
		Class_fieldsContext _localctx = new Class_fieldsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_class_fields);
		int _la;
		try {
			setState(176);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				_localctx = new FieldsContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(170); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(167);
					field_def();
					setState(168);
					match(NL);
					}
					}
					setState(172); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==NAME );
				}
				break;
			case PASS:
				_localctx = new EmptyClassContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(174);
				match(PASS);
				setState(175);
				match(NL);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Field_defContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(Python3Parser.NAME, 0); }
		public TerminalNode COLON() { return getToken(Python3Parser.COLON, 0); }
		public Type_exprContext type_expr() {
			return getRuleContext(Type_exprContext.class,0);
		}
		public TerminalNode EQUAL() { return getToken(Python3Parser.EQUAL, 0); }
		public TestContext test() {
			return getRuleContext(TestContext.class,0);
		}
		public Field_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_field_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterField_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitField_def(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitField_def(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Field_defContext field_def() throws RecognitionException {
		Field_defContext _localctx = new Field_defContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_field_def);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			match(NAME);
			setState(181);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(179);
				match(COLON);
				setState(180);
				type_expr();
				}
			}

			setState(185);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EQUAL) {
				{
				setState(183);
				match(EQUAL);
				setState(184);
				test(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Type_exprContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Type_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterType_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitType_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitType_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_exprContext type_expr() throws RecognitionException {
		Type_exprContext _localctx = new Type_exprContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_type_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Type_expr_listContext extends ParserRuleContext {
		public List<Type_exprContext> type_expr() {
			return getRuleContexts(Type_exprContext.class);
		}
		public Type_exprContext type_expr(int i) {
			return getRuleContext(Type_exprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Python3Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Python3Parser.COMMA, i);
		}
		public Type_expr_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_expr_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterType_expr_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitType_expr_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitType_expr_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_expr_listContext type_expr_list() throws RecognitionException {
		Type_expr_listContext _localctx = new Type_expr_listContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_type_expr_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			type_expr();
			setState(194);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(190);
				match(COMMA);
				setState(191);
				type_expr();
				}
				}
				setState(196);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarargslistContext extends ParserRuleContext {
		public List<VfpdefContext> vfpdef() {
			return getRuleContexts(VfpdefContext.class);
		}
		public VfpdefContext vfpdef(int i) {
			return getRuleContext(VfpdefContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Python3Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Python3Parser.COMMA, i);
		}
		public VarargslistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varargslist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterVarargslist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitVarargslist(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitVarargslist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarargslistContext varargslist() throws RecognitionException {
		VarargslistContext _localctx = new VarargslistContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_varargslist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			vfpdef();
			setState(202);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(198);
				match(COMMA);
				setState(199);
				vfpdef();
				}
				}
				setState(204);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VfpdefContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(Python3Parser.NAME, 0); }
		public VfpdefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vfpdef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterVfpdef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitVfpdef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitVfpdef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VfpdefContext vfpdef() throws RecognitionException {
		VfpdefContext _localctx = new VfpdefContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_vfpdef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StmtContext extends ParserRuleContext {
		public Simple_stmtContext simple_stmt() {
			return getRuleContext(Simple_stmtContext.class,0);
		}
		public Compound_stmtContext compound_stmt() {
			return getRuleContext(Compound_stmtContext.class,0);
		}
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_stmt);
		try {
			setState(210);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FALSE:
			case PASS:
			case NONE:
			case BREAK:
			case TRUE:
			case RETURN:
			case CONTINUE:
			case LAMBDA:
			case ASSERT:
			case DEL:
			case NOT:
			case LPAR:
			case LSQB:
			case LBRACE:
			case PLUS:
			case MINUS:
			case STAR:
			case TILDE:
			case ELLIPSIS:
			case STRING:
			case NAME:
			case NUMBER:
				enterOuterAlt(_localctx, 1);
				{
				setState(207);
				simple_stmt();
				}
				break;
			case CLASS:
			case FOR:
			case DEF:
			case WHILE:
			case IF:
			case AT:
				enterOuterAlt(_localctx, 2);
				{
				setState(208);
				compound_stmt();
				}
				break;
			case ANNO_COMMENT:
				enterOuterAlt(_localctx, 3);
				{
				setState(209);
				annotation();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Simple_stmtContext extends ParserRuleContext {
		public List<Small_stmtContext> small_stmt() {
			return getRuleContexts(Small_stmtContext.class);
		}
		public Small_stmtContext small_stmt(int i) {
			return getRuleContext(Small_stmtContext.class,i);
		}
		public TerminalNode NL() { return getToken(Python3Parser.NL, 0); }
		public List<TerminalNode> SEMI() { return getTokens(Python3Parser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(Python3Parser.SEMI, i);
		}
		public Simple_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simple_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterSimple_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitSimple_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitSimple_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Simple_stmtContext simple_stmt() throws RecognitionException {
		Simple_stmtContext _localctx = new Simple_stmtContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_simple_stmt);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			small_stmt();
			setState(217);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(213);
					match(SEMI);
					setState(214);
					small_stmt();
					}
					} 
				}
				setState(219);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			}
			setState(221);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(220);
				match(SEMI);
				}
			}

			setState(223);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Small_stmtContext extends ParserRuleContext {
		public Small_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_small_stmt; }
	 
		public Small_stmtContext() { }
		public void copyFrom(Small_stmtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AugAssignContext extends Small_stmtContext {
		public List<Test_listContext> test_list() {
			return getRuleContexts(Test_listContext.class);
		}
		public Test_listContext test_list(int i) {
			return getRuleContext(Test_listContext.class,i);
		}
		public Aug_assignContext aug_assign() {
			return getRuleContext(Aug_assignContext.class,0);
		}
		public AugAssignContext(Small_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterAugAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitAugAssign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitAugAssign(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ReturnContext extends Small_stmtContext {
		public TerminalNode RETURN() { return getToken(Python3Parser.RETURN, 0); }
		public Test_listContext test_list() {
			return getRuleContext(Test_listContext.class,0);
		}
		public ReturnContext(Small_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterReturn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitReturn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitReturn(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssertContext extends Small_stmtContext {
		public TestContext value;
		public TestContext msg;
		public TerminalNode ASSERT() { return getToken(Python3Parser.ASSERT, 0); }
		public List<TestContext> test() {
			return getRuleContexts(TestContext.class);
		}
		public TestContext test(int i) {
			return getRuleContext(TestContext.class,i);
		}
		public TerminalNode COMMA() { return getToken(Python3Parser.COMMA, 0); }
		public AssertContext(Small_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterAssert(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitAssert(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitAssert(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprStmtContext extends Small_stmtContext {
		public Test_listContext test_list() {
			return getRuleContext(Test_listContext.class,0);
		}
		public ExprStmtContext(Small_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterExprStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitExprStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitExprStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AnnAssignContext extends Small_stmtContext {
		public Test_listContext target;
		public Type_exprContext type;
		public Test_listContext value;
		public TerminalNode COLON() { return getToken(Python3Parser.COLON, 0); }
		public List<Test_listContext> test_list() {
			return getRuleContexts(Test_listContext.class);
		}
		public Test_listContext test_list(int i) {
			return getRuleContext(Test_listContext.class,i);
		}
		public Type_exprContext type_expr() {
			return getRuleContext(Type_exprContext.class,0);
		}
		public TerminalNode EQUAL() { return getToken(Python3Parser.EQUAL, 0); }
		public AnnAssignContext(Small_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterAnnAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitAnnAssign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitAnnAssign(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PassContext extends Small_stmtContext {
		public TerminalNode PASS() { return getToken(Python3Parser.PASS, 0); }
		public PassContext(Small_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterPass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitPass(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitPass(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BreakContext extends Small_stmtContext {
		public TerminalNode BREAK() { return getToken(Python3Parser.BREAK, 0); }
		public BreakContext(Small_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterBreak(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitBreak(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitBreak(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssignContext extends Small_stmtContext {
		public Test_listContext target;
		public Test_listContext value;
		public TerminalNode EQUAL() { return getToken(Python3Parser.EQUAL, 0); }
		public List<Test_listContext> test_list() {
			return getRuleContexts(Test_listContext.class);
		}
		public Test_listContext test_list(int i) {
			return getRuleContext(Test_listContext.class,i);
		}
		public AssignContext(Small_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitAssign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitAssign(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DelContext extends Small_stmtContext {
		public TerminalNode DEL() { return getToken(Python3Parser.DEL, 0); }
		public Expr_listContext expr_list() {
			return getRuleContext(Expr_listContext.class,0);
		}
		public DelContext(Small_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterDel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitDel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitDel(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ContinueContext extends Small_stmtContext {
		public TerminalNode CONTINUE() { return getToken(Python3Parser.CONTINUE, 0); }
		public ContinueContext(Small_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterContinue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitContinue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitContinue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Small_stmtContext small_stmt() throws RecognitionException {
		Small_stmtContext _localctx = new Small_stmtContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_small_stmt);
		int _la;
		try {
			setState(256);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				_localctx = new ExprStmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(225);
				test_list();
				}
				break;
			case 2:
				_localctx = new AssignContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(226);
				((AssignContext)_localctx).target = test_list();
				setState(227);
				match(EQUAL);
				setState(228);
				((AssignContext)_localctx).value = test_list();
				}
				break;
			case 3:
				_localctx = new AnnAssignContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(230);
				((AnnAssignContext)_localctx).target = test_list();
				setState(231);
				match(COLON);
				setState(232);
				((AnnAssignContext)_localctx).type = type_expr();
				setState(235);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==EQUAL) {
					{
					setState(233);
					match(EQUAL);
					setState(234);
					((AnnAssignContext)_localctx).value = test_list();
					}
				}

				}
				break;
			case 4:
				_localctx = new AugAssignContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(237);
				test_list();
				setState(238);
				aug_assign();
				setState(239);
				test_list();
				}
				break;
			case 5:
				_localctx = new DelContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(241);
				match(DEL);
				setState(242);
				expr_list();
				}
				break;
			case 6:
				_localctx = new PassContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(243);
				match(PASS);
				}
				break;
			case 7:
				_localctx = new BreakContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(244);
				match(BREAK);
				}
				break;
			case 8:
				_localctx = new ContinueContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(245);
				match(CONTINUE);
				}
				break;
			case 9:
				_localctx = new ReturnContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(246);
				match(RETURN);
				setState(248);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((_la) & ~0x3f) == 0 && ((1L << _la) & 2306089781392050242L) != 0 || (((_la - 82)) & ~0x3f) == 0 && ((1L << (_la - 82)) & 197L) != 0) {
					{
					setState(247);
					test_list();
					}
				}

				}
				break;
			case 10:
				_localctx = new AssertContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(250);
				match(ASSERT);
				setState(251);
				((AssertContext)_localctx).value = test(0);
				setState(254);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(252);
					match(COMMA);
					setState(253);
					((AssertContext)_localctx).msg = test(0);
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Aug_assignContext extends ParserRuleContext {
		public TerminalNode PLUSEQUAL() { return getToken(Python3Parser.PLUSEQUAL, 0); }
		public TerminalNode MINEQUAL() { return getToken(Python3Parser.MINEQUAL, 0); }
		public TerminalNode STAREQUAL() { return getToken(Python3Parser.STAREQUAL, 0); }
		public TerminalNode ATEQUAL() { return getToken(Python3Parser.ATEQUAL, 0); }
		public TerminalNode SLASHEQUAL() { return getToken(Python3Parser.SLASHEQUAL, 0); }
		public TerminalNode PERCENTEQUAL() { return getToken(Python3Parser.PERCENTEQUAL, 0); }
		public TerminalNode AMPEREQUAL() { return getToken(Python3Parser.AMPEREQUAL, 0); }
		public TerminalNode VBAREQUAL() { return getToken(Python3Parser.VBAREQUAL, 0); }
		public TerminalNode CIRCUMFLEXEQUAL() { return getToken(Python3Parser.CIRCUMFLEXEQUAL, 0); }
		public TerminalNode LEFTSHIFTEQUAL() { return getToken(Python3Parser.LEFTSHIFTEQUAL, 0); }
		public TerminalNode RIGHTSHIFTEQUAL() { return getToken(Python3Parser.RIGHTSHIFTEQUAL, 0); }
		public TerminalNode DOUBLESTAREQUAL() { return getToken(Python3Parser.DOUBLESTAREQUAL, 0); }
		public TerminalNode DOUBLESLASHEQUAL() { return getToken(Python3Parser.DOUBLESLASHEQUAL, 0); }
		public Aug_assignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aug_assign; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterAug_assign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitAug_assign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitAug_assign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Aug_assignContext aug_assign() throws RecognitionException {
		Aug_assignContext _localctx = new Aug_assignContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_aug_assign);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(258);
			_la = _input.LA(1);
			if ( !((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & 22527L) != 0) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Compound_stmtContext extends ParserRuleContext {
		public Compound_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compound_stmt; }
	 
		public Compound_stmtContext() { }
		public void copyFrom(Compound_stmtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ClassDefContext extends Compound_stmtContext {
		public Class_defContext class_def() {
			return getRuleContext(Class_defContext.class,0);
		}
		public ClassDefContext(Compound_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterClassDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitClassDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitClassDef(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FuncDefContext extends Compound_stmtContext {
		public Func_defContext func_def() {
			return getRuleContext(Func_defContext.class,0);
		}
		public FuncDefContext(Compound_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterFuncDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitFuncDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitFuncDef(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ForContext extends Compound_stmtContext {
		public Expr_listContext tgt;
		public Test_listContext iter;
		public SuiteContext body;
		public TerminalNode FOR() { return getToken(Python3Parser.FOR, 0); }
		public TerminalNode IN() { return getToken(Python3Parser.IN, 0); }
		public TerminalNode COLON() { return getToken(Python3Parser.COLON, 0); }
		public Expr_listContext expr_list() {
			return getRuleContext(Expr_listContext.class,0);
		}
		public Test_listContext test_list() {
			return getRuleContext(Test_listContext.class,0);
		}
		public SuiteContext suite() {
			return getRuleContext(SuiteContext.class,0);
		}
		public ForContext(Compound_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterFor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitFor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitFor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class WhileContext extends Compound_stmtContext {
		public ExprContext tst;
		public SuiteContext body;
		public TerminalNode WHILE() { return getToken(Python3Parser.WHILE, 0); }
		public TerminalNode COLON() { return getToken(Python3Parser.COLON, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public SuiteContext suite() {
			return getRuleContext(SuiteContext.class,0);
		}
		public WhileContext(Compound_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterWhile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitWhile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitWhile(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IfContext extends Compound_stmtContext {
		public TestContext tst;
		public SuiteContext body;
		public TestContext test;
		public List<TestContext> elif_tsts = new ArrayList<TestContext>();
		public SuiteContext suite;
		public List<SuiteContext> elif_bodies = new ArrayList<SuiteContext>();
		public SuiteContext orelse;
		public TerminalNode IF() { return getToken(Python3Parser.IF, 0); }
		public List<TerminalNode> COLON() { return getTokens(Python3Parser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(Python3Parser.COLON, i);
		}
		public List<TestContext> test() {
			return getRuleContexts(TestContext.class);
		}
		public TestContext test(int i) {
			return getRuleContext(TestContext.class,i);
		}
		public List<SuiteContext> suite() {
			return getRuleContexts(SuiteContext.class);
		}
		public SuiteContext suite(int i) {
			return getRuleContext(SuiteContext.class,i);
		}
		public List<TerminalNode> ELIF() { return getTokens(Python3Parser.ELIF); }
		public TerminalNode ELIF(int i) {
			return getToken(Python3Parser.ELIF, i);
		}
		public TerminalNode ELSE() { return getToken(Python3Parser.ELSE, 0); }
		public IfContext(Compound_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterIf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitIf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitIf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Compound_stmtContext compound_stmt() throws RecognitionException {
		Compound_stmtContext _localctx = new Compound_stmtContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_compound_stmt);
		int _la;
		try {
			setState(293);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				_localctx = new IfContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(260);
				match(IF);
				setState(261);
				((IfContext)_localctx).tst = test(0);
				setState(262);
				match(COLON);
				setState(263);
				((IfContext)_localctx).body = suite();
				setState(271);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ELIF) {
					{
					{
					setState(264);
					match(ELIF);
					setState(265);
					((IfContext)_localctx).test = test(0);
					((IfContext)_localctx).elif_tsts.add(((IfContext)_localctx).test);
					setState(266);
					match(COLON);
					setState(267);
					((IfContext)_localctx).suite = suite();
					((IfContext)_localctx).elif_bodies.add(((IfContext)_localctx).suite);
					}
					}
					setState(273);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(277);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ELSE) {
					{
					setState(274);
					match(ELSE);
					setState(275);
					match(COLON);
					setState(276);
					((IfContext)_localctx).orelse = suite();
					}
				}

				}
				break;
			case 2:
				_localctx = new WhileContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(279);
				match(WHILE);
				setState(280);
				((WhileContext)_localctx).tst = expr(0);
				setState(281);
				match(COLON);
				setState(282);
				((WhileContext)_localctx).body = suite();
				}
				break;
			case 3:
				_localctx = new ForContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(284);
				match(FOR);
				setState(285);
				((ForContext)_localctx).tgt = expr_list();
				setState(286);
				match(IN);
				setState(287);
				((ForContext)_localctx).iter = test_list();
				setState(288);
				match(COLON);
				setState(289);
				((ForContext)_localctx).body = suite();
				}
				break;
			case 4:
				_localctx = new FuncDefContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(291);
				func_def();
				}
				break;
			case 5:
				_localctx = new ClassDefContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(292);
				class_def();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AnnotationContext extends ParserRuleContext {
		public TerminalNode ANNO_COMMENT() { return getToken(Python3Parser.ANNO_COMMENT, 0); }
		public TerminalNode NL() { return getToken(Python3Parser.NL, 0); }
		public AnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterAnnotation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitAnnotation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationContext annotation() throws RecognitionException {
		AnnotationContext _localctx = new AnnotationContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_annotation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(295);
			match(ANNO_COMMENT);
			setState(296);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SuiteContext extends ParserRuleContext {
		public SuiteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_suite; }
	 
		public SuiteContext() { }
		public void copyFrom(SuiteContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SimpleStmtContext extends SuiteContext {
		public Simple_stmtContext simple_stmt() {
			return getRuleContext(Simple_stmtContext.class,0);
		}
		public SimpleStmtContext(SuiteContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterSimpleStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitSimpleStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitSimpleStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends SuiteContext {
		public StmtContext stmt;
		public List<StmtContext> body = new ArrayList<StmtContext>();
		public TerminalNode INDENT() { return getToken(Python3Parser.INDENT, 0); }
		public TerminalNode DEDENT() { return getToken(Python3Parser.DEDENT, 0); }
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public BlockContext(SuiteContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SuiteContext suite() throws RecognitionException {
		SuiteContext _localctx = new SuiteContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_suite);
		int _la;
		try {
			setState(307);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FALSE:
			case PASS:
			case NONE:
			case BREAK:
			case TRUE:
			case RETURN:
			case CONTINUE:
			case LAMBDA:
			case ASSERT:
			case DEL:
			case NOT:
			case LPAR:
			case LSQB:
			case LBRACE:
			case PLUS:
			case MINUS:
			case STAR:
			case TILDE:
			case ELLIPSIS:
			case STRING:
			case NAME:
			case NUMBER:
				_localctx = new SimpleStmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(298);
				simple_stmt();
				}
				break;
			case INDENT:
				_localctx = new BlockContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(299);
				match(INDENT);
				setState(301); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(300);
					((BlockContext)_localctx).stmt = stmt();
					((BlockContext)_localctx).body.add(((BlockContext)_localctx).stmt);
					}
					}
					setState(303); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( ((_la) & ~0x3f) == 0 && ((1L << _la) & 2306089790221490402L) != 0 || (((_la - 79)) & ~0x3f) == 0 && ((1L << (_la - 79)) & 1705L) != 0 );
				setState(305);
				match(DEDENT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Comp_opContext extends ParserRuleContext {
		public TerminalNode LESS() { return getToken(Python3Parser.LESS, 0); }
		public TerminalNode GREATER() { return getToken(Python3Parser.GREATER, 0); }
		public TerminalNode EQEQUAL() { return getToken(Python3Parser.EQEQUAL, 0); }
		public TerminalNode GREATEREQUAL() { return getToken(Python3Parser.GREATEREQUAL, 0); }
		public TerminalNode LESSEQUAL() { return getToken(Python3Parser.LESSEQUAL, 0); }
		public TerminalNode INEQUAL() { return getToken(Python3Parser.INEQUAL, 0); }
		public TerminalNode NOTEQUAL() { return getToken(Python3Parser.NOTEQUAL, 0); }
		public TerminalNode IN() { return getToken(Python3Parser.IN, 0); }
		public TerminalNode NOT() { return getToken(Python3Parser.NOT, 0); }
		public TerminalNode IS() { return getToken(Python3Parser.IS, 0); }
		public Comp_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comp_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterComp_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitComp_op(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitComp_op(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Comp_opContext comp_op() throws RecognitionException {
		Comp_opContext _localctx = new Comp_opContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_comp_op);
		try {
			setState(322);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(309);
				match(LESS);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(310);
				match(GREATER);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(311);
				match(EQEQUAL);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(312);
				match(GREATEREQUAL);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(313);
				match(LESSEQUAL);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(314);
				match(INEQUAL);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(315);
				match(NOTEQUAL);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(316);
				match(IN);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(317);
				match(NOT);
				setState(318);
				match(IN);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(319);
				match(IS);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(320);
				match(IS);
				setState(321);
				match(NOT);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TestContext extends ParserRuleContext {
		public TestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_test; }
	 
		public TestContext() { }
		public void copyFrom(TestContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IfExpContext extends TestContext {
		public TestContext body;
		public TestContext tst;
		public TestContext orelse;
		public TerminalNode IF() { return getToken(Python3Parser.IF, 0); }
		public TerminalNode ELSE() { return getToken(Python3Parser.ELSE, 0); }
		public List<TestContext> test() {
			return getRuleContexts(TestContext.class);
		}
		public TestContext test(int i) {
			return getRuleContext(TestContext.class,i);
		}
		public IfExpContext(TestContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterIfExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitIfExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitIfExp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TExprContext extends TestContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TExprContext(TestContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterTExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitTExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitTExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LambdaContext extends TestContext {
		public TerminalNode LAMBDA() { return getToken(Python3Parser.LAMBDA, 0); }
		public TerminalNode COLON() { return getToken(Python3Parser.COLON, 0); }
		public TestContext test() {
			return getRuleContext(TestContext.class,0);
		}
		public VarargslistContext varargslist() {
			return getRuleContext(VarargslistContext.class,0);
		}
		public LambdaContext(TestContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterLambda(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitLambda(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitLambda(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TestContext test() throws RecognitionException {
		return test(0);
	}

	private TestContext test(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TestContext _localctx = new TestContext(_ctx, _parentState);
		TestContext _prevctx = _localctx;
		int _startState = 48;
		enterRecursionRule(_localctx, 48, RULE_test, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(332);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LAMBDA:
				{
				_localctx = new LambdaContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(325);
				match(LAMBDA);
				setState(327);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NAME) {
					{
					setState(326);
					varargslist();
					}
				}

				setState(329);
				match(COLON);
				setState(330);
				test(2);
				}
				break;
			case FALSE:
			case NONE:
			case TRUE:
			case NOT:
			case LPAR:
			case LSQB:
			case LBRACE:
			case PLUS:
			case MINUS:
			case STAR:
			case TILDE:
			case ELLIPSIS:
			case STRING:
			case NAME:
			case NUMBER:
				{
				_localctx = new TExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(331);
				expr(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(342);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new IfExpContext(new TestContext(_parentctx, _parentState));
					((IfExpContext)_localctx).body = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_test);
					setState(334);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(335);
					match(IF);
					setState(336);
					((IfExpContext)_localctx).tst = test(0);
					setState(337);
					match(ELSE);
					setState(338);
					((IfExpContext)_localctx).orelse = test(4);
					}
					} 
				}
				setState(344);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndExprContext extends ExprContext {
		public List<ExprContext> exprs = new ArrayList<ExprContext>();
		public ExprContext expr;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> AND() { return getTokens(Python3Parser.AND); }
		public TerminalNode AND(int i) {
			return getToken(Python3Parser.AND, i);
		}
		public AndExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitAndExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BitOrContext extends ExprContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode VBAR() { return getToken(Python3Parser.VBAR, 0); }
		public BitOrContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterBitOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitBitOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitBitOr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StarExprContext extends ExprContext {
		public ExprContext expr;
		public List<ExprContext> value = new ArrayList<ExprContext>();
		public TerminalNode STAR() { return getToken(Python3Parser.STAR, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public StarExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterStarExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitStarExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitStarExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TermContext extends ExprContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode STAR() { return getToken(Python3Parser.STAR, 0); }
		public TerminalNode AT() { return getToken(Python3Parser.AT, 0); }
		public TerminalNode SLASH() { return getToken(Python3Parser.SLASH, 0); }
		public TerminalNode PERCENT() { return getToken(Python3Parser.PERCENT, 0); }
		public TerminalNode DOUBLESLASH() { return getToken(Python3Parser.DOUBLESLASH, 0); }
		public TermContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitTerm(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AtomExprContext extends ExprContext {
		public Atom_exprContext value;
		public Atom_exprContext atom_expr() {
			return getRuleContext(Atom_exprContext.class,0);
		}
		public AtomExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterAtomExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitAtomExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitAtomExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrExprContext extends ExprContext {
		public List<ExprContext> exprs = new ArrayList<ExprContext>();
		public ExprContext expr;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(Python3Parser.OR); }
		public TerminalNode OR(int i) {
			return getToken(Python3Parser.OR, i);
		}
		public OrExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterOrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitOrExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitOrExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PlusMinusContext extends ExprContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(Python3Parser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(Python3Parser.MINUS, 0); }
		public PlusMinusContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterPlusMinus(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitPlusMinus(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitPlusMinus(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BitXorContext extends ExprContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode CIRCUMFLEX() { return getToken(Python3Parser.CIRCUMFLEX, 0); }
		public BitXorContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterBitXor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitBitXor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitBitXor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FactorContext extends ExprContext {
		public Token op;
		public ExprContext operand;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(Python3Parser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(Python3Parser.MINUS, 0); }
		public TerminalNode TILDE() { return getToken(Python3Parser.TILDE, 0); }
		public FactorContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterFactor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitFactor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitFactor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BitAndContext extends ExprContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode AMPER() { return getToken(Python3Parser.AMPER, 0); }
		public BitAndContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterBitAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitBitAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitBitAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CompareContext extends ExprContext {
		public ExprContext left;
		public Comp_opContext comp_op;
		public List<Comp_opContext> ops = new ArrayList<Comp_opContext>();
		public ExprContext expr;
		public List<ExprContext> comparators = new ArrayList<ExprContext>();
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<Comp_opContext> comp_op() {
			return getRuleContexts(Comp_opContext.class);
		}
		public Comp_opContext comp_op(int i) {
			return getRuleContext(Comp_opContext.class,i);
		}
		public CompareContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterCompare(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitCompare(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitCompare(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotExprContext extends ExprContext {
		public ExprContext operand;
		public TerminalNode NOT() { return getToken(Python3Parser.NOT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public NotExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterNotExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitNotExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitNotExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ShiftExprContext extends ExprContext {
		public ExprContext left;
		public Token op;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode LEFTSHIFT() { return getToken(Python3Parser.LEFTSHIFT, 0); }
		public TerminalNode RIGHTSHIFT() { return getToken(Python3Parser.RIGHTSHIFT, 0); }
		public ShiftExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterShiftExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitShiftExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitShiftExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PowerContext extends ExprContext {
		public Atom_exprContext left;
		public Token op;
		public ExprContext right;
		public Atom_exprContext atom_expr() {
			return getRuleContext(Atom_exprContext.class,0);
		}
		public TerminalNode DOUBLESTAR() { return getToken(Python3Parser.DOUBLESTAR, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public PowerContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterPower(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitPower(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitPower(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 50;
		enterRecursionRule(_localctx, 50, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(357);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(346);
				match(NOT);
				setState(347);
				((NotExprContext)_localctx).operand = expr(12);
				}
				break;
			case 2:
				{
				_localctx = new StarExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(348);
				match(STAR);
				setState(349);
				((StarExprContext)_localctx).expr = expr(10);
				((StarExprContext)_localctx).value.add(((StarExprContext)_localctx).expr);
				}
				break;
			case 3:
				{
				_localctx = new FactorContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(350);
				((FactorContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(((_la) & ~0x3f) == 0 && ((1L << _la) & 2305948562329960448L) != 0) ) {
					((FactorContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(351);
				((FactorContext)_localctx).operand = expr(3);
				}
				break;
			case 4:
				{
				_localctx = new PowerContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(352);
				((PowerContext)_localctx).left = atom_expr();
				setState(353);
				((PowerContext)_localctx).op = match(DOUBLESTAR);
				setState(354);
				((PowerContext)_localctx).right = expr(2);
				}
				break;
			case 5:
				{
				_localctx = new AtomExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(356);
				((AtomExprContext)_localctx).value = atom_expr();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(401);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(399);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
					case 1:
						{
						_localctx = new BitOrContext(new ExprContext(_parentctx, _parentState));
						((BitOrContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(359);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(360);
						((BitOrContext)_localctx).op = match(VBAR);
						setState(361);
						((BitOrContext)_localctx).right = expr(10);
						}
						break;
					case 2:
						{
						_localctx = new BitXorContext(new ExprContext(_parentctx, _parentState));
						((BitXorContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(362);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(363);
						((BitXorContext)_localctx).op = match(CIRCUMFLEX);
						setState(364);
						((BitXorContext)_localctx).right = expr(9);
						}
						break;
					case 3:
						{
						_localctx = new BitAndContext(new ExprContext(_parentctx, _parentState));
						((BitAndContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(365);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(366);
						((BitAndContext)_localctx).op = match(AMPER);
						setState(367);
						((BitAndContext)_localctx).right = expr(8);
						}
						break;
					case 4:
						{
						_localctx = new ShiftExprContext(new ExprContext(_parentctx, _parentState));
						((ShiftExprContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(368);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(369);
						((ShiftExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==LEFTSHIFT || _la==RIGHTSHIFT) ) {
							((ShiftExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(370);
						((ShiftExprContext)_localctx).right = expr(7);
						}
						break;
					case 5:
						{
						_localctx = new PlusMinusContext(new ExprContext(_parentctx, _parentState));
						((PlusMinusContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(371);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(372);
						((PlusMinusContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((PlusMinusContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(373);
						((PlusMinusContext)_localctx).right = expr(6);
						}
						break;
					case 6:
						{
						_localctx = new TermContext(new ExprContext(_parentctx, _parentState));
						((TermContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(374);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(375);
						((TermContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la - 47)) & ~0x3f) == 0 && ((1L << (_la - 47)) & 5368709379L) != 0) ) {
							((TermContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(376);
						((TermContext)_localctx).right = expr(5);
						}
						break;
					case 7:
						{
						_localctx = new OrExprContext(new ExprContext(_parentctx, _parentState));
						((OrExprContext)_localctx).exprs.add(_prevctx);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(377);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(380); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(378);
								match(OR);
								setState(379);
								((OrExprContext)_localctx).expr = expr(0);
								((OrExprContext)_localctx).exprs.add(((OrExprContext)_localctx).expr);
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(382); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
						} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						}
						break;
					case 8:
						{
						_localctx = new AndExprContext(new ExprContext(_parentctx, _parentState));
						((AndExprContext)_localctx).exprs.add(_prevctx);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(384);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(387); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(385);
								match(AND);
								setState(386);
								((AndExprContext)_localctx).expr = expr(0);
								((AndExprContext)_localctx).exprs.add(((AndExprContext)_localctx).expr);
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(389); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
						} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						}
						break;
					case 9:
						{
						_localctx = new CompareContext(new ExprContext(_parentctx, _parentState));
						((CompareContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(391);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(395); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(392);
								((CompareContext)_localctx).comp_op = comp_op();
								((CompareContext)_localctx).ops.add(((CompareContext)_localctx).comp_op);
								setState(393);
								((CompareContext)_localctx).expr = expr(0);
								((CompareContext)_localctx).comparators.add(((CompareContext)_localctx).expr);
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(397); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
						} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						}
						break;
					}
					} 
				}
				setState(403);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Atom_exprContext extends ParserRuleContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public List<TrailerContext> trailer() {
			return getRuleContexts(TrailerContext.class);
		}
		public TrailerContext trailer(int i) {
			return getRuleContext(TrailerContext.class,i);
		}
		public Atom_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterAtom_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitAtom_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitAtom_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Atom_exprContext atom_expr() throws RecognitionException {
		Atom_exprContext _localctx = new Atom_exprContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_atom_expr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(404);
			atom();
			setState(408);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(405);
					trailer();
					}
					} 
				}
				setState(410);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AtomContext extends ParserRuleContext {
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
	 
		public AtomContext() { }
		public void copyFrom(AtomContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EllipsisContext extends AtomContext {
		public TerminalNode ELLIPSIS() { return getToken(Python3Parser.ELLIPSIS, 0); }
		public EllipsisContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterEllipsis(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitEllipsis(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitEllipsis(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ListLitContext extends AtomContext {
		public TerminalNode LSQB() { return getToken(Python3Parser.LSQB, 0); }
		public TerminalNode RSQB() { return getToken(Python3Parser.RSQB, 0); }
		public Test_listContext test_list() {
			return getRuleContext(Test_listContext.class,0);
		}
		public ListLitContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterListLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitListLit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitListLit(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TrueContext extends AtomContext {
		public TerminalNode TRUE() { return getToken(Python3Parser.TRUE, 0); }
		public TrueContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterTrue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitTrue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitTrue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ListCompContext extends AtomContext {
		public TerminalNode LSQB() { return getToken(Python3Parser.LSQB, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Comp_forContext comp_for() {
			return getRuleContext(Comp_forContext.class,0);
		}
		public TerminalNode RSQB() { return getToken(Python3Parser.RSQB, 0); }
		public ListCompContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterListComp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitListComp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitListComp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FalseContext extends AtomContext {
		public TerminalNode FALSE() { return getToken(Python3Parser.FALSE, 0); }
		public FalseContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterFalse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitFalse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitFalse(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NameContext extends AtomContext {
		public Token id;
		public TerminalNode NAME() { return getToken(Python3Parser.NAME, 0); }
		public NameContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitName(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SetCompContext extends AtomContext {
		public TerminalNode LBRACE() { return getToken(Python3Parser.LBRACE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Comp_forContext comp_for() {
			return getRuleContext(Comp_forContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(Python3Parser.RBRACE, 0); }
		public SetCompContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterSetComp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitSetComp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitSetComp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StrContext extends AtomContext {
		public Token STRING;
		public List<Token> str = new ArrayList<Token>();
		public List<TerminalNode> STRING() { return getTokens(Python3Parser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(Python3Parser.STRING, i);
		}
		public StrContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterStr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitStr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitStr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SetLitContext extends AtomContext {
		public TerminalNode LBRACE() { return getToken(Python3Parser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(Python3Parser.RBRACE, 0); }
		public Test_listContext test_list() {
			return getRuleContext(Test_listContext.class,0);
		}
		public SetLitContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterSetLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitSetLit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitSetLit(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumberContext extends AtomContext {
		public Token n;
		public TerminalNode NUMBER() { return getToken(Python3Parser.NUMBER, 0); }
		public NumberContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DictCompContext extends AtomContext {
		public ExprContext key;
		public ExprContext value;
		public TerminalNode LBRACE() { return getToken(Python3Parser.LBRACE, 0); }
		public TerminalNode COLON() { return getToken(Python3Parser.COLON, 0); }
		public Comp_forContext comp_for() {
			return getRuleContext(Comp_forContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(Python3Parser.RBRACE, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public DictCompContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterDictComp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitDictComp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitDictComp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParensExprContext extends AtomContext {
		public TerminalNode LPAR() { return getToken(Python3Parser.LPAR, 0); }
		public TestContext test() {
			return getRuleContext(TestContext.class,0);
		}
		public TerminalNode RPAR() { return getToken(Python3Parser.RPAR, 0); }
		public ParensExprContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterParensExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitParensExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitParensExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DictLitContext extends AtomContext {
		public TestContext test;
		public List<TestContext> keys = new ArrayList<TestContext>();
		public List<TestContext> values = new ArrayList<TestContext>();
		public TerminalNode LBRACE() { return getToken(Python3Parser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(Python3Parser.RBRACE, 0); }
		public List<TerminalNode> COLON() { return getTokens(Python3Parser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(Python3Parser.COLON, i);
		}
		public List<TestContext> test() {
			return getRuleContexts(TestContext.class);
		}
		public TestContext test(int i) {
			return getRuleContext(TestContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Python3Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Python3Parser.COMMA, i);
		}
		public DictLitContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterDictLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitDictLit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitDictLit(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NoneContext extends AtomContext {
		public TerminalNode NONE() { return getToken(Python3Parser.NONE, 0); }
		public NoneContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterNone(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitNone(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitNone(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TupleContext extends AtomContext {
		public TerminalNode LPAR() { return getToken(Python3Parser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(Python3Parser.RPAR, 0); }
		public Test_listContext test_list() {
			return getRuleContext(Test_listContext.class,0);
		}
		public TupleContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterTuple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitTuple(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitTuple(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_atom);
		int _la;
		try {
			int _alt;
			setState(475);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				_localctx = new ParensExprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(411);
				match(LPAR);
				setState(412);
				test(0);
				setState(413);
				match(RPAR);
				}
				break;
			case 2:
				_localctx = new TupleContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(415);
				match(LPAR);
				setState(417);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((_la) & ~0x3f) == 0 && ((1L << _la) & 2306089781392050242L) != 0 || (((_la - 82)) & ~0x3f) == 0 && ((1L << (_la - 82)) & 197L) != 0) {
					{
					setState(416);
					test_list();
					}
				}

				setState(419);
				match(RPAR);
				}
				break;
			case 3:
				_localctx = new ListLitContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(420);
				match(LSQB);
				setState(422);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((_la) & ~0x3f) == 0 && ((1L << _la) & 2306089781392050242L) != 0 || (((_la - 82)) & ~0x3f) == 0 && ((1L << (_la - 82)) & 197L) != 0) {
					{
					setState(421);
					test_list();
					}
				}

				setState(424);
				match(RSQB);
				}
				break;
			case 4:
				_localctx = new ListCompContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(425);
				match(LSQB);
				setState(426);
				expr(0);
				setState(427);
				comp_for();
				setState(428);
				match(RSQB);
				}
				break;
			case 5:
				_localctx = new DictLitContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(430);
				match(LBRACE);
				setState(444);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((_la) & ~0x3f) == 0 && ((1L << _la) & 2306089781392050242L) != 0 || (((_la - 82)) & ~0x3f) == 0 && ((1L << (_la - 82)) & 197L) != 0) {
					{
					setState(431);
					((DictLitContext)_localctx).test = test(0);
					((DictLitContext)_localctx).keys.add(((DictLitContext)_localctx).test);
					setState(432);
					match(COLON);
					setState(433);
					((DictLitContext)_localctx).test = test(0);
					((DictLitContext)_localctx).values.add(((DictLitContext)_localctx).test);
					setState(439);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
					case 1:
						{
						setState(434);
						match(COMMA);
						setState(435);
						((DictLitContext)_localctx).test = test(0);
						((DictLitContext)_localctx).keys.add(((DictLitContext)_localctx).test);
						setState(436);
						match(COLON);
						setState(437);
						((DictLitContext)_localctx).test = test(0);
						((DictLitContext)_localctx).values.add(((DictLitContext)_localctx).test);
						}
						break;
					}
					setState(442);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==COMMA) {
						{
						setState(441);
						match(COMMA);
						}
					}

					}
				}

				setState(446);
				match(RBRACE);
				}
				break;
			case 6:
				_localctx = new DictCompContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(447);
				match(LBRACE);
				setState(448);
				((DictCompContext)_localctx).key = expr(0);
				setState(449);
				match(COLON);
				setState(450);
				((DictCompContext)_localctx).value = expr(0);
				setState(451);
				comp_for();
				setState(452);
				match(RBRACE);
				}
				break;
			case 7:
				_localctx = new SetLitContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(454);
				match(LBRACE);
				setState(456);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((_la) & ~0x3f) == 0 && ((1L << _la) & 2306089781392050242L) != 0 || (((_la - 82)) & ~0x3f) == 0 && ((1L << (_la - 82)) & 197L) != 0) {
					{
					setState(455);
					test_list();
					}
				}

				setState(458);
				match(RBRACE);
				}
				break;
			case 8:
				_localctx = new SetCompContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(459);
				match(LBRACE);
				setState(460);
				expr(0);
				setState(461);
				comp_for();
				setState(462);
				match(RBRACE);
				}
				break;
			case 9:
				_localctx = new NameContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(464);
				((NameContext)_localctx).id = match(NAME);
				}
				break;
			case 10:
				_localctx = new NumberContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(465);
				((NumberContext)_localctx).n = match(NUMBER);
				}
				break;
			case 11:
				_localctx = new StrContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(467); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(466);
						((StrContext)_localctx).STRING = match(STRING);
						((StrContext)_localctx).str.add(((StrContext)_localctx).STRING);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(469); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			case 12:
				_localctx = new EllipsisContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(471);
				match(ELLIPSIS);
				}
				break;
			case 13:
				_localctx = new NoneContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(472);
				match(NONE);
				}
				break;
			case 14:
				_localctx = new TrueContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(473);
				match(TRUE);
				}
				break;
			case 15:
				_localctx = new FalseContext(_localctx);
				enterOuterAlt(_localctx, 15);
				{
				setState(474);
				match(FALSE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TrailerContext extends ParserRuleContext {
		public TrailerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trailer; }
	 
		public TrailerContext() { }
		public void copyFrom(TrailerContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CallTrailerContext extends TrailerContext {
		public TerminalNode LPAR() { return getToken(Python3Parser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(Python3Parser.RPAR, 0); }
		public ArglistContext arglist() {
			return getRuleContext(ArglistContext.class,0);
		}
		public CallTrailerContext(TrailerContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterCallTrailer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitCallTrailer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitCallTrailer(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SubscrTrailerContext extends TrailerContext {
		public TerminalNode LSQB() { return getToken(Python3Parser.LSQB, 0); }
		public SubscriptlistContext subscriptlist() {
			return getRuleContext(SubscriptlistContext.class,0);
		}
		public TerminalNode RSQB() { return getToken(Python3Parser.RSQB, 0); }
		public SubscrTrailerContext(TrailerContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterSubscrTrailer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitSubscrTrailer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitSubscrTrailer(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AttrTrailerContext extends TrailerContext {
		public TerminalNode DOT() { return getToken(Python3Parser.DOT, 0); }
		public TerminalNode NAME() { return getToken(Python3Parser.NAME, 0); }
		public AttrTrailerContext(TrailerContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterAttrTrailer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitAttrTrailer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitAttrTrailer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TrailerContext trailer() throws RecognitionException {
		TrailerContext _localctx = new TrailerContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_trailer);
		int _la;
		try {
			setState(488);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAR:
				_localctx = new CallTrailerContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(477);
				match(LPAR);
				setState(479);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((_la) & ~0x3f) == 0 && ((1L << _la) & 2306089781392050242L) != 0 || (((_la - 82)) & ~0x3f) == 0 && ((1L << (_la - 82)) & 197L) != 0) {
					{
					setState(478);
					arglist();
					}
				}

				setState(481);
				match(RPAR);
				}
				break;
			case LSQB:
				_localctx = new SubscrTrailerContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(482);
				match(LSQB);
				setState(483);
				subscriptlist();
				setState(484);
				match(RSQB);
				}
				break;
			case DOT:
				_localctx = new AttrTrailerContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(486);
				match(DOT);
				setState(487);
				match(NAME);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SubscriptlistContext extends ParserRuleContext {
		public List<SubscriptContext> subscript() {
			return getRuleContexts(SubscriptContext.class);
		}
		public SubscriptContext subscript(int i) {
			return getRuleContext(SubscriptContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Python3Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Python3Parser.COMMA, i);
		}
		public SubscriptlistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subscriptlist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterSubscriptlist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitSubscriptlist(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitSubscriptlist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubscriptlistContext subscriptlist() throws RecognitionException {
		SubscriptlistContext _localctx = new SubscriptlistContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_subscriptlist);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(490);
			subscript();
			setState(495);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(491);
					match(COMMA);
					setState(492);
					subscript();
					}
					} 
				}
				setState(497);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
			}
			setState(499);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(498);
				match(COMMA);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SubscriptContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode COLON() { return getToken(Python3Parser.COLON, 0); }
		public SliceopContext sliceop() {
			return getRuleContext(SliceopContext.class,0);
		}
		public SubscriptContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subscript; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterSubscript(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitSubscript(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitSubscript(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubscriptContext subscript() throws RecognitionException {
		SubscriptContext _localctx = new SubscriptContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_subscript);
		int _la;
		try {
			setState(512);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,58,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(501);
				expr(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(503);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((_la) & ~0x3f) == 0 && ((1L << _la) & 2306089781391525954L) != 0 || (((_la - 82)) & ~0x3f) == 0 && ((1L << (_la - 82)) & 197L) != 0) {
					{
					setState(502);
					expr(0);
					}
				}

				setState(505);
				match(COLON);
				setState(507);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((_la) & ~0x3f) == 0 && ((1L << _la) & 2306089781391525954L) != 0 || (((_la - 82)) & ~0x3f) == 0 && ((1L << (_la - 82)) & 197L) != 0) {
					{
					setState(506);
					expr(0);
					}
				}

				setState(510);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COLON) {
					{
					setState(509);
					sliceop();
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SliceopContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(Python3Parser.COLON, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public SliceopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sliceop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterSliceop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitSliceop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitSliceop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SliceopContext sliceop() throws RecognitionException {
		SliceopContext _localctx = new SliceopContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_sliceop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(514);
			match(COLON);
			setState(516);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 2306089781391525954L) != 0 || (((_la - 82)) & ~0x3f) == 0 && ((1L << (_la - 82)) & 197L) != 0) {
				{
				setState(515);
				expr(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expr_listContext extends ParserRuleContext {
		public ExprContext expr;
		public List<ExprContext> elems = new ArrayList<ExprContext>();
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Python3Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Python3Parser.COMMA, i);
		}
		public Expr_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterExpr_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitExpr_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitExpr_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expr_listContext expr_list() throws RecognitionException {
		Expr_listContext _localctx = new Expr_listContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_expr_list);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(518);
			((Expr_listContext)_localctx).expr = expr(0);
			((Expr_listContext)_localctx).elems.add(((Expr_listContext)_localctx).expr);
			setState(523);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,60,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(519);
					match(COMMA);
					setState(520);
					((Expr_listContext)_localctx).expr = expr(0);
					((Expr_listContext)_localctx).elems.add(((Expr_listContext)_localctx).expr);
					}
					} 
				}
				setState(525);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,60,_ctx);
			}
			setState(527);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(526);
				match(COMMA);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Test_listContext extends ParserRuleContext {
		public TestContext test;
		public List<TestContext> elems = new ArrayList<TestContext>();
		public List<TestContext> test() {
			return getRuleContexts(TestContext.class);
		}
		public TestContext test(int i) {
			return getRuleContext(TestContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Python3Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Python3Parser.COMMA, i);
		}
		public Test_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_test_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterTest_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitTest_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitTest_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Test_listContext test_list() throws RecognitionException {
		Test_listContext _localctx = new Test_listContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_test_list);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(529);
			((Test_listContext)_localctx).test = test(0);
			((Test_listContext)_localctx).elems.add(((Test_listContext)_localctx).test);
			setState(534);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,62,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(530);
					match(COMMA);
					setState(531);
					((Test_listContext)_localctx).test = test(0);
					((Test_listContext)_localctx).elems.add(((Test_listContext)_localctx).test);
					}
					} 
				}
				setState(536);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,62,_ctx);
			}
			setState(538);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(537);
				match(COMMA);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArglistContext extends ParserRuleContext {
		public ArgumentContext argument;
		public List<ArgumentContext> arg = new ArrayList<ArgumentContext>();
		public List<ArgumentContext> argument() {
			return getRuleContexts(ArgumentContext.class);
		}
		public ArgumentContext argument(int i) {
			return getRuleContext(ArgumentContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Python3Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Python3Parser.COMMA, i);
		}
		public ArglistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arglist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterArglist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitArglist(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitArglist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArglistContext arglist() throws RecognitionException {
		ArglistContext _localctx = new ArglistContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_arglist);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(540);
			((ArglistContext)_localctx).argument = argument();
			((ArglistContext)_localctx).arg.add(((ArglistContext)_localctx).argument);
			setState(545);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,64,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(541);
					match(COMMA);
					setState(542);
					((ArglistContext)_localctx).argument = argument();
					((ArglistContext)_localctx).arg.add(((ArglistContext)_localctx).argument);
					}
					} 
				}
				setState(547);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,64,_ctx);
			}
			setState(549);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(548);
				match(COMMA);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentContext extends ParserRuleContext {
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
	 
		public ArgumentContext() { }
		public void copyFrom(ArgumentContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GeneratorArgContext extends ArgumentContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Comp_forContext comp_for() {
			return getRuleContext(Comp_forContext.class,0);
		}
		public GeneratorArgContext(ArgumentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterGeneratorArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitGeneratorArg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitGeneratorArg(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StarArgContext extends ArgumentContext {
		public TerminalNode STAR() { return getToken(Python3Parser.STAR, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public StarArgContext(ArgumentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterStarArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitStarArg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitStarArg(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TestArgContext extends ArgumentContext {
		public TestContext test() {
			return getRuleContext(TestContext.class,0);
		}
		public TestArgContext(ArgumentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterTestArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitTestArg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitTestArg(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NamedArgContext extends ArgumentContext {
		public TerminalNode NAME() { return getToken(Python3Parser.NAME, 0); }
		public TerminalNode EQUAL() { return getToken(Python3Parser.EQUAL, 0); }
		public TestContext test() {
			return getRuleContext(TestContext.class,0);
		}
		public NamedArgContext(ArgumentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterNamedArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitNamedArg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitNamedArg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_argument);
		try {
			setState(560);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,66,_ctx) ) {
			case 1:
				_localctx = new TestArgContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(551);
				test(0);
				}
				break;
			case 2:
				_localctx = new GeneratorArgContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(552);
				expr(0);
				setState(553);
				comp_for();
				}
				break;
			case 3:
				_localctx = new NamedArgContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(555);
				match(NAME);
				setState(556);
				match(EQUAL);
				setState(557);
				test(0);
				}
				break;
			case 4:
				_localctx = new StarArgContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(558);
				match(STAR);
				setState(559);
				expr(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Name_listContext extends ParserRuleContext {
		public Token NAME;
		public List<Token> n = new ArrayList<Token>();
		public List<TerminalNode> NAME() { return getTokens(Python3Parser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(Python3Parser.NAME, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Python3Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Python3Parser.COMMA, i);
		}
		public Name_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterName_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitName_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitName_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Name_listContext name_list() throws RecognitionException {
		Name_listContext _localctx = new Name_listContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_name_list);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(562);
			((Name_listContext)_localctx).NAME = match(NAME);
			((Name_listContext)_localctx).n.add(((Name_listContext)_localctx).NAME);
			setState(567);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,67,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(563);
					match(COMMA);
					setState(564);
					((Name_listContext)_localctx).NAME = match(NAME);
					((Name_listContext)_localctx).n.add(((Name_listContext)_localctx).NAME);
					}
					} 
				}
				setState(569);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,67,_ctx);
			}
			setState(571);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(570);
				match(COMMA);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Comp_forContext extends ParserRuleContext {
		public ExprContext coll;
		public ExprContext expr;
		public List<ExprContext> ifs = new ArrayList<ExprContext>();
		public TerminalNode FOR() { return getToken(Python3Parser.FOR, 0); }
		public Name_listContext name_list() {
			return getRuleContext(Name_listContext.class,0);
		}
		public TerminalNode IN() { return getToken(Python3Parser.IN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> IF() { return getTokens(Python3Parser.IF); }
		public TerminalNode IF(int i) {
			return getToken(Python3Parser.IF, i);
		}
		public Comp_forContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comp_for; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).enterComp_for(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Python3Listener ) ((Python3Listener)listener).exitComp_for(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Python3Visitor ) return ((Python3Visitor<? extends T>)visitor).visitComp_for(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Comp_forContext comp_for() throws RecognitionException {
		Comp_forContext _localctx = new Comp_forContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_comp_for);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(573);
			match(FOR);
			setState(574);
			name_list();
			setState(575);
			match(IN);
			setState(576);
			((Comp_forContext)_localctx).coll = expr(0);
			setState(581);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IF) {
				{
				{
				setState(577);
				match(IF);
				setState(578);
				((Comp_forContext)_localctx).expr = expr(0);
				((Comp_forContext)_localctx).ifs.add(((Comp_forContext)_localctx).expr);
				}
				}
				setState(583);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 24:
			return test_sempred((TestContext)_localctx, predIndex);
		case 25:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean test_sempred(TestContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 9);
		case 2:
			return precpred(_ctx, 8);
		case 3:
			return precpred(_ctx, 7);
		case 4:
			return precpred(_ctx, 6);
		case 5:
			return precpred(_ctx, 5);
		case 6:
			return precpred(_ctx, 4);
		case 7:
			return precpred(_ctx, 14);
		case 8:
			return precpred(_ctx, 13);
		case 9:
			return precpred(_ctx, 11);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\\\u0249\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"+
		"#\u0007#\u0002$\u0007$\u0002%\u0007%\u0001\u0000\u0001\u0000\u0005\u0000"+
		"O\b\u0000\n\u0000\f\u0000R\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0003\u0001X\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0003\u0002^\b\u0002\u0001\u0002\u0003\u0002a\b\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003h\b"+
		"\u0003\n\u0003\f\u0003k\t\u0003\u0001\u0004\u0005\u0004n\b\u0004\n\u0004"+
		"\f\u0004q\t\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003"+
		"\u0004w\b\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004|\b\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0005\u0005\u0084\b\u0005\n\u0005\f\u0005\u0087\t\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0003\u0006\u008c\b\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\b\u0005\b\u0093\b\b\n\b\f\b\u0096\t\b\u0001\b"+
		"\u0001\b\u0001\b\u0001\b\u0003\b\u009c\b\b\u0001\b\u0003\b\u009f\b\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\n\u0004\n\u00ab\b\n\u000b\n\f\n\u00ac\u0001\n\u0001\n\u0003\n\u00b1\b"+
		"\n\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u00b6\b\u000b\u0001"+
		"\u000b\u0001\u000b\u0003\u000b\u00ba\b\u000b\u0001\f\u0001\f\u0001\r\u0001"+
		"\r\u0001\r\u0005\r\u00c1\b\r\n\r\f\r\u00c4\t\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0005\u000e\u00c9\b\u000e\n\u000e\f\u000e\u00cc\t\u000e\u0001"+
		"\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u00d3"+
		"\b\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0005\u0011\u00d8\b\u0011"+
		"\n\u0011\f\u0011\u00db\t\u0011\u0001\u0011\u0003\u0011\u00de\b\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003"+
		"\u0012\u00ec\b\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0003\u0012\u00f9\b\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0003\u0012\u00ff\b\u0012\u0003\u0012\u0101\b\u0012\u0001\u0013"+
		"\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0005\u0014\u010e\b\u0014"+
		"\n\u0014\f\u0014\u0111\t\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0003"+
		"\u0014\u0116\b\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0003\u0014\u0126\b\u0014\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0004"+
		"\u0016\u012e\b\u0016\u000b\u0016\f\u0016\u012f\u0001\u0016\u0001\u0016"+
		"\u0003\u0016\u0134\b\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0003\u0017\u0143\b\u0017\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0003\u0018\u0148\b\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0003\u0018\u014d\b\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0005\u0018\u0155\b\u0018\n\u0018"+
		"\f\u0018\u0158\t\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0003\u0019\u0166\b\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0004\u0019\u017d\b\u0019\u000b\u0019\f\u0019\u017e\u0001"+
		"\u0019\u0001\u0019\u0001\u0019\u0004\u0019\u0184\b\u0019\u000b\u0019\f"+
		"\u0019\u0185\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0004\u0019"+
		"\u018c\b\u0019\u000b\u0019\f\u0019\u018d\u0005\u0019\u0190\b\u0019\n\u0019"+
		"\f\u0019\u0193\t\u0019\u0001\u001a\u0001\u001a\u0005\u001a\u0197\b\u001a"+
		"\n\u001a\f\u001a\u019a\t\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0001"+
		"\u001b\u0001\u001b\u0001\u001b\u0003\u001b\u01a2\b\u001b\u0001\u001b\u0001"+
		"\u001b\u0001\u001b\u0003\u001b\u01a7\b\u001b\u0001\u001b\u0001\u001b\u0001"+
		"\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001"+
		"\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001"+
		"\u001b\u0003\u001b\u01b8\b\u001b\u0001\u001b\u0003\u001b\u01bb\b\u001b"+
		"\u0003\u001b\u01bd\b\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0003\u001b\u01c9\b\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0004\u001b"+
		"\u01d4\b\u001b\u000b\u001b\f\u001b\u01d5\u0001\u001b\u0001\u001b\u0001"+
		"\u001b\u0001\u001b\u0003\u001b\u01dc\b\u001b\u0001\u001c\u0001\u001c\u0003"+
		"\u001c\u01e0\b\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0001\u001c\u0003\u001c\u01e9\b\u001c\u0001\u001d\u0001"+
		"\u001d\u0001\u001d\u0005\u001d\u01ee\b\u001d\n\u001d\f\u001d\u01f1\t\u001d"+
		"\u0001\u001d\u0003\u001d\u01f4\b\u001d\u0001\u001e\u0001\u001e\u0003\u001e"+
		"\u01f8\b\u001e\u0001\u001e\u0001\u001e\u0003\u001e\u01fc\b\u001e\u0001"+
		"\u001e\u0003\u001e\u01ff\b\u001e\u0003\u001e\u0201\b\u001e\u0001\u001f"+
		"\u0001\u001f\u0003\u001f\u0205\b\u001f\u0001 \u0001 \u0001 \u0005 \u020a"+
		"\b \n \f \u020d\t \u0001 \u0003 \u0210\b \u0001!\u0001!\u0001!\u0005!"+
		"\u0215\b!\n!\f!\u0218\t!\u0001!\u0003!\u021b\b!\u0001\"\u0001\"\u0001"+
		"\"\u0005\"\u0220\b\"\n\"\f\"\u0223\t\"\u0001\"\u0003\"\u0226\b\"\u0001"+
		"#\u0001#\u0001#\u0001#\u0001#\u0001#\u0001#\u0001#\u0001#\u0003#\u0231"+
		"\b#\u0001$\u0001$\u0001$\u0005$\u0236\b$\n$\f$\u0239\t$\u0001$\u0003$"+
		"\u023c\b$\u0001%\u0001%\u0001%\u0001%\u0001%\u0001%\u0005%\u0244\b%\n"+
		"%\f%\u0247\t%\u0001%\u0000\u000202&\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468:<>@BDF"+
		"HJ\u0000\u0005\u0003\u0000BLNNPP\u0002\u0000-.==\u0001\u0000?@\u0001\u0000"+
		"-.\u0004\u0000/077MMOO\u0297\u0000P\u0001\u0000\u0000\u0000\u0002W\u0001"+
		"\u0000\u0000\u0000\u0004Y\u0001\u0000\u0000\u0000\u0006d\u0001\u0000\u0000"+
		"\u0000\bo\u0001\u0000\u0000\u0000\n\u0080\u0001\u0000\u0000\u0000\f\u0088"+
		"\u0001\u0000\u0000\u0000\u000e\u008d\u0001\u0000\u0000\u0000\u0010\u0094"+
		"\u0001\u0000\u0000\u0000\u0012\u00a3\u0001\u0000\u0000\u0000\u0014\u00b0"+
		"\u0001\u0000\u0000\u0000\u0016\u00b2\u0001\u0000\u0000\u0000\u0018\u00bb"+
		"\u0001\u0000\u0000\u0000\u001a\u00bd\u0001\u0000\u0000\u0000\u001c\u00c5"+
		"\u0001\u0000\u0000\u0000\u001e\u00cd\u0001\u0000\u0000\u0000 \u00d2\u0001"+
		"\u0000\u0000\u0000\"\u00d4\u0001\u0000\u0000\u0000$\u0100\u0001\u0000"+
		"\u0000\u0000&\u0102\u0001\u0000\u0000\u0000(\u0125\u0001\u0000\u0000\u0000"+
		"*\u0127\u0001\u0000\u0000\u0000,\u0133\u0001\u0000\u0000\u0000.\u0142"+
		"\u0001\u0000\u0000\u00000\u014c\u0001\u0000\u0000\u00002\u0165\u0001\u0000"+
		"\u0000\u00004\u0194\u0001\u0000\u0000\u00006\u01db\u0001\u0000\u0000\u0000"+
		"8\u01e8\u0001\u0000\u0000\u0000:\u01ea\u0001\u0000\u0000\u0000<\u0200"+
		"\u0001\u0000\u0000\u0000>\u0202\u0001\u0000\u0000\u0000@\u0206\u0001\u0000"+
		"\u0000\u0000B\u0211\u0001\u0000\u0000\u0000D\u021c\u0001\u0000\u0000\u0000"+
		"F\u0230\u0001\u0000\u0000\u0000H\u0232\u0001\u0000\u0000\u0000J\u023d"+
		"\u0001\u0000\u0000\u0000LO\u0005Z\u0000\u0000MO\u0003\u0002\u0001\u0000"+
		"NL\u0001\u0000\u0000\u0000NM\u0001\u0000\u0000\u0000OR\u0001\u0000\u0000"+
		"\u0000PN\u0001\u0000\u0000\u0000PQ\u0001\u0000\u0000\u0000QS\u0001\u0000"+
		"\u0000\u0000RP\u0001\u0000\u0000\u0000ST\u0005\u0000\u0000\u0001T\u0001"+
		"\u0001\u0000\u0000\u0000UX\u0003\u0010\b\u0000VX\u0003\b\u0004\u0000W"+
		"U\u0001\u0000\u0000\u0000WV\u0001\u0000\u0000\u0000X\u0003\u0001\u0000"+
		"\u0000\u0000YZ\u0005O\u0000\u0000Z`\u0003\u0006\u0003\u0000[]\u0005$\u0000"+
		"\u0000\\^\u0003D\"\u0000]\\\u0001\u0000\u0000\u0000]^\u0001\u0000\u0000"+
		"\u0000^_\u0001\u0000\u0000\u0000_a\u0005\'\u0000\u0000`[\u0001\u0000\u0000"+
		"\u0000`a\u0001\u0000\u0000\u0000ab\u0001\u0000\u0000\u0000bc\u0005Z\u0000"+
		"\u0000c\u0005\u0001\u0000\u0000\u0000di\u0005X\u0000\u0000ef\u00056\u0000"+
		"\u0000fh\u0005X\u0000\u0000ge\u0001\u0000\u0000\u0000hk\u0001\u0000\u0000"+
		"\u0000ig\u0001\u0000\u0000\u0000ij\u0001\u0000\u0000\u0000j\u0007\u0001"+
		"\u0000\u0000\u0000ki\u0001\u0000\u0000\u0000ln\u0003\u0004\u0002\u0000"+
		"ml\u0001\u0000\u0000\u0000nq\u0001\u0000\u0000\u0000om\u0001\u0000\u0000"+
		"\u0000op\u0001\u0000\u0000\u0000pr\u0001\u0000\u0000\u0000qo\u0001\u0000"+
		"\u0000\u0000rs\u0005\u0016\u0000\u0000st\u0005X\u0000\u0000tv\u0005$\u0000"+
		"\u0000uw\u0003\n\u0005\u0000vu\u0001\u0000\u0000\u0000vw\u0001\u0000\u0000"+
		"\u0000wx\u0001\u0000\u0000\u0000x{\u0005\'\u0000\u0000yz\u0005Q\u0000"+
		"\u0000z|\u0003\u0018\f\u0000{y\u0001\u0000\u0000\u0000{|\u0001\u0000\u0000"+
		"\u0000|}\u0001\u0000\u0000\u0000}~\u0005*\u0000\u0000~\u007f\u0003,\u0016"+
		"\u0000\u007f\t\u0001\u0000\u0000\u0000\u0080\u0085\u0003\f\u0006\u0000"+
		"\u0081\u0082\u0005+\u0000\u0000\u0082\u0084\u0003\f\u0006\u0000\u0083"+
		"\u0081\u0001\u0000\u0000\u0000\u0084\u0087\u0001\u0000\u0000\u0000\u0085"+
		"\u0083\u0001\u0000\u0000\u0000\u0085\u0086\u0001\u0000\u0000\u0000\u0086"+
		"\u000b\u0001\u0000\u0000\u0000\u0087\u0085\u0001\u0000\u0000\u0000\u0088"+
		"\u008b\u0003\u000e\u0007\u0000\u0089\u008a\u00055\u0000\u0000\u008a\u008c"+
		"\u00030\u0018\u0000\u008b\u0089\u0001\u0000\u0000\u0000\u008b\u008c\u0001"+
		"\u0000\u0000\u0000\u008c\r\u0001\u0000\u0000\u0000\u008d\u008e\u0005X"+
		"\u0000\u0000\u008e\u008f\u0005*\u0000\u0000\u008f\u0090\u0003\u0018\f"+
		"\u0000\u0090\u000f\u0001\u0000\u0000\u0000\u0091\u0093\u0003\u0004\u0002"+
		"\u0000\u0092\u0091\u0001\u0000\u0000\u0000\u0093\u0096\u0001\u0000\u0000"+
		"\u0000\u0094\u0092\u0001\u0000\u0000\u0000\u0094\u0095\u0001\u0000\u0000"+
		"\u0000\u0095\u0097\u0001\u0000\u0000\u0000\u0096\u0094\u0001\u0000\u0000"+
		"\u0000\u0097\u0098\u0005\f\u0000\u0000\u0098\u009e\u0005X\u0000\u0000"+
		"\u0099\u009b\u0005$\u0000\u0000\u009a\u009c\u0003\u001a\r\u0000\u009b"+
		"\u009a\u0001\u0000\u0000\u0000\u009b\u009c\u0001\u0000\u0000\u0000\u009c"+
		"\u009d\u0001\u0000\u0000\u0000\u009d\u009f\u0005\'\u0000\u0000\u009e\u0099"+
		"\u0001\u0000\u0000\u0000\u009e\u009f\u0001\u0000\u0000\u0000\u009f\u00a0"+
		"\u0001\u0000\u0000\u0000\u00a0\u00a1\u0005*\u0000\u0000\u00a1\u00a2\u0003"+
		"\u0012\t\u0000\u00a2\u0011\u0001\u0000\u0000\u0000\u00a3\u00a4\u0005["+
		"\u0000\u0000\u00a4\u00a5\u0003\u0014\n\u0000\u00a5\u00a6\u0005\\\u0000"+
		"\u0000\u00a6\u0013\u0001\u0000\u0000\u0000\u00a7\u00a8\u0003\u0016\u000b"+
		"\u0000\u00a8\u00a9\u0005Z\u0000\u0000\u00a9\u00ab\u0001\u0000\u0000\u0000"+
		"\u00aa\u00a7\u0001\u0000\u0000\u0000\u00ab\u00ac\u0001\u0000\u0000\u0000"+
		"\u00ac\u00aa\u0001\u0000\u0000\u0000\u00ac\u00ad\u0001\u0000\u0000\u0000"+
		"\u00ad\u00b1\u0001\u0000\u0000\u0000\u00ae\u00af\u0005\u0005\u0000\u0000"+
		"\u00af\u00b1\u0005Z\u0000\u0000\u00b0\u00aa\u0001\u0000\u0000\u0000\u00b0"+
		"\u00ae\u0001\u0000\u0000\u0000\u00b1\u0015\u0001\u0000\u0000\u0000\u00b2"+
		"\u00b5\u0005X\u0000\u0000\u00b3\u00b4\u0005*\u0000\u0000\u00b4\u00b6\u0003"+
		"\u0018\f\u0000\u00b5\u00b3\u0001\u0000\u0000\u0000\u00b5\u00b6\u0001\u0000"+
		"\u0000\u0000\u00b6\u00b9\u0001\u0000\u0000\u0000\u00b7\u00b8\u00055\u0000"+
		"\u0000\u00b8\u00ba\u00030\u0018\u0000\u00b9\u00b7\u0001\u0000\u0000\u0000"+
		"\u00b9\u00ba\u0001\u0000\u0000\u0000\u00ba\u0017\u0001\u0000\u0000\u0000"+
		"\u00bb\u00bc\u00032\u0019\u0000\u00bc\u0019\u0001\u0000\u0000\u0000\u00bd"+
		"\u00c2\u0003\u0018\f\u0000\u00be\u00bf\u0005+\u0000\u0000\u00bf\u00c1"+
		"\u0003\u0018\f\u0000\u00c0\u00be\u0001\u0000\u0000\u0000\u00c1\u00c4\u0001"+
		"\u0000\u0000\u0000\u00c2\u00c0\u0001\u0000\u0000\u0000\u00c2\u00c3\u0001"+
		"\u0000\u0000\u0000\u00c3\u001b\u0001\u0000\u0000\u0000\u00c4\u00c2\u0001"+
		"\u0000\u0000\u0000\u00c5\u00ca\u0003\u001e\u000f\u0000\u00c6\u00c7\u0005"+
		"+\u0000\u0000\u00c7\u00c9\u0003\u001e\u000f\u0000\u00c8\u00c6\u0001\u0000"+
		"\u0000\u0000\u00c9\u00cc\u0001\u0000\u0000\u0000\u00ca\u00c8\u0001\u0000"+
		"\u0000\u0000\u00ca\u00cb\u0001\u0000\u0000\u0000\u00cb\u001d\u0001\u0000"+
		"\u0000\u0000\u00cc\u00ca\u0001\u0000\u0000\u0000\u00cd\u00ce\u0005X\u0000"+
		"\u0000\u00ce\u001f\u0001\u0000\u0000\u0000\u00cf\u00d3\u0003\"\u0011\u0000"+
		"\u00d0\u00d3\u0003(\u0014\u0000\u00d1\u00d3\u0003*\u0015\u0000\u00d2\u00cf"+
		"\u0001\u0000\u0000\u0000\u00d2\u00d0\u0001\u0000\u0000\u0000\u00d2\u00d1"+
		"\u0001\u0000\u0000\u0000\u00d3!\u0001\u0000\u0000\u0000\u00d4\u00d9\u0003"+
		"$\u0012\u0000\u00d5\u00d6\u0005,\u0000\u0000\u00d6\u00d8\u0003$\u0012"+
		"\u0000\u00d7\u00d5\u0001\u0000\u0000\u0000\u00d8\u00db\u0001\u0000\u0000"+
		"\u0000\u00d9\u00d7\u0001\u0000\u0000\u0000\u00d9\u00da\u0001\u0000\u0000"+
		"\u0000\u00da\u00dd\u0001\u0000\u0000\u0000\u00db\u00d9\u0001\u0000\u0000"+
		"\u0000\u00dc\u00de\u0005,\u0000\u0000\u00dd\u00dc\u0001\u0000\u0000\u0000"+
		"\u00dd\u00de\u0001\u0000\u0000\u0000\u00de\u00df\u0001\u0000\u0000\u0000"+
		"\u00df\u00e0\u0005Z\u0000\u0000\u00e0#\u0001\u0000\u0000\u0000\u00e1\u0101"+
		"\u0003B!\u0000\u00e2\u00e3\u0003B!\u0000\u00e3\u00e4\u00055\u0000\u0000"+
		"\u00e4\u00e5\u0003B!\u0000\u00e5\u0101\u0001\u0000\u0000\u0000\u00e6\u00e7"+
		"\u0003B!\u0000\u00e7\u00e8\u0005*\u0000\u0000\u00e8\u00eb\u0003\u0018"+
		"\f\u0000\u00e9\u00ea\u00055\u0000\u0000\u00ea\u00ec\u0003B!\u0000\u00eb"+
		"\u00e9\u0001\u0000\u0000\u0000\u00eb\u00ec\u0001\u0000\u0000\u0000\u00ec"+
		"\u0101\u0001\u0000\u0000\u0000\u00ed\u00ee\u0003B!\u0000\u00ee\u00ef\u0003"+
		"&\u0013\u0000\u00ef\u00f0\u0003B!\u0000\u00f0\u0101\u0001\u0000\u0000"+
		"\u0000\u00f1\u00f2\u0005\u001b\u0000\u0000\u00f2\u0101\u0003@ \u0000\u00f3"+
		"\u0101\u0005\u0005\u0000\u0000\u00f4\u0101\u0005\u0007\u0000\u0000\u00f5"+
		"\u0101\u0005\u0011\u0000\u0000\u00f6\u00f8\u0005\u000f\u0000\u0000\u00f7"+
		"\u00f9\u0003B!\u0000\u00f8\u00f7\u0001\u0000\u0000\u0000\u00f8\u00f9\u0001"+
		"\u0000\u0000\u0000\u00f9\u0101\u0001\u0000\u0000\u0000\u00fa\u00fb\u0005"+
		"\u001a\u0000\u0000\u00fb\u00fe\u00030\u0018\u0000\u00fc\u00fd\u0005+\u0000"+
		"\u0000\u00fd\u00ff\u00030\u0018\u0000\u00fe\u00fc\u0001\u0000\u0000\u0000"+
		"\u00fe\u00ff\u0001\u0000\u0000\u0000\u00ff\u0101\u0001\u0000\u0000\u0000"+
		"\u0100\u00e1\u0001\u0000\u0000\u0000\u0100\u00e2\u0001\u0000\u0000\u0000"+
		"\u0100\u00e6\u0001\u0000\u0000\u0000\u0100\u00ed\u0001\u0000\u0000\u0000"+
		"\u0100\u00f1\u0001\u0000\u0000\u0000\u0100\u00f3\u0001\u0000\u0000\u0000"+
		"\u0100\u00f4\u0001\u0000\u0000\u0000\u0100\u00f5\u0001\u0000\u0000\u0000"+
		"\u0100\u00f6\u0001\u0000\u0000\u0000\u0100\u00fa\u0001\u0000\u0000\u0000"+
		"\u0101%\u0001\u0000\u0000\u0000\u0102\u0103\u0007\u0000\u0000\u0000\u0103"+
		"\'\u0001\u0000\u0000\u0000\u0104\u0105\u0005!\u0000\u0000\u0105\u0106"+
		"\u00030\u0018\u0000\u0106\u0107\u0005*\u0000\u0000\u0107\u010f\u0003,"+
		"\u0016\u0000\u0108\u0109\u0005 \u0000\u0000\u0109\u010a\u00030\u0018\u0000"+
		"\u010a\u010b\u0005*\u0000\u0000\u010b\u010c\u0003,\u0016\u0000\u010c\u010e"+
		"\u0001\u0000\u0000\u0000\u010d\u0108\u0001\u0000\u0000\u0000\u010e\u0111"+
		"\u0001\u0000\u0000\u0000\u010f\u010d\u0001\u0000\u0000\u0000\u010f\u0110"+
		"\u0001\u0000\u0000\u0000\u0110\u0115\u0001\u0000\u0000\u0000\u0111\u010f"+
		"\u0001\u0000\u0000\u0000\u0112\u0113\u0005\u0003\u0000\u0000\u0113\u0114"+
		"\u0005*\u0000\u0000\u0114\u0116\u0003,\u0016\u0000\u0115\u0112\u0001\u0000"+
		"\u0000\u0000\u0115\u0116\u0001\u0000\u0000\u0000\u0116\u0126\u0001\u0000"+
		"\u0000\u0000\u0117\u0118\u0005\u0019\u0000\u0000\u0118\u0119\u00032\u0019"+
		"\u0000\u0119\u011a\u0005*\u0000\u0000\u011a\u011b\u0003,\u0016\u0000\u011b"+
		"\u0126\u0001\u0000\u0000\u0000\u011c\u011d\u0005\u0012\u0000\u0000\u011d"+
		"\u011e\u0003@ \u0000\u011e\u011f\u0005\t\u0000\u0000\u011f\u0120\u0003"+
		"B!\u0000\u0120\u0121\u0005*\u0000\u0000\u0121\u0122\u0003,\u0016\u0000"+
		"\u0122\u0126\u0001\u0000\u0000\u0000\u0123\u0126\u0003\b\u0004\u0000\u0124"+
		"\u0126\u0003\u0010\b\u0000\u0125\u0104\u0001\u0000\u0000\u0000\u0125\u0117"+
		"\u0001\u0000\u0000\u0000\u0125\u011c\u0001\u0000\u0000\u0000\u0125\u0123"+
		"\u0001\u0000\u0000\u0000\u0125\u0124\u0001\u0000\u0000\u0000\u0126)\u0001"+
		"\u0000\u0000\u0000\u0127\u0128\u0005V\u0000\u0000\u0128\u0129\u0005Z\u0000"+
		"\u0000\u0129+\u0001\u0000\u0000\u0000\u012a\u0134\u0003\"\u0011\u0000"+
		"\u012b\u012d\u0005[\u0000\u0000\u012c\u012e\u0003 \u0010\u0000\u012d\u012c"+
		"\u0001\u0000\u0000\u0000\u012e\u012f\u0001\u0000\u0000\u0000\u012f\u012d"+
		"\u0001\u0000\u0000\u0000\u012f\u0130\u0001\u0000\u0000\u0000\u0130\u0131"+
		"\u0001\u0000\u0000\u0000\u0131\u0132\u0005\\\u0000\u0000\u0132\u0134\u0001"+
		"\u0000\u0000\u0000\u0133\u012a\u0001\u0000\u0000\u0000\u0133\u012b\u0001"+
		"\u0000\u0000\u0000\u0134-\u0001\u0000\u0000\u0000\u0135\u0143\u00053\u0000"+
		"\u0000\u0136\u0143\u00054\u0000\u0000\u0137\u0143\u00058\u0000\u0000\u0138"+
		"\u0143\u0005<\u0000\u0000\u0139\u0143\u0005;\u0000\u0000\u013a\u0143\u0005"+
		"9\u0000\u0000\u013b\u0143\u0005:\u0000\u0000\u013c\u0143\u0005\t\u0000"+
		"\u0000\u013d\u013e\u0005\u001d\u0000\u0000\u013e\u0143\u0005\t\u0000\u0000"+
		"\u013f\u0143\u0005\u000e\u0000\u0000\u0140\u0141\u0005\u000e\u0000\u0000"+
		"\u0141\u0143\u0005\u001d\u0000\u0000\u0142\u0135\u0001\u0000\u0000\u0000"+
		"\u0142\u0136\u0001\u0000\u0000\u0000\u0142\u0137\u0001\u0000\u0000\u0000"+
		"\u0142\u0138\u0001\u0000\u0000\u0000\u0142\u0139\u0001\u0000\u0000\u0000"+
		"\u0142\u013a\u0001\u0000\u0000\u0000\u0142\u013b\u0001\u0000\u0000\u0000"+
		"\u0142\u013c\u0001\u0000\u0000\u0000\u0142\u013d\u0001\u0000\u0000\u0000"+
		"\u0142\u013f\u0001\u0000\u0000\u0000\u0142\u0140\u0001\u0000\u0000\u0000"+
		"\u0143/\u0001\u0000\u0000\u0000\u0144\u0145\u0006\u0018\uffff\uffff\u0000"+
		"\u0145\u0147\u0005\u0013\u0000\u0000\u0146\u0148\u0003\u001c\u000e\u0000"+
		"\u0147\u0146\u0001\u0000\u0000\u0000\u0147\u0148\u0001\u0000\u0000\u0000"+
		"\u0148\u0149\u0001\u0000\u0000\u0000\u0149\u014a\u0005*\u0000\u0000\u014a"+
		"\u014d\u00030\u0018\u0002\u014b\u014d\u00032\u0019\u0000\u014c\u0144\u0001"+
		"\u0000\u0000\u0000\u014c\u014b\u0001\u0000\u0000\u0000\u014d\u0156\u0001"+
		"\u0000\u0000\u0000\u014e\u014f\n\u0003\u0000\u0000\u014f\u0150\u0005!"+
		"\u0000\u0000\u0150\u0151\u00030\u0018\u0000\u0151\u0152\u0005\u0003\u0000"+
		"\u0000\u0152\u0153\u00030\u0018\u0004\u0153\u0155\u0001\u0000\u0000\u0000"+
		"\u0154\u014e\u0001\u0000\u0000\u0000\u0155\u0158\u0001\u0000\u0000\u0000"+
		"\u0156\u0154\u0001\u0000\u0000\u0000\u0156\u0157\u0001\u0000\u0000\u0000"+
		"\u01571\u0001\u0000\u0000\u0000\u0158\u0156\u0001\u0000\u0000\u0000\u0159"+
		"\u015a\u0006\u0019\uffff\uffff\u0000\u015a\u015b\u0005\u001d\u0000\u0000"+
		"\u015b\u0166\u00032\u0019\f\u015c\u015d\u0005/\u0000\u0000\u015d\u0166"+
		"\u00032\u0019\n\u015e\u015f\u0007\u0001\u0000\u0000\u015f\u0166\u0003"+
		"2\u0019\u0003\u0160\u0161\u00034\u001a\u0000\u0161\u0162\u0005A\u0000"+
		"\u0000\u0162\u0163\u00032\u0019\u0002\u0163\u0166\u0001\u0000\u0000\u0000"+
		"\u0164\u0166\u00034\u001a\u0000\u0165\u0159\u0001\u0000\u0000\u0000\u0165"+
		"\u015c\u0001\u0000\u0000\u0000\u0165\u015e\u0001\u0000\u0000\u0000\u0165"+
		"\u0160\u0001\u0000\u0000\u0000\u0165\u0164\u0001\u0000\u0000\u0000\u0166"+
		"\u0191\u0001\u0000\u0000\u0000\u0167\u0168\n\t\u0000\u0000\u0168\u0169"+
		"\u00051\u0000\u0000\u0169\u0190\u00032\u0019\n\u016a\u016b\n\b\u0000\u0000"+
		"\u016b\u016c\u0005>\u0000\u0000\u016c\u0190\u00032\u0019\t\u016d\u016e"+
		"\n\u0007\u0000\u0000\u016e\u016f\u00052\u0000\u0000\u016f\u0190\u0003"+
		"2\u0019\b\u0170\u0171\n\u0006\u0000\u0000\u0171\u0172\u0007\u0002\u0000"+
		"\u0000\u0172\u0190\u00032\u0019\u0007\u0173\u0174\n\u0005\u0000\u0000"+
		"\u0174\u0175\u0007\u0003\u0000\u0000\u0175\u0190\u00032\u0019\u0006\u0176"+
		"\u0177\n\u0004\u0000\u0000\u0177\u0178\u0007\u0004\u0000\u0000\u0178\u0190"+
		"\u00032\u0019\u0005\u0179\u017c\n\u000e\u0000\u0000\u017a\u017b\u0005"+
		"\"\u0000\u0000\u017b\u017d\u00032\u0019\u0000\u017c\u017a\u0001\u0000"+
		"\u0000\u0000\u017d\u017e\u0001\u0000\u0000\u0000\u017e\u017c\u0001\u0000"+
		"\u0000\u0000\u017e\u017f\u0001\u0000\u0000\u0000\u017f\u0190\u0001\u0000"+
		"\u0000\u0000\u0180\u0183\n\r\u0000\u0000\u0181\u0182\u0005\u0010\u0000"+
		"\u0000\u0182\u0184\u00032\u0019\u0000\u0183\u0181\u0001\u0000\u0000\u0000"+
		"\u0184\u0185\u0001\u0000\u0000\u0000\u0185\u0183\u0001\u0000\u0000\u0000"+
		"\u0185\u0186\u0001\u0000\u0000\u0000\u0186\u0190\u0001\u0000\u0000\u0000"+
		"\u0187\u018b\n\u000b\u0000\u0000\u0188\u0189\u0003.\u0017\u0000\u0189"+
		"\u018a\u00032\u0019\u0000\u018a\u018c\u0001\u0000\u0000\u0000\u018b\u0188"+
		"\u0001\u0000\u0000\u0000\u018c\u018d\u0001\u0000\u0000\u0000\u018d\u018b"+
		"\u0001\u0000\u0000\u0000\u018d\u018e\u0001\u0000\u0000\u0000\u018e\u0190"+
		"\u0001\u0000\u0000\u0000\u018f\u0167\u0001\u0000\u0000\u0000\u018f\u016a"+
		"\u0001\u0000\u0000\u0000\u018f\u016d\u0001\u0000\u0000\u0000\u018f\u0170"+
		"\u0001\u0000\u0000\u0000\u018f\u0173\u0001\u0000\u0000\u0000\u018f\u0176"+
		"\u0001\u0000\u0000\u0000\u018f\u0179\u0001\u0000\u0000\u0000\u018f\u0180"+
		"\u0001\u0000\u0000\u0000\u018f\u0187\u0001\u0000\u0000\u0000\u0190\u0193"+
		"\u0001\u0000\u0000\u0000\u0191\u018f\u0001\u0000\u0000\u0000\u0191\u0192"+
		"\u0001\u0000\u0000\u0000\u01923\u0001\u0000\u0000\u0000\u0193\u0191\u0001"+
		"\u0000\u0000\u0000\u0194\u0198\u00036\u001b\u0000\u0195\u0197\u00038\u001c"+
		"\u0000\u0196\u0195\u0001\u0000\u0000\u0000\u0197\u019a\u0001\u0000\u0000"+
		"\u0000\u0198\u0196\u0001\u0000\u0000\u0000\u0198\u0199\u0001\u0000\u0000"+
		"\u0000\u01995\u0001\u0000\u0000\u0000\u019a\u0198\u0001\u0000\u0000\u0000"+
		"\u019b\u019c\u0005$\u0000\u0000\u019c\u019d\u00030\u0018\u0000\u019d\u019e"+
		"\u0005\'\u0000\u0000\u019e\u01dc\u0001\u0000\u0000\u0000\u019f\u01a1\u0005"+
		"$\u0000\u0000\u01a0\u01a2\u0003B!\u0000\u01a1\u01a0\u0001\u0000\u0000"+
		"\u0000\u01a1\u01a2\u0001\u0000\u0000\u0000\u01a2\u01a3\u0001\u0000\u0000"+
		"\u0000\u01a3\u01dc\u0005\'\u0000\u0000\u01a4\u01a6\u0005%\u0000\u0000"+
		"\u01a5\u01a7\u0003B!\u0000\u01a6\u01a5\u0001\u0000\u0000\u0000\u01a6\u01a7"+
		"\u0001\u0000\u0000\u0000\u01a7\u01a8\u0001\u0000\u0000\u0000\u01a8\u01dc"+
		"\u0005(\u0000\u0000\u01a9\u01aa\u0005%\u0000\u0000\u01aa\u01ab\u00032"+
		"\u0019\u0000\u01ab\u01ac\u0003J%\u0000\u01ac\u01ad\u0005(\u0000\u0000"+
		"\u01ad\u01dc\u0001\u0000\u0000\u0000\u01ae\u01bc\u0005&\u0000\u0000\u01af"+
		"\u01b0\u00030\u0018\u0000\u01b0\u01b1\u0005*\u0000\u0000\u01b1\u01b7\u0003"+
		"0\u0018\u0000\u01b2\u01b3\u0005+\u0000\u0000\u01b3\u01b4\u00030\u0018"+
		"\u0000\u01b4\u01b5\u0005*\u0000\u0000\u01b5\u01b6\u00030\u0018\u0000\u01b6"+
		"\u01b8\u0001\u0000\u0000\u0000\u01b7\u01b2\u0001\u0000\u0000\u0000\u01b7"+
		"\u01b8\u0001\u0000\u0000\u0000\u01b8\u01ba\u0001\u0000\u0000\u0000\u01b9"+
		"\u01bb\u0005+\u0000\u0000\u01ba\u01b9\u0001\u0000\u0000\u0000\u01ba\u01bb"+
		"\u0001\u0000\u0000\u0000\u01bb\u01bd\u0001\u0000\u0000\u0000\u01bc\u01af"+
		"\u0001\u0000\u0000\u0000\u01bc\u01bd\u0001\u0000\u0000\u0000\u01bd\u01be"+
		"\u0001\u0000\u0000\u0000\u01be\u01dc\u0005)\u0000\u0000\u01bf\u01c0\u0005"+
		"&\u0000\u0000\u01c0\u01c1\u00032\u0019\u0000\u01c1\u01c2\u0005*\u0000"+
		"\u0000\u01c2\u01c3\u00032\u0019\u0000\u01c3\u01c4\u0003J%\u0000\u01c4"+
		"\u01c5\u0005)\u0000\u0000\u01c5\u01dc\u0001\u0000\u0000\u0000\u01c6\u01c8"+
		"\u0005&\u0000\u0000\u01c7\u01c9\u0003B!\u0000\u01c8\u01c7\u0001\u0000"+
		"\u0000\u0000\u01c8\u01c9\u0001\u0000\u0000\u0000\u01c9\u01ca\u0001\u0000"+
		"\u0000\u0000\u01ca\u01dc\u0005)\u0000\u0000\u01cb\u01cc\u0005&\u0000\u0000"+
		"\u01cc\u01cd\u00032\u0019\u0000\u01cd\u01ce\u0003J%\u0000\u01ce\u01cf"+
		"\u0005)\u0000\u0000\u01cf\u01dc\u0001\u0000\u0000\u0000\u01d0\u01dc\u0005"+
		"X\u0000\u0000\u01d1\u01dc\u0005Y\u0000\u0000\u01d2\u01d4\u0005T\u0000"+
		"\u0000\u01d3\u01d2\u0001\u0000\u0000\u0000\u01d4\u01d5\u0001\u0000\u0000"+
		"\u0000\u01d5\u01d3\u0001\u0000\u0000\u0000\u01d5\u01d6\u0001\u0000\u0000"+
		"\u0000\u01d6\u01dc\u0001\u0000\u0000\u0000\u01d7\u01dc\u0005R\u0000\u0000"+
		"\u01d8\u01dc\u0005\u0006\u0000\u0000\u01d9\u01dc\u0005\u000b\u0000\u0000"+
		"\u01da\u01dc\u0005\u0001\u0000\u0000\u01db\u019b\u0001\u0000\u0000\u0000"+
		"\u01db\u019f\u0001\u0000\u0000\u0000\u01db\u01a4\u0001\u0000\u0000\u0000"+
		"\u01db\u01a9\u0001\u0000\u0000\u0000\u01db\u01ae\u0001\u0000\u0000\u0000"+
		"\u01db\u01bf\u0001\u0000\u0000\u0000\u01db\u01c6\u0001\u0000\u0000\u0000"+
		"\u01db\u01cb\u0001\u0000\u0000\u0000\u01db\u01d0\u0001\u0000\u0000\u0000"+
		"\u01db\u01d1\u0001\u0000\u0000\u0000\u01db\u01d3\u0001\u0000\u0000\u0000"+
		"\u01db\u01d7\u0001\u0000\u0000\u0000\u01db\u01d8\u0001\u0000\u0000\u0000"+
		"\u01db\u01d9\u0001\u0000\u0000\u0000\u01db\u01da\u0001\u0000\u0000\u0000"+
		"\u01dc7\u0001\u0000\u0000\u0000\u01dd\u01df\u0005$\u0000\u0000\u01de\u01e0"+
		"\u0003D\"\u0000\u01df\u01de\u0001\u0000\u0000\u0000\u01df\u01e0\u0001"+
		"\u0000\u0000\u0000\u01e0\u01e1\u0001\u0000\u0000\u0000\u01e1\u01e9\u0005"+
		"\'\u0000\u0000\u01e2\u01e3\u0005%\u0000\u0000\u01e3\u01e4\u0003:\u001d"+
		"\u0000\u01e4\u01e5\u0005(\u0000\u0000\u01e5\u01e9\u0001\u0000\u0000\u0000"+
		"\u01e6\u01e7\u00056\u0000\u0000\u01e7\u01e9\u0005X\u0000\u0000\u01e8\u01dd"+
		"\u0001\u0000\u0000\u0000\u01e8\u01e2\u0001\u0000\u0000\u0000\u01e8\u01e6"+
		"\u0001\u0000\u0000\u0000\u01e99\u0001\u0000\u0000\u0000\u01ea\u01ef\u0003"+
		"<\u001e\u0000\u01eb\u01ec\u0005+\u0000\u0000\u01ec\u01ee\u0003<\u001e"+
		"\u0000\u01ed\u01eb\u0001\u0000\u0000\u0000\u01ee\u01f1\u0001\u0000\u0000"+
		"\u0000\u01ef\u01ed\u0001\u0000\u0000\u0000\u01ef\u01f0\u0001\u0000\u0000"+
		"\u0000\u01f0\u01f3\u0001\u0000\u0000\u0000\u01f1\u01ef\u0001\u0000\u0000"+
		"\u0000\u01f2\u01f4\u0005+\u0000\u0000\u01f3\u01f2\u0001\u0000\u0000\u0000"+
		"\u01f3\u01f4\u0001\u0000\u0000\u0000\u01f4;\u0001\u0000\u0000\u0000\u01f5"+
		"\u0201\u00032\u0019\u0000\u01f6\u01f8\u00032\u0019\u0000\u01f7\u01f6\u0001"+
		"\u0000\u0000\u0000\u01f7\u01f8\u0001\u0000\u0000\u0000\u01f8\u01f9\u0001"+
		"\u0000\u0000\u0000\u01f9\u01fb\u0005*\u0000\u0000\u01fa\u01fc\u00032\u0019"+
		"\u0000\u01fb\u01fa\u0001\u0000\u0000\u0000\u01fb\u01fc\u0001\u0000\u0000"+
		"\u0000\u01fc\u01fe\u0001\u0000\u0000\u0000\u01fd\u01ff\u0003>\u001f\u0000"+
		"\u01fe\u01fd\u0001\u0000\u0000\u0000\u01fe\u01ff\u0001\u0000\u0000\u0000"+
		"\u01ff\u0201\u0001\u0000\u0000\u0000\u0200\u01f5\u0001\u0000\u0000\u0000"+
		"\u0200\u01f7\u0001\u0000\u0000\u0000\u0201=\u0001\u0000\u0000\u0000\u0202"+
		"\u0204\u0005*\u0000\u0000\u0203\u0205\u00032\u0019\u0000\u0204\u0203\u0001"+
		"\u0000\u0000\u0000\u0204\u0205\u0001\u0000\u0000\u0000\u0205?\u0001\u0000"+
		"\u0000\u0000\u0206\u020b\u00032\u0019\u0000\u0207\u0208\u0005+\u0000\u0000"+
		"\u0208\u020a\u00032\u0019\u0000\u0209\u0207\u0001\u0000\u0000\u0000\u020a"+
		"\u020d\u0001\u0000\u0000\u0000\u020b\u0209\u0001\u0000\u0000\u0000\u020b"+
		"\u020c\u0001\u0000\u0000\u0000\u020c\u020f\u0001\u0000\u0000\u0000\u020d"+
		"\u020b\u0001\u0000\u0000\u0000\u020e\u0210\u0005+\u0000\u0000\u020f\u020e"+
		"\u0001\u0000\u0000\u0000\u020f\u0210\u0001\u0000\u0000\u0000\u0210A\u0001"+
		"\u0000\u0000\u0000\u0211\u0216\u00030\u0018\u0000\u0212\u0213\u0005+\u0000"+
		"\u0000\u0213\u0215\u00030\u0018\u0000\u0214\u0212\u0001\u0000\u0000\u0000"+
		"\u0215\u0218\u0001\u0000\u0000\u0000\u0216\u0214\u0001\u0000\u0000\u0000"+
		"\u0216\u0217\u0001\u0000\u0000\u0000\u0217\u021a\u0001\u0000\u0000\u0000"+
		"\u0218\u0216\u0001\u0000\u0000\u0000\u0219\u021b\u0005+\u0000\u0000\u021a"+
		"\u0219\u0001\u0000\u0000\u0000\u021a\u021b\u0001\u0000\u0000\u0000\u021b"+
		"C\u0001\u0000\u0000\u0000\u021c\u0221\u0003F#\u0000\u021d\u021e\u0005"+
		"+\u0000\u0000\u021e\u0220\u0003F#\u0000\u021f\u021d\u0001\u0000\u0000"+
		"\u0000\u0220\u0223\u0001\u0000\u0000\u0000\u0221\u021f\u0001\u0000\u0000"+
		"\u0000\u0221\u0222\u0001\u0000\u0000\u0000\u0222\u0225\u0001\u0000\u0000"+
		"\u0000\u0223\u0221\u0001\u0000\u0000\u0000\u0224\u0226\u0005+\u0000\u0000"+
		"\u0225\u0224\u0001\u0000\u0000\u0000\u0225\u0226\u0001\u0000\u0000\u0000"+
		"\u0226E\u0001\u0000\u0000\u0000\u0227\u0231\u00030\u0018\u0000\u0228\u0229"+
		"\u00032\u0019\u0000\u0229\u022a\u0003J%\u0000\u022a\u0231\u0001\u0000"+
		"\u0000\u0000\u022b\u022c\u0005X\u0000\u0000\u022c\u022d\u00055\u0000\u0000"+
		"\u022d\u0231\u00030\u0018\u0000\u022e\u022f\u0005/\u0000\u0000\u022f\u0231"+
		"\u00032\u0019\u0000\u0230\u0227\u0001\u0000\u0000\u0000\u0230\u0228\u0001"+
		"\u0000\u0000\u0000\u0230\u022b\u0001\u0000\u0000\u0000\u0230\u022e\u0001"+
		"\u0000\u0000\u0000\u0231G\u0001\u0000\u0000\u0000\u0232\u0237\u0005X\u0000"+
		"\u0000\u0233\u0234\u0005+\u0000\u0000\u0234\u0236\u0005X\u0000\u0000\u0235"+
		"\u0233\u0001\u0000\u0000\u0000\u0236\u0239\u0001\u0000\u0000\u0000\u0237"+
		"\u0235\u0001\u0000\u0000\u0000\u0237\u0238\u0001\u0000\u0000\u0000\u0238"+
		"\u023b\u0001\u0000\u0000\u0000\u0239\u0237\u0001\u0000\u0000\u0000\u023a"+
		"\u023c\u0005+\u0000\u0000\u023b\u023a\u0001\u0000\u0000\u0000\u023b\u023c"+
		"\u0001\u0000\u0000\u0000\u023cI\u0001\u0000\u0000\u0000\u023d\u023e\u0005"+
		"\u0012\u0000\u0000\u023e\u023f\u0003H$\u0000\u023f\u0240\u0005\t\u0000"+
		"\u0000\u0240\u0245\u00032\u0019\u0000\u0241\u0242\u0005!\u0000\u0000\u0242"+
		"\u0244\u00032\u0019\u0000\u0243\u0241\u0001\u0000\u0000\u0000\u0244\u0247"+
		"\u0001\u0000\u0000\u0000\u0245\u0243\u0001\u0000\u0000\u0000\u0245\u0246"+
		"\u0001\u0000\u0000\u0000\u0246K\u0001\u0000\u0000\u0000\u0247\u0245\u0001"+
		"\u0000\u0000\u0000FNPW]`iov{\u0085\u008b\u0094\u009b\u009e\u00ac\u00b0"+
		"\u00b5\u00b9\u00c2\u00ca\u00d2\u00d9\u00dd\u00eb\u00f8\u00fe\u0100\u010f"+
		"\u0115\u0125\u012f\u0133\u0142\u0147\u014c\u0156\u0165\u017e\u0185\u018d"+
		"\u018f\u0191\u0198\u01a1\u01a6\u01b7\u01ba\u01bc\u01c8\u01d5\u01db\u01df"+
		"\u01e8\u01ef\u01f3\u01f7\u01fb\u01fe\u0200\u0204\u020b\u020f\u0216\u021a"+
		"\u0221\u0225\u0230\u0237\u023b\u0245";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}