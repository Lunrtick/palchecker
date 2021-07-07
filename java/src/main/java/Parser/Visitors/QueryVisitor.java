package Parser.Visitors;

import EpistemicModelChecker.Query;
import EpistemicModelChecker.QueryType;
import Parser.SMCDELBaseVisitor;
import Parser.SMCDELParser;
import Parser.SMCDELVisitor;
import org.antlr.v4.runtime.Token;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class QueryVisitor extends SMCDELBaseVisitor<Query> implements SMCDELVisitor<Query> {
    @Override
    public Query visitWhereQuery(SMCDELParser.WhereQueryContext ctx) {
        return new Query(QueryType.WHERE, new FormulaVisitor().visit(ctx.form));
    }

    @Override
    public Query visitValidQuery(SMCDELParser.ValidQueryContext ctx) {
        return new Query(QueryType.VALID, new FormulaVisitor().visit(ctx.form));
    }

    @Override
    public Query visitTrueQuery(SMCDELParser.TrueQueryContext ctx) {
        return new Query(QueryType.TRUE, new FormulaVisitor().visit(ctx.form), ctx.props.vs.stream().map(Token::getText).collect(Collectors.toSet()));
    }
}
