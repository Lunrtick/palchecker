package EpistemicModelChecker;

import java.util.List;

public class Formula {
    public FormulaType type;
    public String proposition;
    public List<Formula> children;
    public String agent;
    public Formula announcement;

    public Formula(FormulaType type, List<Formula> children) {
        this(type, children, null, null, null);
    }

    public Formula(FormulaType type, List<Formula> children, String agent) {
        this(type, children, null, agent, null);
    }

    public Formula(FormulaType type, List<Formula> children, Formula announcement) {
        this(type, children, null, null, announcement);
    }

    public Formula(FormulaType type, String proposition) {
        this(type, null, proposition, null, null);
    }

    public Formula(FormulaType type) {
        this(type, null, null, null, null);
    }

    public Formula(FormulaType type, List<Formula> children, String proposition, String agent, Formula announcement) {
        this.type = type;
        this.proposition = proposition;
        this.children = children;
        this.agent = agent;
        this.announcement = announcement;
    }

    @Override
    public String toString() {
        switch (type) {
            case Top -> {
                return "⊤";
            }
            case Bot -> {
                return "⊥";
            }
            case And -> {
                return "(%s)".formatted(String.join(" ∧ ", children.stream().map(c -> c.toString()).toList()));
            }
            case Or -> {
                return "(%s)".formatted(String.join(" ∨ ", children.stream().map(c -> c.toString()).toList()));
            }
            case Not -> {
                return "¬(%s)".formatted(children.get(0).toString());
            }
            case Proposition -> {
                return proposition.toString();
            }
            case KnowsThat -> {
                return "( %s knows that %s )".formatted(agent.toString(), children.get(0).toString());
            }
            case KnowsWhether -> {
                return "( %s knows whether %s )".formatted(agent.toString(), children.get(0).toString());
            }
            case PubAnnounceBox -> {
                return "[! %s ]%s".formatted(announcement.toString(), children.get(0).toString());
            }
            case PubAnnounceDiamond -> {
                return "<! %s >%s".formatted(announcement.toString(), children.get(0).toString());
            }
            default -> {
                throw new RuntimeException("Unknown EpistemicModelChecker.Formula");
            }
        }
    }
}
