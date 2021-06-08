// Generated from SMCDEL.g4 by ANTLR 4.9
package Parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SMCDELParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SMCDELVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SMCDELParser#propositions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropositions(SMCDELParser.PropositionsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binconj}
	 * labeled alternative in {@link SMCDELParser#binary_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinconj(SMCDELParser.BinconjContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bindisj}
	 * labeled alternative in {@link SMCDELParser#binary_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBindisj(SMCDELParser.BindisjContext ctx);
	/**
	 * Visit a parse tree produced by {@link SMCDELParser#optionally_bracketed_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOptionally_bracketed_formula(SMCDELParser.Optionally_bracketed_formulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link SMCDELParser#formula_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormula_list(SMCDELParser.Formula_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SMCDELParser#variable_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable_list(SMCDELParser.Variable_listContext ctx);
	/**
	 * Visit a parse tree produced by the {@code top}
	 * labeled alternative in {@link SMCDELParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTop(SMCDELParser.TopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bot}
	 * labeled alternative in {@link SMCDELParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBot(SMCDELParser.BotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bin}
	 * labeled alternative in {@link SMCDELParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBin(SMCDELParser.BinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code conj}
	 * labeled alternative in {@link SMCDELParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConj(SMCDELParser.ConjContext ctx);
	/**
	 * Visit a parse tree produced by the {@code disj}
	 * labeled alternative in {@link SMCDELParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDisj(SMCDELParser.DisjContext ctx);
	/**
	 * Visit a parse tree produced by the {@code neg}
	 * labeled alternative in {@link SMCDELParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNeg(SMCDELParser.NegContext ctx);
	/**
	 * Visit a parse tree produced by the {@code knowsthat}
	 * labeled alternative in {@link SMCDELParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKnowsthat(SMCDELParser.KnowsthatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code knowswhether}
	 * labeled alternative in {@link SMCDELParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKnowswhether(SMCDELParser.KnowswhetherContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pubAnnounceDiamond}
	 * labeled alternative in {@link SMCDELParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPubAnnounceDiamond(SMCDELParser.PubAnnounceDiamondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pubAnnounceBox}
	 * labeled alternative in {@link SMCDELParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPubAnnounceBox(SMCDELParser.PubAnnounceBoxContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prop}
	 * labeled alternative in {@link SMCDELParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProp(SMCDELParser.PropContext ctx);
	/**
	 * Visit a parse tree produced by {@link SMCDELParser#state_law}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitState_law(SMCDELParser.State_lawContext ctx);
	/**
	 * Visit a parse tree produced by {@link SMCDELParser#observation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObservation(SMCDELParser.ObservationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SMCDELParser#observations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObservations(SMCDELParser.ObservationsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whereQuery}
	 * labeled alternative in {@link SMCDELParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhereQuery(SMCDELParser.WhereQueryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code trueQuery}
	 * labeled alternative in {@link SMCDELParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueQuery(SMCDELParser.TrueQueryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code validQuery}
	 * labeled alternative in {@link SMCDELParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValidQuery(SMCDELParser.ValidQueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SMCDELParser#queries}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueries(SMCDELParser.QueriesContext ctx);
	/**
	 * Visit a parse tree produced by {@link SMCDELParser#model}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModel(SMCDELParser.ModelContext ctx);
}