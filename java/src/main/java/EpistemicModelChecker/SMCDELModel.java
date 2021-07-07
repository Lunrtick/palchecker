package EpistemicModelChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SMCDELModel {
    public List<String> getVariables() {
        return variables;
    }

    public Formula getStateLaw() {
        return stateLaw;
    }

    public Map<String, List<String>> getObs() {
        return obs;
    }

    public List<Query> getQueries() {
        return queries;
    }

    private List<String> variables;
    private Formula stateLaw;
    private Map<String, List<String>> obs;
    private List<Query> queries;

    public SMCDELModel(List<String> variables, Formula stateLaw, Map<String, List<String>> obs, List<Query> queries) {
        this.variables = variables;
        this.stateLaw = stateLaw;
        this.obs = obs;
        this.queries = queries;
    }

    public SMCDELModel() {

    }
}
