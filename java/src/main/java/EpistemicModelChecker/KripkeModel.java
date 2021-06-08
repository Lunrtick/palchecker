package EpistemicModelChecker;

import java.util.List;

public class KripkeModel {
    public List<World<String>> worlds;

    public KripkeModel(List<World<String>> worlds) {
        this.worlds = worlds;
        for (World<String> w : worlds) {
            w.setModel(this);
        }
    }
}
