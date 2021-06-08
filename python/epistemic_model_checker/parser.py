from typing import Any, Iterator, Tuple
from epistemic_model_checker.antlr4_parser import (
    SMCDELLexer,
    SMCDELParser,
    SMCDELVisitor,
)
from antlr4 import CommonTokenStream, FileStream, ParseTreeWalker
from epistemic_model_checker.internal import (
    Formula as F,
    FormulaType as FT,
    Query,
    QueryType,
    powerset,
)

P = SMCDELParser.SMCDELParser


def var_to_prop(var: Any):
    return F(FT.Proposition, proposition=var.text)


class SMCDELVInterpreter(SMCDELVisitor.SMCDELVisitor):
    def visitModel(self, ctx: P.ModelContext):
        variables = self.visitPropositions(ctx.props)
        state_law = self.visitState_law(ctx.law)
        obs = self.visitObservations(ctx.obs)
        queries = self.visitQueries(ctx.qs)
        return variables, state_law, obs, queries

    def visitPropositions(self, ctx: P.PropositionsContext):
        return self.visitVariable_list(ctx.props)

    def visitVariable_list(self, ctx: P.Variable_listContext):
        return [v.text for v in ctx.vs]

    def visitState_law(self, ctx: P.State_lawContext):
        return self.visit(ctx.form)

    def visitFormula_list(self, ctx: P.Formula_listContext):
        return [self.visit(f) for f in ctx.forms]

    def visitOptionally_bracketed_formula(
        self, ctx: P.Optionally_bracketed_formulaContext
    ):
        return self.visit(ctx.form)

    def visitTop(self, ctx: P.TopContext):
        return F(FT.Top)

    def visitBot(self, ctx: P.BotContext):
        return F(FT.Bot)

    def visitBin(self, ctx: P.BinContext):
        return self.visit(ctx.children[0])

    def visitBinconj(self, ctx: P.BinconjContext):
        return F(FT.And, children=[var_to_prop(ctx.left), var_to_prop(ctx.right)])

    def visitBindisj(self, ctx: P.BindisjContext):
        return F(FT.Or, children=[var_to_prop(ctx.left), var_to_prop(ctx.right)])

    def visitConj(self, ctx: P.ConjContext):
        return F(FT.And, children=self.visit(ctx.conjs))

    def visitDisj(self, ctx: P.DisjContext):
        return F(FT.Or, children=self.visit(ctx.disjs))

    def visitNeg(self, ctx: P.NegContext):
        return F(FT.Not, children=[self.visit(ctx.negated)])

    def visitKnowsthat(self, ctx: P.KnowsthatContext):
        return F(
            FT.KnowsThat,
            agent=ctx.agent.text,
            children=[self.visit(ctx.knows_that)],
        )

    def visitKnowswhether(self, ctx: P.KnowswhetherContext):
        return F(
            FT.KnowsWhether,
            agent=ctx.agent.text,
            children=[self.visit(ctx.knows_whether)],
        )

    def visitProp(self, ctx: P.PropContext):
        return var_to_prop(ctx.p)

    def visitPubAnnounceBox(self, ctx: P.PubAnnounceBoxContext):
        return F(
            FT.PubAnnounceBox,
            children=[self.visit(ctx.form)],
            announcement=self.visit(ctx.announced),
        )

    def visitPubAnnounceDiamond(self, ctx: P.PubAnnounceDiamondContext):
        return F(
            FT.PubAnnounceDiamond,
            children=[self.visit(ctx.form)],
            announcement=self.visit(ctx.announced),
        )

    def visitObservations(self, ctx: P.ObservationsContext):
        return [self.visit(o) for o in ctx.obs]

    def visitObservation(self, ctx: P.ObservationContext):
        return ctx.agent.text, self.visit(ctx.vs)

    def visitQueries(self, ctx: P.QueriesContext):
        return [self.visit(q) for q in ctx.q]

    def visitWhereQuery(self, ctx: P.WhereQueryContext):
        return Query(QueryType.WHERE, formula=self.visit(ctx.form))

    def visitValidQuery(self, ctx: P.ValidQueryContext):
        return Query(QueryType.VALID, formula=self.visit(ctx.form))

    def visitTrueQuery(self, ctx: P.TrueQueryContext):
        return Query(
            QueryType.TRUE,
            formula=self.visit(ctx.form),
            propositions=self.visit(ctx.props),
        )


def get_model_data(
    filename: str, encoding="ascii"
) -> Tuple[list[str], F, list[Tuple[str, list[str]]], list[Query]]:
    input_stream = FileStream(filename, encoding)
    lexer = SMCDELLexer.SMCDELLexer(input_stream)
    stream = CommonTokenStream(lexer)
    parser = SMCDELParser.SMCDELParser(stream)

    tree = parser.model()

    return SMCDELVInterpreter().visit(tree)