package EpistemicModelChecker;

import java.util.List;
import java.util.Set;

public interface World<T> {
    KripkeModel getModel();
    void setModel(KripkeModel m);
    int getId();

    boolean isTrueHere(T proposition);

    List<World<T>> accessibleWorlds(T agent);

    Set<T> getTruePropositions();
}
