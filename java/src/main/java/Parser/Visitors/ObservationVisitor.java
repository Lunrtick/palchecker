package Parser.Visitors;

import EpistemicModelChecker.Formula;
import Parser.SMCDELBaseVisitor;
import Parser.SMCDELParser;
import Parser.SMCDELVisitor;
import org.antlr.v4.runtime.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObservationVisitor extends SMCDELBaseVisitor<Map<String, List<String>>> implements SMCDELVisitor<Map<String, List<String>>> {
    @Override
    public Map<String, List<String>> visitObservations(SMCDELParser.ObservationsContext ctx) {
        var m = new HashMap<String, List<String>>();
        ctx.obs.forEach(c -> {
            m.put(c.agent.getText(), c.vs.vs.stream().map(Token::getText).toList());
        });
        return m;
    }
}
