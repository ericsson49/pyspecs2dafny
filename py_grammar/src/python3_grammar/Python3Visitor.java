// Generated from java-escape by ANTLR 4.11.1
package python3_grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Python3Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface Python3Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link Python3Parser#file_input}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile_input(Python3Parser.File_inputContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#top_level_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTop_level_stmt(Python3Parser.Top_level_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#decorator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecorator(Python3Parser.DecoratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#dotted_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotted_name(Python3Parser.Dotted_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#func_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_def(Python3Parser.Func_defContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#typedargslist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypedargslist(Python3Parser.TypedargslistContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#tfparg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTfparg(Python3Parser.TfpargContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#tfpdef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTfpdef(Python3Parser.TfpdefContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#class_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClass_def(Python3Parser.Class_defContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#class_suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClass_suite(Python3Parser.Class_suiteContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Fields}
	 * labeled alternative in {@link Python3Parser#class_fields}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFields(Python3Parser.FieldsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EmptyClass}
	 * labeled alternative in {@link Python3Parser#class_fields}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyClass(Python3Parser.EmptyClassContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#field_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField_def(Python3Parser.Field_defContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#type_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_expr(Python3Parser.Type_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#type_expr_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_expr_list(Python3Parser.Type_expr_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#varargslist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarargslist(Python3Parser.VarargslistContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#vfpdef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVfpdef(Python3Parser.VfpdefContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(Python3Parser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#simple_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimple_stmt(Python3Parser.Simple_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprStmt}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStmt(Python3Parser.ExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Assign}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(Python3Parser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AnnAssign}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnAssign(Python3Parser.AnnAssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AugAssign}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAugAssign(Python3Parser.AugAssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Del}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDel(Python3Parser.DelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Pass}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPass(Python3Parser.PassContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Break}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreak(Python3Parser.BreakContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Continue}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinue(Python3Parser.ContinueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Return}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn(Python3Parser.ReturnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Assert}
	 * labeled alternative in {@link Python3Parser#small_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssert(Python3Parser.AssertContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#aug_assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAug_assign(Python3Parser.Aug_assignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code If}
	 * labeled alternative in {@link Python3Parser#compound_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf(Python3Parser.IfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code While}
	 * labeled alternative in {@link Python3Parser#compound_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile(Python3Parser.WhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code For}
	 * labeled alternative in {@link Python3Parser#compound_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor(Python3Parser.ForContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FuncDef}
	 * labeled alternative in {@link Python3Parser#compound_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDef(Python3Parser.FuncDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ClassDef}
	 * labeled alternative in {@link Python3Parser#compound_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDef(Python3Parser.ClassDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#annotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotation(Python3Parser.AnnotationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SimpleStmt}
	 * labeled alternative in {@link Python3Parser#suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleStmt(Python3Parser.SimpleStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Block}
	 * labeled alternative in {@link Python3Parser#suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(Python3Parser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#comp_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_op(Python3Parser.Comp_opContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfExp}
	 * labeled alternative in {@link Python3Parser#test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfExp(Python3Parser.IfExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TExpr}
	 * labeled alternative in {@link Python3Parser#test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTExpr(Python3Parser.TExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Lambda}
	 * labeled alternative in {@link Python3Parser#test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambda(Python3Parser.LambdaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(Python3Parser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BitOr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitOr(Python3Parser.BitOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StarExpr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStarExpr(Python3Parser.StarExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Term}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(Python3Parser.TermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AtomExpr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomExpr(Python3Parser.AtomExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(Python3Parser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PlusMinus}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusMinus(Python3Parser.PlusMinusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BitXor}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitXor(Python3Parser.BitXorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Factor}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(Python3Parser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BitAnd}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitAnd(Python3Parser.BitAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Compare}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompare(Python3Parser.CompareContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpr(Python3Parser.NotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ShiftExpr}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftExpr(Python3Parser.ShiftExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Power}
	 * labeled alternative in {@link Python3Parser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPower(Python3Parser.PowerContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#atom_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom_expr(Python3Parser.Atom_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParensExpr}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParensExpr(Python3Parser.ParensExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Tuple}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTuple(Python3Parser.TupleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ListLit}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListLit(Python3Parser.ListLitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DictLit}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictLit(Python3Parser.DictLitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetLit}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetLit(Python3Parser.SetLitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Name}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitName(Python3Parser.NameContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Number}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(Python3Parser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Str}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStr(Python3Parser.StrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Ellipsis}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEllipsis(Python3Parser.EllipsisContext ctx);
	/**
	 * Visit a parse tree produced by the {@code None}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNone(Python3Parser.NoneContext ctx);
	/**
	 * Visit a parse tree produced by the {@code True}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrue(Python3Parser.TrueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code False}
	 * labeled alternative in {@link Python3Parser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalse(Python3Parser.FalseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CallTrailer}
	 * labeled alternative in {@link Python3Parser#trailer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallTrailer(Python3Parser.CallTrailerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SubscrTrailer}
	 * labeled alternative in {@link Python3Parser#trailer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscrTrailer(Python3Parser.SubscrTrailerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AttrTrailer}
	 * labeled alternative in {@link Python3Parser#trailer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttrTrailer(Python3Parser.AttrTrailerContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#subscriptlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscriptlist(Python3Parser.SubscriptlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#subscript}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscript(Python3Parser.SubscriptContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#sliceop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSliceop(Python3Parser.SliceopContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#expr_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_list(Python3Parser.Expr_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#test_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTest_list(Python3Parser.Test_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#arglist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArglist(Python3Parser.ArglistContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument(Python3Parser.ArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#comp_iter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_iter(Python3Parser.Comp_iterContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#sync_comp_for}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSync_comp_for(Python3Parser.Sync_comp_forContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#comp_for}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_for(Python3Parser.Comp_forContext ctx);
	/**
	 * Visit a parse tree produced by {@link Python3Parser#comp_if}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_if(Python3Parser.Comp_ifContext ctx);
}