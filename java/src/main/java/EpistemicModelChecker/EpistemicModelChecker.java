package EpistemicModelChecker;

import com.google.common.collect.Sets;
import com.koloboke.collect.set.hash.HashIntSet;
import com.koloboke.collect.set.hash.HashIntSets;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class EpistemicModelChecker {
    public int maxDepth = 0;

    public <V> List<Set<V>> powerset(@org.jetbrains.annotations.NotNull List<V> s) {
        int size = 1 << s.size();
        List<Set<V>> returned = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Set<V> ss = new HashSet<>();
            int j = 0;
            for (V el : s) {
                if ((i & (1 << j)) != 0) {
                    ss.add(el);
                }
                j++;
            }
            returned.add(ss);
        }
        return returned;
    }

    public boolean valid(Formula f, Set<String> truePropositions) {
        switch (f.type) {
            case Top -> {
                return true;
            }
            case Bot -> {
                return false;
            }
            case And -> {
                for (Formula child : f.children) {
                    if (!valid(child, truePropositions)) {
                        return false;
                    }
                }
                return true;
            }
            case Or -> {
                for (Formula child : f.children) {
                    if (valid(child, truePropositions)) {
                        return true;
                    }
                }
                return false;
            }
            case Not -> {
                return !valid(f.children.get(0), truePropositions);
            }
            case Proposition -> {
                return truePropositions.contains(f.proposition);
            }
            default -> throw new RuntimeException("Unknown EpistemicModelChecker.Formula");
        }
    }

    public boolean trueAtWorld(Formula f, World<String> world, HashIntSet eliminatedWorlds, int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
        switch (f.type) {
            case Top -> {
                return true;
            }
            case Bot -> {
                return false;
            }
            case And -> {
                for (Formula child : f.children) {
                    if (!trueAtWorld(child, world, eliminatedWorlds, depth + 1)) {
                        return false;
                    }
                }
                return true;
            }
            case Or -> {
                for (Formula child : f.children) {
                    if (trueAtWorld(child, world, eliminatedWorlds, depth + 1)) {
                        return true;
                    }
                }
                return false;
            }
            case Not -> {
                return !trueAtWorld(f.children.get(0), world, eliminatedWorlds, depth + 1);
            }
            case Proposition -> {
                return world.isTrueHere(f.proposition);
            }
            case KnowsThat -> {
                return trueAtWorldKnowsThat(f.children.get(0), world, f.agent, eliminatedWorlds, depth + 1);
            }
            case KnowsWhether -> {
                return trueAtWorldKnowsThat(f.children.get(0), world, f.agent, eliminatedWorlds, depth + 1) ||
                        trueAtWorldKnowsThat(
                                new Formula(FormulaType.Not, f.children),
                                world,
                                f.agent,
                                eliminatedWorlds,
                                depth + 1
                        );
            }
            case PubAnnounceBox -> {
                return !trueAtWorldPublicAnnouncement(f.announcement, world, new Formula(FormulaType.Not, f.children), eliminatedWorlds, depth + 1);
            }
            case PubAnnounceDiamond -> {
                return trueAtWorldPublicAnnouncement(f.announcement, world, f.children.get(0), eliminatedWorlds, depth + 1);
            }
            default -> throw new RuntimeException("Unknown EpistemicModelChecker.Formula");
        }
    }

    public boolean trueAtWorldKnowsThat(Formula f, World<String> world, String agent, HashIntSet eliminatedWorlds, int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
        boolean moreThanOne = false;
        var accessibleWorlds = world.accessibleWorlds(agent);
        if (accessibleWorlds == null) {
            return false;
        }
        for (World<String> w : accessibleWorlds) {
            if (eliminatedWorlds.contains(w.getId())) {
                continue;
            }

            boolean v = trueAtWorld(f, w, eliminatedWorlds, depth + 1);
            moreThanOne = true;
            if (!v) {
                return false;
            }
        }
        return moreThanOne;
    }

    public boolean trueAtWorldPublicAnnouncement(Formula announcement, World<String> world, Formula f, HashIntSet eliminatedWorlds, int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
        if (eliminatedWorlds.contains(world.getId()) || !trueAtWorld(announcement, world, eliminatedWorlds, depth + 1)) {
            return false;
        }
        var newEliminations = HashIntSets.newUpdatableSet();
        for (World<String> w : world.getModel().worlds) {
            if (eliminatedWorlds.contains(w.getId())) {
                continue;
            }
            if (!trueAtWorld(announcement, w, eliminatedWorlds, depth + 1)) {
                newEliminations.add(w.getId());
            }
        }
        newEliminations.addAll(eliminatedWorlds);

        return trueAtWorld(f, world, newEliminations, depth + 1);
    }

    public void solveModel(KripkeModel model, List<Query> queries) {
        for (Query q : queries) {
            maxDepth = 0;
            switch (q.type) {
                case WHERE -> {
                    List<World<String>> worlds = model.worlds
                            .stream()
                            .filter(w -> trueAtWorld(q.formula, w, HashIntSets.newUpdatableSet(), 1))
                            .toList();
                    System.out.println(String.join(",", worlds.stream().map(Object::toString).toList()));
                }
                case VALID -> {
                    System.out.printf("Is %s valid?%n", q.formula);
                    boolean result = model.worlds
                            .parallelStream()
                            .allMatch(w -> trueAtWorld(q.formula, w, HashIntSets.newUpdatableSet(), 1));
                    if (result) {
                        System.out.println("Valid!");
                    } else {
                        System.out.println("Not Valid...");
                    }
                }
                case TRUE -> {
                    var world = model.worlds
                            .stream()
                            .dropWhile(w -> !w.getTruePropositions().equals(q.propositions))
                            .findFirst();
                    if (world.isEmpty()) {
                        throw new RuntimeException("No matching world found.");
                    }

                }
            }
            System.out.println("Max Depth: %d".formatted(maxDepth));
        }
    }

    private <T> Set<T> difference(Set<T> a, Set<T> b) {
        return a.stream().filter(v -> !b.contains(v)).collect(Collectors.toSet());
    }
    public KripkeModel generateModel(List<String> variables, Formula stateLaw, Map<String, List<String>> obs) {
        AtomicInteger idx = new AtomicInteger(1);
        AtomicInteger varId = new AtomicInteger(1);
        Map<String, Integer> variable_mapping = variables.stream()
                .collect(Collectors.toMap(
                        (v) -> v,
                        (v) -> varId.getAndIncrement())
                );

        List<World<String>> worlds = Sets.powerSet(Sets.newHashSet(variables))
                .stream()
                .map(props -> new KripkeWorld(idx.getAndIncrement(), props, obs.keySet()))
                .filter(w -> valid(stateLaw, w.getTruePropositions()))
                .collect(Collectors.toList());

        KripkeModel model = new KripkeModel(worlds);

        System.out.println("Worlds Generated");

        Set<String> allVariables = new HashSet<>(variables);
        model.worlds.parallelStream().forEach(w -> obs.forEach((agent, obs_list) -> {
            Set<String> myObservations = new HashSet<>(obs_list);
            var unobserved = Sets.difference(allVariables, myObservations);
            var observablyTrue = Sets.difference(w.getTruePropositions(), unobserved);
            for (var w1 : model.worlds) {
                var d = Sets.difference(w1.getTruePropositions(), unobserved);
                if (observablyTrue.equals(d)) {
                    var a = w.accessibleWorlds(agent);
                    a.add(w1);
                }
            }
        }));

        System.out.println("Relationships Assigned!");
        return model;
    }
}
