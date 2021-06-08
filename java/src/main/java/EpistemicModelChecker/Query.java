package EpistemicModelChecker;

import java.util.Set;

public class Query {
    public QueryType type;
    public Formula formula;
    public Set<String> propositions;

    public Query(QueryType type, Formula formula) {
        this(type, formula, null);
    }

    public Query(QueryType type, Formula formula, Set<String> propositions) {
        this.type = type;
        this.formula = formula;
        this.propositions = propositions;
    }
}
