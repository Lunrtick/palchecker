package Parser.Visitors;

import EpistemicModelChecker.Formula;
import Parser.SMCDELBaseVisitor;
import Parser.SMCDELParser;
import Parser.SMCDELVisitor;

import java.util.List;

public class FormulaListVisitor extends SMCDELBaseVisitor<List<Formula>> implements SMCDELVisitor<List<Formula>> {
    @Override
    public List<Formula> visitFormula_list(SMCDELParser.Formula_listContext ctx) {
        return ctx.forms.stream().map(f -> new FormulaVisitor().visit(f)).toList();
    }
}
