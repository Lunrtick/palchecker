package Parser.Visitors;

import EpistemicModelChecker.Formula;
import EpistemicModelChecker.FormulaType;
import EpistemicModelChecker.Query;
import Parser.SMCDELBaseVisitor;
import Parser.SMCDELParser;
import Parser.SMCDELVisitor;
import org.javatuples.Quartet;

import java.util.List;
import java.util.Map;

public class ModelVisitor extends SMCDELBaseVisitor<Quartet<List<String>,Formula, Map<String, List<String>>, List<Query>>> implements SMCDELVisitor<Quartet<List<String>,Formula, Map<String, List<String>>, List<Query>>> {
    @Override
    public Quartet<List<String>, Formula, Map<String, List<String>>, List<Query>> visitModel(SMCDELParser.ModelContext ctx) {
        return super.visitModel(ctx);
    }
}
