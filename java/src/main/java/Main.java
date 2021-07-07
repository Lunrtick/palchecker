import EpistemicModelChecker.EpistemicModelChecker;
import EpistemicModelChecker.KripkeModel;
import EpistemicModelChecker.SMCDELModel;
import Examples.MuddyChildren;
import Parser.SMCDELBaseVisitor;
import Parser.SMCDELLexer;
import Parser.SMCDELParser;
import Parser.Visitors.ModelVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class Main {
    public static SMCDELModel parseInput(String filename) throws IOException {
        try {
            CharStream inputStream = CharStreams.fromFileName(filename);
            var lexer = new SMCDELLexer(inputStream);
            var tokens = new CommonTokenStream(lexer);
            var parser = new SMCDELParser(tokens);

            var visitor = new ModelVisitor();
            return visitor.visit(parser.model());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) throws IOException {
        SMCDELModel mc = args.length < 3
                ? new MuddyChildren(Integer.parseInt(args[0]))
                : parseInput(args[2]);
        if (mc == null) {
            throw new RuntimeException("Parsing Failed!");
        }
        var start = System.currentTimeMillis();
        EpistemicModelChecker modelChecker = new EpistemicModelChecker();
        KripkeModel model = modelChecker.generateModel(mc.getVariables(), mc.getStateLaw(), mc.getObs());
        if (Integer.parseInt(args[1]) != 0) {
            return;
        }
        System.out.printf("Generated Model in %f seconds%n", (System.currentTimeMillis() - start) / 1_000.0);
        System.out.printf("Worlds: %d%n", model.worlds.size());
        var generated = System.currentTimeMillis();
        modelChecker.solveModel(model, mc.getQueries());
        System.out.printf("Solved in %f seconds%n", (System.currentTimeMillis() - generated) / 1_000.0);
        System.out.printf("Total: %f seconds%n", (System.currentTimeMillis() - start) / 1_000.0);
    }
}
