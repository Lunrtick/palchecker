package Parser.Visitors;

import EpistemicModelChecker.Query;
import Parser.SMCDELBaseVisitor;
import Parser.SMCDELParser;
import Parser.SMCDELVisitor;

import java.util.List;

public class QueryListVisitor extends SMCDELBaseVisitor<List<Query>> implements SMCDELVisitor<List<Query>> {
    @Override
    public List<Query> visitQueries(SMCDELParser.QueriesContext ctx) {
        return ctx.q.stream().map(q -> new QueryVisitor().visit(q)).toList();
    }
}
