package EpistemicModelChecker;

import java.util.*;
import java.util.stream.Stream;

public class KripkeWorld implements World<String> {
    private int id;
    private final Set<String> truePropositions;
    private final Map<String, List<World<String>>> relations = new HashMap<>();
    private KripkeModel model;

    public KripkeWorld(int id, Set<String> truePropositions, Set<String> agents) {
        this.id = id;
        this.truePropositions = truePropositions;

        for (String agent : agents) {
            relations.put(agent, new ArrayList<>());
        }
    }

    @Override
    public String toString() {
        return "W{" +
                String.join(",", truePropositions.stream().toList())
                + '}';
    }

    @Override
    public KripkeModel getModel() {
        return model;
    }

    @Override
    public void setModel(KripkeModel m) {
        model = m;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean isTrueHere(String proposition) {
        return truePropositions.contains(proposition);
    }

    @Override
    public List<World<String>> accessibleWorlds(String agent) {
        return relations.get(agent);
    }

    @Override
    public Set<String> getTruePropositions() {
        return truePropositions;
    }
}
