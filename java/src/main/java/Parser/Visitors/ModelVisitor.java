package Parser.Visitors;

import EpistemicModelChecker.Formula;
import EpistemicModelChecker.FormulaType;
import EpistemicModelChecker.Query;
import EpistemicModelChecker.SMCDELModel;
import Parser.SMCDELBaseVisitor;
import Parser.SMCDELParser;
import Parser.SMCDELVisitor;
import org.javatuples.Quartet;

import java.util.*;

public class ModelVisitor extends SMCDELBaseVisitor<SMCDELModel> implements SMCDELVisitor<SMCDELModel> {
    @Override
    public SMCDELModel visitModel(SMCDELParser.ModelContext ctx) {
        var variables = new PropositionVisitor().visitPropositions(ctx.propositions());
        var state_law = new FormulaVisitor().visitState_law(ctx.state_law());
        var obs = new ObservationVisitor().visitObservations(ctx.observations());
        var queries = new QueryListVisitor().visitQueries(ctx.queries());
        return new SMCDELModel(variables, state_law, obs, queries);
    }
}
