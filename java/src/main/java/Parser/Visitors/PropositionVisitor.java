package Parser.Visitors;

import Parser.SMCDELBaseVisitor;
import Parser.SMCDELParser;
import Parser.SMCDELVisitor;

import java.util.List;

public class PropositionVisitor extends SMCDELBaseVisitor<List<String>> implements SMCDELVisitor<List<String>> {
    @Override
    public List<String> visitPropositions(SMCDELParser.PropositionsContext ctx) {
        return super.visitPropositions(ctx);
    }
}
