package Parser.Visitors;

import EpistemicModelChecker.Formula;
import EpistemicModelChecker.FormulaType;
import Parser.SMCDELBaseVisitor;
import Parser.SMCDELParser;
import Parser.SMCDELVisitor;
import org.antlr.v4.runtime.Token;

import java.util.Arrays;
import java.util.Collections;

public class FormulaVisitor extends SMCDELBaseVisitor<Formula> implements SMCDELVisitor<Formula> {
    @Override
    public Formula visitState_law(SMCDELParser.State_lawContext ctx) {
        return visit(ctx.form);
    }

    @Override
    public Formula visitTop(SMCDELParser.TopContext ctx) {
        return new Formula(FormulaType.Top);
    }

    @Override
    public Formula visitBot(SMCDELParser.BotContext ctx) {
        return new Formula(FormulaType.Bot);
    }

    @Override
    public Formula visitBin(SMCDELParser.BinContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public Formula visitBinconj(SMCDELParser.BinconjContext ctx) {
        return new Formula(FormulaType.And, Arrays.asList(var_to_prop(ctx.left), var_to_prop(ctx.right)));
    }

    @Override
    public Formula visitBindisj(SMCDELParser.BindisjContext ctx) {
        return new Formula(FormulaType.Or, Arrays.asList(var_to_prop(ctx.left), var_to_prop(ctx.right)));
    }

    private Formula var_to_prop(Token t) {
        return new Formula(FormulaType.Proposition, t.getText());
    }

    @Override
    public Formula visitOptionally_bracketed_formula(SMCDELParser.Optionally_bracketed_formulaContext ctx) {
        return visit(ctx.form);
    }

    @Override
    public Formula visitConj(SMCDELParser.ConjContext ctx) {
        return new Formula(FormulaType.And, new FormulaListVisitor().visit(ctx.formula_list()));
    }

    @Override
    public Formula visitDisj(SMCDELParser.DisjContext ctx) {
        return new Formula(FormulaType.Or, new FormulaListVisitor().visit(ctx.formula_list()));
    }

    @Override
    public Formula visitNeg(SMCDELParser.NegContext ctx) {
        return new Formula(FormulaType.Not, Collections.singletonList(visit(ctx.negated)));
    }

    @Override
    public Formula visitKnowsthat(SMCDELParser.KnowsthatContext ctx) {
        return new Formula(FormulaType.KnowsThat, Collections.singletonList(visit(ctx.knows_that)), ctx.agent.getText());
    }

    @Override
    public Formula visitKnowswhether(SMCDELParser.KnowswhetherContext ctx) {
        return new Formula(FormulaType.KnowsWhether, Collections.singletonList(visit(ctx.knows_whether)), ctx.agent.getText());
    }

    @Override
    public Formula visitProp(SMCDELParser.PropContext ctx) {
        return var_to_prop(ctx.p);
    }

    @Override
    public Formula visitPubAnnounceBox(SMCDELParser.PubAnnounceBoxContext ctx) {
        return new Formula(FormulaType.PubAnnounceBox, Collections.singletonList(visit(ctx.form)), visit(ctx.announced));
    }

    @Override
    public Formula visitPubAnnounceDiamond(SMCDELParser.PubAnnounceDiamondContext ctx) {
        return new Formula(FormulaType.PubAnnounceDiamond, Collections.singletonList(visit(ctx.form)), visit(ctx.announced));
    }
}

