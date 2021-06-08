package Examples;

import EpistemicModelChecker.*;

import java.util.*;
import java.util.stream.IntStream;

public class MuddyChildren {
    public List<String> variables;
    public Formula stateLaw;
    public Map<String, List<String>> obs;
    public List<Query> queries;

    private List<Formula> buildAnnouncements(List<String> vars, int depth) {
        if (depth == vars.size()) {
            List<Formula> forms = new ArrayList<>(vars.size());
            forms.add(new Formula(
                        FormulaType.And,
                        vars
                        .stream()
                        .map(v -> new Formula(FormulaType.Proposition, v))
                        .toList()
            ));
            return forms;
        } else {
            List<Formula> knows = vars
                    .stream()
                    .map(v -> new Formula(
                            FormulaType.KnowsWhether,
                            Collections.singletonList(new Formula(FormulaType.Proposition, v)),
                            v
                            )
                    )
                    .toList();
            return Collections.singletonList(
                    new Formula(
                            FormulaType.PubAnnounceBox,
                            buildAnnouncements(vars, depth + 1),
                            new Formula(FormulaType.Not, Collections.singletonList(new Formula(FormulaType.Or, knows)))
                    )
            );
        }
    }

    private Query buildQuery(List<String> vars) {
        List<Formula> disjunction = new ArrayList<>(vars.size());
        for (String v : vars) {
            disjunction.add(new Formula(FormulaType.Proposition, v));
        }
        Formula or = new Formula(FormulaType.Or, disjunction);

        Formula f = new Formula(FormulaType.PubAnnounceBox, buildAnnouncements(vars, 1), or);
        return new Query(QueryType.VALID, f);
    }
    public MuddyChildren(int n) {
        variables = new ArrayList<>();
        for (int v : IntStream.rangeClosed(1, n).toArray()) {
            variables.add(String.valueOf(v));
        }
        stateLaw = new Formula(FormulaType.Top);
        obs = new HashMap<>();
        for (String v : variables) {
            obs.put(v, variables.stream().filter(val -> val != v).toList());
        }
        queries = new ArrayList<>();
        queries.add(buildQuery(variables));
    }
}
