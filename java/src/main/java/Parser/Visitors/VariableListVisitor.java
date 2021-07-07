package Parser.Visitors;

import EpistemicModelChecker.Formula;
import EpistemicModelChecker.FormulaType;
import EpistemicModelChecker.SMCDELModel;
import Parser.SMCDELBaseVisitor;
import Parser.SMCDELParser;
import Parser.SMCDELVisitor;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class VariableListVisitor extends SMCDELBaseVisitor<List<String>> implements SMCDELVisitor<List<String>> {
    @Override
    public List<String> visitVariable_list(SMCDELParser.Variable_listContext ctx) {
        return ctx.vs.stream().map(Token::getText).toList();
    }
}
