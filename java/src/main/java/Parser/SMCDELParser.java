// Generated from SMCDEL.g4 by ANTLR 4.9
package Parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SMCDELParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		VARS=1, LAW=2, OBS=3, COMMA=4, LP=5, RP=6, LSB=7, RSB=8, LCB=9, RCB=10, 
		OT=11, CT=12, EXC=13, AMP=14, PIPE=15, COLON=16, DOUBLEDASH=17, WHERE=18, 
		TRUE=19, VALID=20, COMMENT=21, NEG=22, TILDE=23, CONJ=24, DISJ=25, TOP=26, 
		BOT=27, KNOWS_THAT=28, KNOWS_WHETHER=29, WS=30, VARIABLE=31;
	public static final int
		RULE_propositions = 0, RULE_binary_formula = 1, RULE_optionally_bracketed_formula = 2, 
		RULE_formula_list = 3, RULE_variable_list = 4, RULE_formula = 5, RULE_state_law = 6, 
		RULE_observation = 7, RULE_observations = 8, RULE_query = 9, RULE_queries = 10, 
		RULE_model = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"propositions", "binary_formula", "optionally_bracketed_formula", "formula_list", 
			"variable_list", "formula", "state_law", "observation", "observations", 
			"query", "queries", "model"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'VARS'", "'LAW'", "'OBS'", "','", "'('", "')'", "'['", "']'", 
			"'{'", "'}'", "'<'", "'>'", "'!'", "'&'", "'|'", "':'", "'--'", "'WHERE?'", 
			"'TRUE?'", "'VALID?'", null, null, "'~'", null, null, "'Top'", "'Bot'", 
			"'knows that'", "'knows whether'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "VARS", "LAW", "OBS", "COMMA", "LP", "RP", "LSB", "RSB", "LCB", 
			"RCB", "OT", "CT", "EXC", "AMP", "PIPE", "COLON", "DOUBLEDASH", "WHERE", 
			"TRUE", "VALID", "COMMENT", "NEG", "TILDE", "CONJ", "DISJ", "TOP", "BOT", 
			"KNOWS_THAT", "KNOWS_WHETHER", "WS", "VARIABLE"
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
	public String getGrammarFileName() { return "SMCDEL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SMCDELParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class PropositionsContext extends ParserRuleContext {
		public Variable_listContext props;
		public TerminalNode VARS() { return getToken(SMCDELParser.VARS, 0); }
		public Variable_listContext variable_list() {
			return getRuleContext(Variable_listContext.class,0);
		}
		public PropositionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propositions; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitPropositions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropositionsContext propositions() throws RecognitionException {
		PropositionsContext _localctx = new PropositionsContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_propositions);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			match(VARS);
			setState(25);
			((PropositionsContext)_localctx).props = variable_list();
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

	public static class Binary_formulaContext extends ParserRuleContext {
		public Binary_formulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binary_formula; }
	 
		public Binary_formulaContext() { }
		public void copyFrom(Binary_formulaContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BinconjContext extends Binary_formulaContext {
		public Token left;
		public Token right;
		public TerminalNode AMP() { return getToken(SMCDELParser.AMP, 0); }
		public List<TerminalNode> VARIABLE() { return getTokens(SMCDELParser.VARIABLE); }
		public TerminalNode VARIABLE(int i) {
			return getToken(SMCDELParser.VARIABLE, i);
		}
		public BinconjContext(Binary_formulaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitBinconj(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BindisjContext extends Binary_formulaContext {
		public Token left;
		public Token right;
		public TerminalNode PIPE() { return getToken(SMCDELParser.PIPE, 0); }
		public List<TerminalNode> VARIABLE() { return getTokens(SMCDELParser.VARIABLE); }
		public TerminalNode VARIABLE(int i) {
			return getToken(SMCDELParser.VARIABLE, i);
		}
		public BindisjContext(Binary_formulaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitBindisj(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Binary_formulaContext binary_formula() throws RecognitionException {
		Binary_formulaContext _localctx = new Binary_formulaContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_binary_formula);
		try {
			setState(33);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				_localctx = new BinconjContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(27);
				((BinconjContext)_localctx).left = match(VARIABLE);
				setState(28);
				match(AMP);
				setState(29);
				((BinconjContext)_localctx).right = match(VARIABLE);
				}
				break;
			case 2:
				_localctx = new BindisjContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(30);
				((BindisjContext)_localctx).left = match(VARIABLE);
				setState(31);
				match(PIPE);
				setState(32);
				((BindisjContext)_localctx).right = match(VARIABLE);
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

	public static class Optionally_bracketed_formulaContext extends ParserRuleContext {
		public FormulaContext form;
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public TerminalNode LP() { return getToken(SMCDELParser.LP, 0); }
		public TerminalNode RP() { return getToken(SMCDELParser.RP, 0); }
		public Optionally_bracketed_formulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optionally_bracketed_formula; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitOptionally_bracketed_formula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Optionally_bracketed_formulaContext optionally_bracketed_formula() throws RecognitionException {
		Optionally_bracketed_formulaContext _localctx = new Optionally_bracketed_formulaContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_optionally_bracketed_formula);
		try {
			setState(40);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LSB:
			case OT:
			case NEG:
			case CONJ:
			case DISJ:
			case TOP:
			case BOT:
			case VARIABLE:
				enterOuterAlt(_localctx, 1);
				{
				setState(35);
				((Optionally_bracketed_formulaContext)_localctx).form = formula();
				}
				break;
			case LP:
				enterOuterAlt(_localctx, 2);
				{
				setState(36);
				match(LP);
				setState(37);
				((Optionally_bracketed_formulaContext)_localctx).form = formula();
				setState(38);
				match(RP);
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

	public static class Formula_listContext extends ParserRuleContext {
		public Optionally_bracketed_formulaContext optionally_bracketed_formula;
		public List<Optionally_bracketed_formulaContext> forms = new ArrayList<Optionally_bracketed_formulaContext>();
		public List<Optionally_bracketed_formulaContext> optionally_bracketed_formula() {
			return getRuleContexts(Optionally_bracketed_formulaContext.class);
		}
		public Optionally_bracketed_formulaContext optionally_bracketed_formula(int i) {
			return getRuleContext(Optionally_bracketed_formulaContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(SMCDELParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(SMCDELParser.COMMA, i);
		}
		public Formula_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula_list; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitFormula_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Formula_listContext formula_list() throws RecognitionException {
		Formula_listContext _localctx = new Formula_listContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_formula_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			((Formula_listContext)_localctx).optionally_bracketed_formula = optionally_bracketed_formula();
			((Formula_listContext)_localctx).forms.add(((Formula_listContext)_localctx).optionally_bracketed_formula);
			setState(47);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(43);
				match(COMMA);
				setState(44);
				((Formula_listContext)_localctx).optionally_bracketed_formula = optionally_bracketed_formula();
				((Formula_listContext)_localctx).forms.add(((Formula_listContext)_localctx).optionally_bracketed_formula);
				}
				}
				setState(49);
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

	public static class Variable_listContext extends ParserRuleContext {
		public Token VARIABLE;
		public List<Token> vs = new ArrayList<Token>();
		public List<TerminalNode> VARIABLE() { return getTokens(SMCDELParser.VARIABLE); }
		public TerminalNode VARIABLE(int i) {
			return getToken(SMCDELParser.VARIABLE, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(SMCDELParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(SMCDELParser.COMMA, i);
		}
		public Variable_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable_list; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitVariable_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Variable_listContext variable_list() throws RecognitionException {
		Variable_listContext _localctx = new Variable_listContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_variable_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			((Variable_listContext)_localctx).VARIABLE = match(VARIABLE);
			((Variable_listContext)_localctx).vs.add(((Variable_listContext)_localctx).VARIABLE);
			setState(55);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(51);
				match(COMMA);
				setState(52);
				((Variable_listContext)_localctx).VARIABLE = match(VARIABLE);
				((Variable_listContext)_localctx).vs.add(((Variable_listContext)_localctx).VARIABLE);
				}
				}
				setState(57);
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

	public static class FormulaContext extends ParserRuleContext {
		public FormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula; }
	 
		public FormulaContext() { }
		public void copyFrom(FormulaContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PubAnnounceBoxContext extends FormulaContext {
		public Optionally_bracketed_formulaContext announced;
		public Optionally_bracketed_formulaContext form;
		public TerminalNode LSB() { return getToken(SMCDELParser.LSB, 0); }
		public TerminalNode EXC() { return getToken(SMCDELParser.EXC, 0); }
		public TerminalNode RSB() { return getToken(SMCDELParser.RSB, 0); }
		public List<Optionally_bracketed_formulaContext> optionally_bracketed_formula() {
			return getRuleContexts(Optionally_bracketed_formulaContext.class);
		}
		public Optionally_bracketed_formulaContext optionally_bracketed_formula(int i) {
			return getRuleContext(Optionally_bracketed_formulaContext.class,i);
		}
		public PubAnnounceBoxContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitPubAnnounceBox(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NegContext extends FormulaContext {
		public Optionally_bracketed_formulaContext negated;
		public TerminalNode NEG() { return getToken(SMCDELParser.NEG, 0); }
		public Optionally_bracketed_formulaContext optionally_bracketed_formula() {
			return getRuleContext(Optionally_bracketed_formulaContext.class,0);
		}
		public NegContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitNeg(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DisjContext extends FormulaContext {
		public Formula_listContext disjs;
		public TerminalNode DISJ() { return getToken(SMCDELParser.DISJ, 0); }
		public TerminalNode LP() { return getToken(SMCDELParser.LP, 0); }
		public TerminalNode RP() { return getToken(SMCDELParser.RP, 0); }
		public Formula_listContext formula_list() {
			return getRuleContext(Formula_listContext.class,0);
		}
		public DisjContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitDisj(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TopContext extends FormulaContext {
		public TerminalNode TOP() { return getToken(SMCDELParser.TOP, 0); }
		public TopContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitTop(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class KnowsthatContext extends FormulaContext {
		public Token agent;
		public Optionally_bracketed_formulaContext knows_that;
		public TerminalNode KNOWS_THAT() { return getToken(SMCDELParser.KNOWS_THAT, 0); }
		public TerminalNode VARIABLE() { return getToken(SMCDELParser.VARIABLE, 0); }
		public Optionally_bracketed_formulaContext optionally_bracketed_formula() {
			return getRuleContext(Optionally_bracketed_formulaContext.class,0);
		}
		public KnowsthatContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitKnowsthat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BotContext extends FormulaContext {
		public TerminalNode BOT() { return getToken(SMCDELParser.BOT, 0); }
		public BotContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitBot(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinContext extends FormulaContext {
		public Binary_formulaContext binary_formula() {
			return getRuleContext(Binary_formulaContext.class,0);
		}
		public BinContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitBin(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConjContext extends FormulaContext {
		public Formula_listContext conjs;
		public TerminalNode CONJ() { return getToken(SMCDELParser.CONJ, 0); }
		public TerminalNode LP() { return getToken(SMCDELParser.LP, 0); }
		public TerminalNode RP() { return getToken(SMCDELParser.RP, 0); }
		public Formula_listContext formula_list() {
			return getRuleContext(Formula_listContext.class,0);
		}
		public ConjContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitConj(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PropContext extends FormulaContext {
		public Token p;
		public TerminalNode VARIABLE() { return getToken(SMCDELParser.VARIABLE, 0); }
		public PropContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitProp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class KnowswhetherContext extends FormulaContext {
		public Token agent;
		public Optionally_bracketed_formulaContext knows_whether;
		public TerminalNode KNOWS_WHETHER() { return getToken(SMCDELParser.KNOWS_WHETHER, 0); }
		public TerminalNode VARIABLE() { return getToken(SMCDELParser.VARIABLE, 0); }
		public Optionally_bracketed_formulaContext optionally_bracketed_formula() {
			return getRuleContext(Optionally_bracketed_formulaContext.class,0);
		}
		public KnowswhetherContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitKnowswhether(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PubAnnounceDiamondContext extends FormulaContext {
		public Optionally_bracketed_formulaContext announced;
		public Optionally_bracketed_formulaContext form;
		public TerminalNode OT() { return getToken(SMCDELParser.OT, 0); }
		public TerminalNode EXC() { return getToken(SMCDELParser.EXC, 0); }
		public TerminalNode CT() { return getToken(SMCDELParser.CT, 0); }
		public List<Optionally_bracketed_formulaContext> optionally_bracketed_formula() {
			return getRuleContexts(Optionally_bracketed_formulaContext.class);
		}
		public Optionally_bracketed_formulaContext optionally_bracketed_formula(int i) {
			return getRuleContext(Optionally_bracketed_formulaContext.class,i);
		}
		public PubAnnounceDiamondContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitPubAnnounceDiamond(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormulaContext formula() throws RecognitionException {
		FormulaContext _localctx = new FormulaContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_formula);
		try {
			setState(92);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				_localctx = new TopContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(58);
				match(TOP);
				}
				break;
			case 2:
				_localctx = new BotContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(59);
				match(BOT);
				}
				break;
			case 3:
				_localctx = new BinContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(60);
				binary_formula();
				}
				break;
			case 4:
				_localctx = new ConjContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(61);
				match(CONJ);
				setState(62);
				match(LP);
				setState(63);
				((ConjContext)_localctx).conjs = formula_list();
				setState(64);
				match(RP);
				}
				break;
			case 5:
				_localctx = new DisjContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(66);
				match(DISJ);
				setState(67);
				match(LP);
				setState(68);
				((DisjContext)_localctx).disjs = formula_list();
				setState(69);
				match(RP);
				}
				break;
			case 6:
				_localctx = new NegContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(71);
				match(NEG);
				setState(72);
				((NegContext)_localctx).negated = optionally_bracketed_formula();
				}
				break;
			case 7:
				_localctx = new KnowsthatContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(73);
				((KnowsthatContext)_localctx).agent = match(VARIABLE);
				setState(74);
				match(KNOWS_THAT);
				setState(75);
				((KnowsthatContext)_localctx).knows_that = optionally_bracketed_formula();
				}
				break;
			case 8:
				_localctx = new KnowswhetherContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(76);
				((KnowswhetherContext)_localctx).agent = match(VARIABLE);
				setState(77);
				match(KNOWS_WHETHER);
				setState(78);
				((KnowswhetherContext)_localctx).knows_whether = optionally_bracketed_formula();
				}
				break;
			case 9:
				_localctx = new PubAnnounceDiamondContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(79);
				match(OT);
				setState(80);
				match(EXC);
				setState(81);
				((PubAnnounceDiamondContext)_localctx).announced = optionally_bracketed_formula();
				setState(82);
				match(CT);
				setState(83);
				((PubAnnounceDiamondContext)_localctx).form = optionally_bracketed_formula();
				}
				break;
			case 10:
				_localctx = new PubAnnounceBoxContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(85);
				match(LSB);
				setState(86);
				match(EXC);
				setState(87);
				((PubAnnounceBoxContext)_localctx).announced = optionally_bracketed_formula();
				setState(88);
				match(RSB);
				setState(89);
				((PubAnnounceBoxContext)_localctx).form = optionally_bracketed_formula();
				}
				break;
			case 11:
				_localctx = new PropContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(91);
				((PropContext)_localctx).p = match(VARIABLE);
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

	public static class State_lawContext extends ParserRuleContext {
		public FormulaContext form;
		public TerminalNode LAW() { return getToken(SMCDELParser.LAW, 0); }
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public State_lawContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_state_law; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitState_law(this);
			else return visitor.visitChildren(this);
		}
	}

	public final State_lawContext state_law() throws RecognitionException {
		State_lawContext _localctx = new State_lawContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_state_law);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			match(LAW);
			setState(95);
			((State_lawContext)_localctx).form = formula();
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

	public static class ObservationContext extends ParserRuleContext {
		public Token agent;
		public Variable_listContext vs;
		public TerminalNode COLON() { return getToken(SMCDELParser.COLON, 0); }
		public TerminalNode VARIABLE() { return getToken(SMCDELParser.VARIABLE, 0); }
		public Variable_listContext variable_list() {
			return getRuleContext(Variable_listContext.class,0);
		}
		public ObservationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_observation; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitObservation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObservationContext observation() throws RecognitionException {
		ObservationContext _localctx = new ObservationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_observation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			((ObservationContext)_localctx).agent = match(VARIABLE);
			setState(98);
			match(COLON);
			setState(99);
			((ObservationContext)_localctx).vs = variable_list();
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

	public static class ObservationsContext extends ParserRuleContext {
		public ObservationContext observation;
		public List<ObservationContext> obs = new ArrayList<ObservationContext>();
		public TerminalNode OBS() { return getToken(SMCDELParser.OBS, 0); }
		public List<ObservationContext> observation() {
			return getRuleContexts(ObservationContext.class);
		}
		public ObservationContext observation(int i) {
			return getRuleContext(ObservationContext.class,i);
		}
		public ObservationsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_observations; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitObservations(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObservationsContext observations() throws RecognitionException {
		ObservationsContext _localctx = new ObservationsContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_observations);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			match(OBS);
			setState(103); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(102);
				((ObservationsContext)_localctx).observation = observation();
				((ObservationsContext)_localctx).obs.add(((ObservationsContext)_localctx).observation);
				}
				}
				setState(105); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==VARIABLE );
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

	public static class QueryContext extends ParserRuleContext {
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
	 
		public QueryContext() { }
		public void copyFrom(QueryContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class WhereQueryContext extends QueryContext {
		public FormulaContext form;
		public TerminalNode WHERE() { return getToken(SMCDELParser.WHERE, 0); }
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public WhereQueryContext(QueryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitWhereQuery(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ValidQueryContext extends QueryContext {
		public FormulaContext form;
		public TerminalNode VALID() { return getToken(SMCDELParser.VALID, 0); }
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public ValidQueryContext(QueryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitValidQuery(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TrueQueryContext extends QueryContext {
		public Variable_listContext props;
		public FormulaContext form;
		public TerminalNode TRUE() { return getToken(SMCDELParser.TRUE, 0); }
		public TerminalNode LCB() { return getToken(SMCDELParser.LCB, 0); }
		public TerminalNode RCB() { return getToken(SMCDELParser.RCB, 0); }
		public Variable_listContext variable_list() {
			return getRuleContext(Variable_listContext.class,0);
		}
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public TrueQueryContext(QueryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitTrueQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_query);
		try {
			setState(117);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WHERE:
				_localctx = new WhereQueryContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(107);
				match(WHERE);
				setState(108);
				((WhereQueryContext)_localctx).form = formula();
				}
				break;
			case TRUE:
				_localctx = new TrueQueryContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(109);
				match(TRUE);
				setState(110);
				match(LCB);
				setState(111);
				((TrueQueryContext)_localctx).props = variable_list();
				setState(112);
				match(RCB);
				setState(113);
				((TrueQueryContext)_localctx).form = formula();
				}
				break;
			case VALID:
				_localctx = new ValidQueryContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(115);
				match(VALID);
				setState(116);
				((ValidQueryContext)_localctx).form = formula();
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

	public static class QueriesContext extends ParserRuleContext {
		public QueryContext query;
		public List<QueryContext> q = new ArrayList<QueryContext>();
		public List<QueryContext> query() {
			return getRuleContexts(QueryContext.class);
		}
		public QueryContext query(int i) {
			return getRuleContext(QueryContext.class,i);
		}
		public QueriesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queries; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitQueries(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueriesContext queries() throws RecognitionException {
		QueriesContext _localctx = new QueriesContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_queries);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(119);
				((QueriesContext)_localctx).query = query();
				((QueriesContext)_localctx).q.add(((QueriesContext)_localctx).query);
				}
				}
				setState(122); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << WHERE) | (1L << TRUE) | (1L << VALID))) != 0) );
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

	public static class ModelContext extends ParserRuleContext {
		public PropositionsContext props;
		public State_lawContext law;
		public ObservationsContext obs;
		public QueriesContext qs;
		public TerminalNode EOF() { return getToken(SMCDELParser.EOF, 0); }
		public PropositionsContext propositions() {
			return getRuleContext(PropositionsContext.class,0);
		}
		public State_lawContext state_law() {
			return getRuleContext(State_lawContext.class,0);
		}
		public ObservationsContext observations() {
			return getRuleContext(ObservationsContext.class,0);
		}
		public QueriesContext queries() {
			return getRuleContext(QueriesContext.class,0);
		}
		public ModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_model; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SMCDELVisitor ) return ((SMCDELVisitor<? extends T>)visitor).visitModel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModelContext model() throws RecognitionException {
		ModelContext _localctx = new ModelContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_model);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			((ModelContext)_localctx).props = propositions();
			setState(125);
			((ModelContext)_localctx).law = state_law();
			setState(126);
			((ModelContext)_localctx).obs = observations();
			setState(127);
			((ModelContext)_localctx).qs = queries();
			setState(128);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3!\u0085\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\5\3$\n\3\3\4\3"+
		"\4\3\4\3\4\3\4\5\4+\n\4\3\5\3\5\3\5\7\5\60\n\5\f\5\16\5\63\13\5\3\6\3"+
		"\6\3\6\7\68\n\6\f\6\16\6;\13\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7_\n\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3"+
		"\n\6\nj\n\n\r\n\16\nk\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\5\13x\n\13\3\f\6\f{\n\f\r\f\16\f|\3\r\3\r\3\r\3\r\3\r\3\r\3\r\2\2\16"+
		"\2\4\6\b\n\f\16\20\22\24\26\30\2\2\2\u008a\2\32\3\2\2\2\4#\3\2\2\2\6*"+
		"\3\2\2\2\b,\3\2\2\2\n\64\3\2\2\2\f^\3\2\2\2\16`\3\2\2\2\20c\3\2\2\2\22"+
		"g\3\2\2\2\24w\3\2\2\2\26z\3\2\2\2\30~\3\2\2\2\32\33\7\3\2\2\33\34\5\n"+
		"\6\2\34\3\3\2\2\2\35\36\7!\2\2\36\37\7\20\2\2\37$\7!\2\2 !\7!\2\2!\"\7"+
		"\21\2\2\"$\7!\2\2#\35\3\2\2\2# \3\2\2\2$\5\3\2\2\2%+\5\f\7\2&\'\7\7\2"+
		"\2\'(\5\f\7\2()\7\b\2\2)+\3\2\2\2*%\3\2\2\2*&\3\2\2\2+\7\3\2\2\2,\61\5"+
		"\6\4\2-.\7\6\2\2.\60\5\6\4\2/-\3\2\2\2\60\63\3\2\2\2\61/\3\2\2\2\61\62"+
		"\3\2\2\2\62\t\3\2\2\2\63\61\3\2\2\2\649\7!\2\2\65\66\7\6\2\2\668\7!\2"+
		"\2\67\65\3\2\2\28;\3\2\2\29\67\3\2\2\29:\3\2\2\2:\13\3\2\2\2;9\3\2\2\2"+
		"<_\7\34\2\2=_\7\35\2\2>_\5\4\3\2?@\7\32\2\2@A\7\7\2\2AB\5\b\5\2BC\7\b"+
		"\2\2C_\3\2\2\2DE\7\33\2\2EF\7\7\2\2FG\5\b\5\2GH\7\b\2\2H_\3\2\2\2IJ\7"+
		"\30\2\2J_\5\6\4\2KL\7!\2\2LM\7\36\2\2M_\5\6\4\2NO\7!\2\2OP\7\37\2\2P_"+
		"\5\6\4\2QR\7\r\2\2RS\7\17\2\2ST\5\6\4\2TU\7\16\2\2UV\5\6\4\2V_\3\2\2\2"+
		"WX\7\t\2\2XY\7\17\2\2YZ\5\6\4\2Z[\7\n\2\2[\\\5\6\4\2\\_\3\2\2\2]_\7!\2"+
		"\2^<\3\2\2\2^=\3\2\2\2^>\3\2\2\2^?\3\2\2\2^D\3\2\2\2^I\3\2\2\2^K\3\2\2"+
		"\2^N\3\2\2\2^Q\3\2\2\2^W\3\2\2\2^]\3\2\2\2_\r\3\2\2\2`a\7\4\2\2ab\5\f"+
		"\7\2b\17\3\2\2\2cd\7!\2\2de\7\22\2\2ef\5\n\6\2f\21\3\2\2\2gi\7\5\2\2h"+
		"j\5\20\t\2ih\3\2\2\2jk\3\2\2\2ki\3\2\2\2kl\3\2\2\2l\23\3\2\2\2mn\7\24"+
		"\2\2nx\5\f\7\2op\7\25\2\2pq\7\13\2\2qr\5\n\6\2rs\7\f\2\2st\5\f\7\2tx\3"+
		"\2\2\2uv\7\26\2\2vx\5\f\7\2wm\3\2\2\2wo\3\2\2\2wu\3\2\2\2x\25\3\2\2\2"+
		"y{\5\24\13\2zy\3\2\2\2{|\3\2\2\2|z\3\2\2\2|}\3\2\2\2}\27\3\2\2\2~\177"+
		"\5\2\2\2\177\u0080\5\16\b\2\u0080\u0081\5\22\n\2\u0081\u0082\5\26\f\2"+
		"\u0082\u0083\7\2\2\3\u0083\31\3\2\2\2\n#*\619^kw|";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}