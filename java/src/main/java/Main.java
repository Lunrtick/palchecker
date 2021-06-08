import EpistemicModelChecker.EpistemicModelChecker;
import EpistemicModelChecker.KripkeModel;
import Examples.MuddyChildren;
import Parser.SMCDELBaseVisitor;
import Parser.SMCDELLexer;
import Parser.SMCDELParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class Main {
    public static void parseInput() {
        try {
            CharStream inputStream = CharStreams.fromFileName("asdasd");
            var lexer = new SMCDELLexer(inputStream);
            var tokens = new CommonTokenStream(lexer);
            var parser = new SMCDELParser(tokens);

            var visitor = new SMCDELBaseVisitor<>();
//            return visitor.visit(parser.model());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        var start = System.currentTimeMillis();
        EpistemicModelChecker modelChecker = new EpistemicModelChecker();
        var mc = new MuddyChildren(Integer.parseInt(args[0]));
        KripkeModel model = modelChecker.generateModel(mc.variables, mc.stateLaw, mc.obs);
        System.out.printf("Generated Model in %f seconds%n", (System.currentTimeMillis() - start) / 1_000.0);
        System.out.printf("Worlds: %d%n", model.worlds.size());
        var generated = System.currentTimeMillis();
        modelChecker.solveModel(model, mc.queries);
        System.out.printf("Solved in %f seconds%n", (System.currentTimeMillis() - generated) / 1_000.0);
        System.out.printf("Total: %f seconds%n", (System.currentTimeMillis() - start) / 1_000.0);
    }
}
