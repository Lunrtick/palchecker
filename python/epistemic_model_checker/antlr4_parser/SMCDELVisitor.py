# Generated from SMCDEL.g4 by ANTLR 4.9
from antlr4 import *
if __name__ is not None and "." in __name__:
    from .SMCDELParser import SMCDELParser
else:
    from SMCDELParser import SMCDELParser

# This class defines a complete generic visitor for a parse tree produced by SMCDELParser.

class SMCDELVisitor(ParseTreeVisitor):

    # Visit a parse tree produced by SMCDELParser#propositions.
    def visitPropositions(self, ctx:SMCDELParser.PropositionsContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#binconj.
    def visitBinconj(self, ctx:SMCDELParser.BinconjContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#bindisj.
    def visitBindisj(self, ctx:SMCDELParser.BindisjContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#optionally_bracketed_formula.
    def visitOptionally_bracketed_formula(self, ctx:SMCDELParser.Optionally_bracketed_formulaContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#formula_list.
    def visitFormula_list(self, ctx:SMCDELParser.Formula_listContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#variable_list.
    def visitVariable_list(self, ctx:SMCDELParser.Variable_listContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#top.
    def visitTop(self, ctx:SMCDELParser.TopContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#bot.
    def visitBot(self, ctx:SMCDELParser.BotContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#bin.
    def visitBin(self, ctx:SMCDELParser.BinContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#conj.
    def visitConj(self, ctx:SMCDELParser.ConjContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#disj.
    def visitDisj(self, ctx:SMCDELParser.DisjContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#neg.
    def visitNeg(self, ctx:SMCDELParser.NegContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#knowsthat.
    def visitKnowsthat(self, ctx:SMCDELParser.KnowsthatContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#knowswhether.
    def visitKnowswhether(self, ctx:SMCDELParser.KnowswhetherContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#pubAnnounceDiamond.
    def visitPubAnnounceDiamond(self, ctx:SMCDELParser.PubAnnounceDiamondContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#pubAnnounceBox.
    def visitPubAnnounceBox(self, ctx:SMCDELParser.PubAnnounceBoxContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#prop.
    def visitProp(self, ctx:SMCDELParser.PropContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#state_law.
    def visitState_law(self, ctx:SMCDELParser.State_lawContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#observation.
    def visitObservation(self, ctx:SMCDELParser.ObservationContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#observations.
    def visitObservations(self, ctx:SMCDELParser.ObservationsContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#whereQuery.
    def visitWhereQuery(self, ctx:SMCDELParser.WhereQueryContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#trueQuery.
    def visitTrueQuery(self, ctx:SMCDELParser.TrueQueryContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#validQuery.
    def visitValidQuery(self, ctx:SMCDELParser.ValidQueryContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#queries.
    def visitQueries(self, ctx:SMCDELParser.QueriesContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by SMCDELParser#model.
    def visitModel(self, ctx:SMCDELParser.ModelContext):
        return self.visitChildren(ctx)



del SMCDELParser