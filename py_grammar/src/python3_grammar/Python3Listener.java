// Generated from java-escape by ANTLR 4.11.1
package python3_grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Python3Parser}.
 */
public interface Python3Listener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link Python3Parser#file_input}.
	 * @param ctx the parse tree
	 */
	void enterFile_input(Python3Parser.File_inputContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#file_input}.
	 * @param ctx the parse tree
	 */
	void exitFile_input(Python3Parser.File_inputContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#top_level_stmt}.
	 * @param ctx the parse tree
	 */
	void enterTop_level_stmt(Python3Parser.Top_level_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#top_level_stmt}.
	 * @param ctx the parse tree
	 */
	void exitTop_level_stmt(Python3Parser.Top_level_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#decorator}.
	 * @param ctx the parse tree
	 */
	void enterDecorator(Python3Parser.DecoratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#decorator}.
	 * @param ctx the parse tree
	 */
	void exitDecorator(Python3Parser.DecoratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#dotted_name}.
	 * @param ctx the parse tree
	 */
	void enterDotted_name(Python3Parser.Dotted_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#dotted_name}.
	 * @param ctx the parse tree
	 */
	void exitDotted_name(Python3Parser.Dotted_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#func_def}.
	 * @param ctx the parse tree
	 */
	void enterFunc_def(Python3Parser.Func_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#func_def}.
	 * @param ctx the parse tree
	 */
	void exitFunc_def(Python3Parser.Func_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#typedargslist}.
	 * @param ctx the parse tree
	 */
	void enterTypedargslist(Python3Parser.TypedargslistContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#typedargslist}.
	 * @param ctx the parse tree
	 */
	void exitTypedargslist(Python3Parser.TypedargslistContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#tfparg}.
	 * @param ctx the parse tree
	 */
	void enterTfparg(Python3Parser.TfpargContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#tfparg}.
	 * @param ctx the parse tree
	 */
	void exitTfparg(Python3Parser.TfpargContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#tfpdef}.
	 * @param ctx the parse tree
	 */
	void enterTfpdef(Python3Parser.TfpdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#tfpdef}.
	 * @param ctx the parse tree
	 */
	void exitTfpdef(Python3Parser.TfpdefContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#class_def}.
	 * @param ctx the parse tree
	 */
	void enterClass_def(Python3Parser.Class_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#class_def}.
	 * @param ctx the parse tree
	 */
	void exitClass_def(Python3Parser.Class_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#class_suite}.
	 * @param ctx the parse tree
	 */
	void enterClass_suite(Python3Parser.Class_suiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#class_suite}.
	 * @param ctx the parse tree
	 */
	void exitClass_suite(Python3Parser.Class_suiteContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Fields}
	 * labeled alternative in {@link Python3Parser#class_fields}.
	 * @param ctx the parse tree
	 */
	void enterFields(Python3Parser.FieldsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Fields}
	 * labeled alternative in {@link Python3Parser#class_fields}.
	 * @param ctx the parse tree
	 */
	void exitFields(Python3Parser.FieldsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EmptyClass}
	 * labeled alternative in {@link Python3Parser#class_fields}.
	 * @param ctx the parse tree
	 */
	void enterEmptyClass(Python3Parser.EmptyClassContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EmptyClass}
	 * labeled alternative in {@link Python3Parser#class_fields}.
	 * @param ctx the parse tree
	 */
	void exitEmptyClass(Python3Parser.EmptyClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#field_def}.
	 * @param ctx the parse tree
	 */
	void enterField_def(Python3Parser.Field_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#field_def}.
	 * @param ctx the parse tree
	 */
	void exitField_def(Python3Parser.Field_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#type_expr}.
	 * @param ctx the parse tree
	 */
	void enterType_expr(Python3Parser.Type_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#type_expr}.
	 * @param ctx the parse tree
	 */
	void exitType_expr(Python3Parser.Type_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#type_expr_list}.
	 * @param ctx the parse tree
	 */
	void enterType_expr_list(Python3Parser.Type_expr_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#type_expr_list}.
	 * @param ctx the parse tree
	 */
	void exitType_expr_list(Python3Parser.Type_expr_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#varargslist}.
	 * @param ctx the parse tree
	 */
	void enterVarargslist(Python3Parser.VarargslistContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#varargslist}.
	 * @param ctx the parse tree
	 */
	void exitVarargslist(Python3Parser.VarargslistContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#vfpdef}.
	 * @param ctx the parse tree
	 */
	void enterVfpdef(Python3Parser.VfpdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#vfpdef}.
	 * @param ctx the parse tree
	 */
	void exitVfpdef(Python3Parser.VfpdefContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(Python3Parser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(Python3Parser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#simple_stmt}.
	 * @param ctx the parse tree
	 */
	void enterSimple_stmt(Python3Parser.Simple_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#simple_stmt}.
	 * @param ctx the parse tree
	 */
	void exitSimple_stmt(Python3Parser.Simple_stmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprStmt}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(Python3Parser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprStmt}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(Python3Parser.ExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Assign}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void enterAssign(Python3Parser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Assign}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void exitAssign(Python3Parser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AnnAssign}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void enterAnnAssign(Python3Parser.AnnAssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AnnAssign}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void exitAnnAssign(Python3Parser.AnnAssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AugAssign}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void enterAugAssign(Python3Parser.AugAssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AugAssign}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void exitAugAssign(Python3Parser.AugAssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Del}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void enterDel(Python3Parser.DelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Del}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void exitDel(Python3Parser.DelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Pass}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void enterPass(Python3Parser.PassContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Pass}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void exitPass(Python3Parser.PassContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Break}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void enterBreak(Python3Parser.BreakContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Break}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void exitBreak(Python3Parser.BreakContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Continue}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void enterContinue(Python3Parser.ContinueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Continue}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void exitContinue(Python3Parser.ContinueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Return}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void enterReturn(Python3Parser.ReturnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Return}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void exitReturn(Python3Parser.ReturnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Assert}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void enterAssert(Python3Parser.AssertContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Assert}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 */
	void exitAssert(Python3Parser.AssertContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#aug_assign}.
	 * @param ctx the parse tree
	 */
	void enterAug_assign(Python3Parser.Aug_assignContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#aug_assign}.
	 * @param ctx the parse tree
	 */
	void exitAug_assign(Python3Parser.Aug_assignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code If}
	 * labeled alternative in {@link Python3Parser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void enterIf(Python3Parser.IfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code If}
	 * labeled alternative in {@link Python3Parser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void exitIf(Python3Parser.IfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code While}
	 * labeled alternative in {@link Python3Parser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void enterWhile(Python3Parser.WhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code While}
	 * labeled alternative in {@link Python3Parser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void exitWhile(Python3Parser.WhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code For}
	 * labeled alternative in {@link Python3Parser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void enterFor(Python3Parser.ForContext ctx);
	/**
	 * Exit a parse tree produced by the {@code For}
	 * labeled alternative in {@link Python3Parser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void exitFor(Python3Parser.ForContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FuncDef}
	 * labeled alternative in {@link Python3Parser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void enterFuncDef(Python3Parser.FuncDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FuncDef}
	 * labeled alternative in {@link Python3Parser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void exitFuncDef(Python3Parser.FuncDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ClassDef}
	 * labeled alternative in {@link Python3Parser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void enterClassDef(Python3Parser.ClassDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ClassDef}
	 * labeled alternative in {@link Python3Parser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void exitClassDef(Python3Parser.ClassDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#annotation}.
	 * @param ctx the parse tree
	 */
	void enterAnnotation(Python3Parser.AnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#annotation}.
	 * @param ctx the parse tree
	 */
	void exitAnnotation(Python3Parser.AnnotationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SimpleStmt}
	 * labeled alternative in {@link Python3Parser#suite}.
	 * @param ctx the parse tree
	 */
	void enterSimpleStmt(Python3Parser.SimpleStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SimpleStmt}
	 * labeled alternative in {@link Python3Parser#suite}.
	 * @param ctx the parse tree
	 */
	void exitSimpleStmt(Python3Parser.SimpleStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Block}
	 * labeled alternative in {@link Python3Parser#suite}.
	 * @param ctx the parse tree
	 */
	void enterBlock(Python3Parser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Block}
	 * labeled alternative in {@link Python3Parser#suite}.
	 * @param ctx the parse tree
	 */
	void exitBlock(Python3Parser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#comp_op}.
	 * @param ctx the parse tree
	 */
	void enterComp_op(Python3Parser.Comp_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#comp_op}.
	 * @param ctx the parse tree
	 */
	void exitComp_op(Python3Parser.Comp_opContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfExp}
	 * labeled alternative in {@link Python3Parser#test}.
	 * @param ctx the parse tree
	 */
	void enterIfExp(Python3Parser.IfExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfExp}
	 * labeled alternative in {@link Python3Parser#test}.
	 * @param ctx the parse tree
	 */
	void exitIfExp(Python3Parser.IfExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TExpr}
	 * labeled alternative in {@link Python3Parser#test}.
	 * @param ctx the parse tree
	 */
	void enterTExpr(Python3Parser.TExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TExpr}
	 * labeled alternative in {@link Python3Parser#test}.
	 * @param ctx the parse tree
	 */
	void exitTExpr(Python3Parser.TExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Lambda}
	 * labeled alternative in {@link Python3Parser#test}.
	 * @param ctx the parse tree
	 */
	void enterLambda(Python3Parser.LambdaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Lambda}
	 * labeled alternative in {@link Python3Parser#test}.
	 * @param ctx the parse tree
	 */
	void exitLambda(Python3Parser.LambdaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(Python3Parser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(Python3Parser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BitOr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBitOr(Python3Parser.BitOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BitOr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBitOr(Python3Parser.BitOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StarExpr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterStarExpr(Python3Parser.StarExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StarExpr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitStarExpr(Python3Parser.StarExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Term}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterTerm(Python3Parser.TermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Term}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitTerm(Python3Parser.TermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AtomExpr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpr(Python3Parser.AtomExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AtomExpr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpr(Python3Parser.AtomExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(Python3Parser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(Python3Parser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PlusMinus}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPlusMinus(Python3Parser.PlusMinusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PlusMinus}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPlusMinus(Python3Parser.PlusMinusContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BitXor}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBitXor(Python3Parser.BitXorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BitXor}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBitXor(Python3Parser.BitXorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Factor}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFactor(Python3Parser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Factor}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFactor(Python3Parser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BitAnd}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBitAnd(Python3Parser.BitAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BitAnd}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBitAnd(Python3Parser.BitAndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Compare}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCompare(Python3Parser.CompareContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Compare}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCompare(Python3Parser.CompareContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(Python3Parser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(Python3Parser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ShiftExpr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterShiftExpr(Python3Parser.ShiftExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ShiftExpr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitShiftExpr(Python3Parser.ShiftExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Power}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPower(Python3Parser.PowerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Power}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPower(Python3Parser.PowerContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#atom_expr}.
	 * @param ctx the parse tree
	 */
	void enterAtom_expr(Python3Parser.Atom_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#atom_expr}.
	 * @param ctx the parse tree
	 */
	void exitAtom_expr(Python3Parser.Atom_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParensExpr}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void enterParensExpr(Python3Parser.ParensExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParensExpr}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void exitParensExpr(Python3Parser.ParensExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Tuple}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void enterTuple(Python3Parser.TupleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Tuple}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void exitTuple(Python3Parser.TupleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ListLit}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void enterListLit(Python3Parser.ListLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ListLit}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void exitListLit(Python3Parser.ListLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ListComp}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void enterListComp(Python3Parser.ListCompContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ListComp}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void exitListComp(Python3Parser.ListCompContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DictLit}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void enterDictLit(Python3Parser.DictLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DictLit}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void exitDictLit(Python3Parser.DictLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DictComp}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void enterDictComp(Python3Parser.DictCompContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DictComp}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void exitDictComp(Python3Parser.DictCompContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SetLit}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void enterSetLit(Python3Parser.SetLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SetLit}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void exitSetLit(Python3Parser.SetLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SetComp}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void enterSetComp(Python3Parser.SetCompContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SetComp}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void exitSetComp(Python3Parser.SetCompContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Name}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void enterName(Python3Parser.NameContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Name}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void exitName(Python3Parser.NameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Number}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void enterNumber(Python3Parser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Number}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void exitNumber(Python3Parser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Str}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void enterStr(Python3Parser.StrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Str}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void exitStr(Python3Parser.StrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Ellipsis}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void enterEllipsis(Python3Parser.EllipsisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Ellipsis}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void exitEllipsis(Python3Parser.EllipsisContext ctx);
	/**
	 * Enter a parse tree produced by the {@code None}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void enterNone(Python3Parser.NoneContext ctx);
	/**
	 * Exit a parse tree produced by the {@code None}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void exitNone(Python3Parser.NoneContext ctx);
	/**
	 * Enter a parse tree produced by the {@code True}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void enterTrue(Python3Parser.TrueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code True}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void exitTrue(Python3Parser.TrueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code False}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void enterFalse(Python3Parser.FalseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code False}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 */
	void exitFalse(Python3Parser.FalseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CallTrailer}
	 * labeled alternative in {@link Python3Parser#trailer}.
	 * @param ctx the parse tree
	 */
	void enterCallTrailer(Python3Parser.CallTrailerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CallTrailer}
	 * labeled alternative in {@link Python3Parser#trailer}.
	 * @param ctx the parse tree
	 */
	void exitCallTrailer(Python3Parser.CallTrailerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SubscrTrailer}
	 * labeled alternative in {@link Python3Parser#trailer}.
	 * @param ctx the parse tree
	 */
	void enterSubscrTrailer(Python3Parser.SubscrTrailerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SubscrTrailer}
	 * labeled alternative in {@link Python3Parser#trailer}.
	 * @param ctx the parse tree
	 */
	void exitSubscrTrailer(Python3Parser.SubscrTrailerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AttrTrailer}
	 * labeled alternative in {@link Python3Parser#trailer}.
	 * @param ctx the parse tree
	 */
	void enterAttrTrailer(Python3Parser.AttrTrailerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AttrTrailer}
	 * labeled alternative in {@link Python3Parser#trailer}.
	 * @param ctx the parse tree
	 */
	void exitAttrTrailer(Python3Parser.AttrTrailerContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#subscriptlist}.
	 * @param ctx the parse tree
	 */
	void enterSubscriptlist(Python3Parser.SubscriptlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#subscriptlist}.
	 * @param ctx the parse tree
	 */
	void exitSubscriptlist(Python3Parser.SubscriptlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#subscript}.
	 * @param ctx the parse tree
	 */
	void enterSubscript(Python3Parser.SubscriptContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#subscript}.
	 * @param ctx the parse tree
	 */
	void exitSubscript(Python3Parser.SubscriptContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#sliceop}.
	 * @param ctx the parse tree
	 */
	void enterSliceop(Python3Parser.SliceopContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#sliceop}.
	 * @param ctx the parse tree
	 */
	void exitSliceop(Python3Parser.SliceopContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#expr_list}.
	 * @param ctx the parse tree
	 */
	void enterExpr_list(Python3Parser.Expr_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#expr_list}.
	 * @param ctx the parse tree
	 */
	void exitExpr_list(Python3Parser.Expr_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#test_list}.
	 * @param ctx the parse tree
	 */
	void enterTest_list(Python3Parser.Test_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#test_list}.
	 * @param ctx the parse tree
	 */
	void exitTest_list(Python3Parser.Test_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#arglist}.
	 * @param ctx the parse tree
	 */
	void enterArglist(Python3Parser.ArglistContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#arglist}.
	 * @param ctx the parse tree
	 */
	void exitArglist(Python3Parser.ArglistContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TestArg}
	 * labeled alternative in {@link Python3Parser#argument}.
	 * @param ctx the parse tree
	 */
	void enterTestArg(Python3Parser.TestArgContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TestArg}
	 * labeled alternative in {@link Python3Parser#argument}.
	 * @param ctx the parse tree
	 */
	void exitTestArg(Python3Parser.TestArgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GeneratorArg}
	 * labeled alternative in {@link Python3Parser#argument}.
	 * @param ctx the parse tree
	 */
	void enterGeneratorArg(Python3Parser.GeneratorArgContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GeneratorArg}
	 * labeled alternative in {@link Python3Parser#argument}.
	 * @param ctx the parse tree
	 */
	void exitGeneratorArg(Python3Parser.GeneratorArgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NamedArg}
	 * labeled alternative in {@link Python3Parser#argument}.
	 * @param ctx the parse tree
	 */
	void enterNamedArg(Python3Parser.NamedArgContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NamedArg}
	 * labeled alternative in {@link Python3Parser#argument}.
	 * @param ctx the parse tree
	 */
	void exitNamedArg(Python3Parser.NamedArgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StarArg}
	 * labeled alternative in {@link Python3Parser#argument}.
	 * @param ctx the parse tree
	 */
	void enterStarArg(Python3Parser.StarArgContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StarArg}
	 * labeled alternative in {@link Python3Parser#argument}.
	 * @param ctx the parse tree
	 */
	void exitStarArg(Python3Parser.StarArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#name_list}.
	 * @param ctx the parse tree
	 */
	void enterName_list(Python3Parser.Name_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#name_list}.
	 * @param ctx the parse tree
	 */
	void exitName_list(Python3Parser.Name_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link Python3Parser#comp_for}.
	 * @param ctx the parse tree
	 */
	void enterComp_for(Python3Parser.Comp_forContext ctx);
	/**
	 * Exit a parse tree produced by {@link Python3Parser#comp_for}.
	 * @param ctx the parse tree
	 */
	void exitComp_for(Python3Parser.Comp_forContext ctx);
}