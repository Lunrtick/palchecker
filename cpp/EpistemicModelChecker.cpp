//
// Created by gabe on 14/05/2021.
//
#include "EpistemicModelChecker.h"

#include <utility>
#include "fmt/core.h"
#include "fmt/ranges.h"
#include <tuple>
#include <ranges>
#include <atomic>
#include <algorithm>
#include <iostream>
using namespace EMC;

Formula::Formula(FormulaType fType) : type(fType), proposition(0), agent(0) {}

Formula::Formula(FormulaType fType, Variable_t prop) : type(fType), proposition(prop), agent(0) {}

Formula::Formula(FormulaType fType, std::vector<Formula> cs) : type(fType), proposition(0), agent(0),
                                                               children(std::move(cs)) {}

Formula::Formula(FormulaType fType, std::vector<Formula> cs, Variable_t a) : type(fType), proposition(0), agent(a),
                                                                             children(std::move(cs)) {}

Formula::Formula(FormulaType fType, std::vector<Formula> cs, std::shared_ptr<Formula> announce) : type(fType),
                                                                                                  proposition(0),
                                                                                                  agent(0),
                                                                                                  children(std::move(cs)),
                                                                                                  announcement(std::move(announce)) {}

std::string join(const std::vector<Formula> &fs, const std::string &delim)
{
    std::string res;
    for (int i = 0; i < fs.size(); ++i) {
        res += fs[i].toString();
        if (i != fs.size() - 1) {
            res += delim;
        }
    }
    return res;
}

std::string Formula::toString() const {
    std::ostringstream ss;
    switch (type) {
        case Top:
            return "⊤";
        case Bot:
            return "⊥";
        case And:
            return fmt::format("({})", join(children, " ∧ "));
        case Or:
            return fmt::format("({})", join(children, " ∨ "));
        case Not:
            return fmt::format("¬({})", children[0].toString());
        case Proposition:
            return std::to_string(proposition);
        case KnowsThat:
            return fmt::format("( {} knows that {} )", agent, children[0].toString());
        case KnowsWhether:
            return fmt::format("( {} knows whether {} )", agent, children[0].toString());
        case PubAnnounceBox:
            return fmt::format("[! {} ]{}", announcement->toString(), children[0].toString());
        case PubAnnounceDiamond:
            return fmt::format("<! {} >{}", announcement->toString(), children[0].toString());
        default:
            throw std::runtime_error("Unknown Formula");
    }
}

std::string KripkeWorld::toString() const {
    std::ostringstream ss;
    ss << "W{";
    uint x = 1;
    for (auto &&p : truePropositions) {
        if (x++ != truePropositions.size()) {
            ss << p << ",";
        } else {
            ss << p;
        }
    }
    ss << "}";
    return ss.str();
}

KripkeWorld::KripkeWorld(int id, std::set<Variable_t> props,
                         const std::vector<Variable_t>& agents) : id(id), truePropositions(std::move(props)), relations(std::map<Variable_t, std::vector<std::reference_wrapper<KripkeWorld>>>()) {
    for (auto &&a: agents) {
        relations.emplace(a, std::vector<std::reference_wrapper<KripkeWorld>>());
    }
}

std::vector<std::reference_wrapper<KripkeWorld>> &KripkeWorld::accessibleWorlds(Variable_t agent) {
    return relations[agent];
}


std::string KripkeModel::toString() {
    std::ostringstream ss;
    ss << "Model: \n";
    for (auto &&w : worlds) {
        ss << w.toString() << '\n';
    }
    return ss.str();
}

KripkeModel::KripkeModel(std::vector<KripkeWorld> ws) : worlds(std::move(ws)) {

}

std::vector<Formula> buildAnnouncements(const std::vector<Variable_t>& vars, int depth) {
    if (depth == vars.size()) {
        std::vector<Formula> forms;
        std::vector<Formula> props;
        for (auto&& v : vars) {
            props.emplace_back(Formula(Proposition, v));
        }
        forms.emplace_back(Formula(And, std::move(props)));
        return forms;
    } else {
        std::vector<Formula> knows;
        for (auto&& v : vars) {
            knows.emplace_back(Formula(KnowsWhether, {Formula(Proposition, v)}, v));
        }
        std::vector<Formula> ret;
        std::vector<Formula> notted;
        notted.emplace_back(Formula(Or, knows));
        ret.emplace_back(Formula(PubAnnounceBox, buildAnnouncements(vars, depth + 1), std::make_shared<Formula>(Not, notted)));
        return ret;
    }
}

Query buildQuery(const std::vector<Variable_t>& vars) {
    std::vector<Formula> disjunction;
    for (auto&& v : vars) {
        disjunction.emplace_back(Formula(Proposition, v));
    }
    return Query(VALID,  Formula(PubAnnounceBox, buildAnnouncements(vars, 1), std::make_shared<Formula>(Or, disjunction)));
}

ModelBits_t EMC::makeMuddyChildren(int n) {
    std::vector<Variable_t> variables;
    for (int i = 1; i <= n; ++i) {
        variables.emplace_back(i);
    }
    auto state_law = Formula(Top);
    std::unordered_map<Variable_t, std::vector<Variable_t>> obs;
    for (auto&& v : variables) {
        std::vector<Variable_t> my_obs;
        std::copy_if(variables.begin(), variables.end(), std::back_inserter(my_obs), [&v](Variable_t o){ return o != v;});
        obs.emplace(v, my_obs);
    }
    std::vector<Query> queries;
    queries.emplace_back(buildQuery(variables));
    return {
        variables,
        state_law,
        obs,
        queries
    };
}

bool valid(const Formula& f, const std::set<Variable_t>& true_propositions) {
    switch (f.type) {

        case Top:
            return true;
        case Bot:
            return false;
        case And:
            return std::ranges::all_of(f.children.begin(), f.children.end(), [&true_propositions](const Formula& child) { return valid(child, true_propositions); });
        case Or:
            return std::ranges::any_of(f.children.begin(), f.children.end(), [&true_propositions](const Formula& child) { return valid(child, true_propositions); });
        case Not:
            return !valid(f.children[0], true_propositions);
        case Proposition:
            return true_propositions.contains(f.proposition);
        default:
            throw std::runtime_error("Invalid Formula type");
    }
}

std::vector<std::set<Variable_t>> powerset(const std::vector<Variable_t>& s) {
    int size = 1 << s.size();
    std::vector<std::set<Variable_t>> returned;
    returned.reserve(size);
    for (int i = 0; i < size; i++) {
        std::set<Variable_t> ss;
        int j = 0;
        for (const auto& el : s) {
            if ((i & (1 << j)) != 0) {
                ss.emplace(el);
            }
            j++;
        }
        returned.emplace_back(ss);
    }
    return returned;
}

KripkeModel EMC::generateModel(std::vector<Variable_t> &variables, Formula &state_law,
                          std::unordered_map<Variable_t, std::vector<Variable_t>> &obs) {
    std::atomic_int idx;
    std::vector<Variable_t> agents(obs.size());
    for (const auto& ob : obs) {
        agents.emplace_back(std::get<0>(ob));
    }
    std::vector<KripkeWorld> worlds;
    worlds.reserve(1 << variables.size());
    for (const auto& s : powerset(variables)) {
        KripkeWorld w(idx.fetch_add(1), s, agents);
        if (valid(state_law, w.truePropositions)) {
            worlds.emplace_back(std::move(w));
        }
    }
    worlds.shrink_to_fit();
    KripkeModel model(std::move(worlds));
    std::cout << "Worlds Generated" << std::endl;

    std::set<Variable_t> all_variables(variables.begin(), variables.end());

#pragma omp parallel for schedule(static) shared(all_variables, model, obs) default(none)
    for (int i = 0; i < model.worlds.size(); ++i) {
        for (const auto& [agent, obs_list] : obs) {
            std::set<Variable_t> unobserved, observably_true;
            std::set<Variable_t> my_observations(obs_list.begin(), obs_list.end());

            // unobserved = all_variables - my_observations
            std::set_difference(all_variables.begin(), all_variables.end(), my_observations.begin(),
                                my_observations.end(), std::inserter(unobserved, unobserved.begin()));
            // observably_true = world.true_propositions - unobserved
            std::set_difference(model.worlds[i].truePropositions.begin(), model.worlds[i].truePropositions.end() ,unobserved.begin(),
                                unobserved.end(), std::inserter(observably_true, observably_true.begin()));
            for (auto& w1 : model.worlds) {
                std::set<Variable_t> d;
                // d = w1.truePropositions - unobserved
                std::set_difference(w1.truePropositions.begin(), w1.truePropositions.end(), unobserved.begin(), unobserved.end(), std::inserter(d, d.begin()));
                if (observably_true.size() == d.size() && std::equal(observably_true.begin(), observably_true.end(), d.begin())) {
                    model.worlds[i].accessibleWorlds(agent).emplace_back(w1);
                }
            }
        }
    }
    std::cout << "Relationships Assigned" << std::endl;
    return model;
}

bool EMC::trueAtWorld(const KripkeModel& model, const Formula &f, const KripkeWorld &world, const std::set<int> &eliminatedWorlds, const int depth) {
    switch (f.type) {
        case Top:
            return true;
        case Bot:
            return false;
        case And:
            return std::ranges::all_of(f.children.begin(), f.children.end(), [&model, &world, &eliminatedWorlds, &depth](const Formula& child) { return trueAtWorld(model, child, world, eliminatedWorlds, depth + 1); });
        case Or:
            return std::ranges::any_of(f.children.begin(), f.children.end(), [&model, &world, &eliminatedWorlds, &depth](const Formula& child) { return trueAtWorld(model, child, world, eliminatedWorlds, depth + 1); });
        case Not:
            return !trueAtWorld(model, f.children[0], world, eliminatedWorlds, depth + 1);
        case Proposition:
            return world.truePropositions.contains(f.proposition);
        case KnowsThat:
            return trueAtWorldKnowsThat(model, f.children[0], world, f.agent, eliminatedWorlds, depth + 1);
        case KnowsWhether:
            return trueAtWorldKnowsThat(model, f.children[0], world, f.agent, eliminatedWorlds, depth + 1) ||
            trueAtWorldKnowsThat(model, Formula(Not, {f.children[0]}), world, f.agent, eliminatedWorlds, depth + 1);
        case PubAnnounceBox:
            return !trueAtWorldPublicAnnouncement(model, *f.announcement, world, Formula(Not, {f.children[0]}), eliminatedWorlds, depth + 1);
        case PubAnnounceDiamond:
            return trueAtWorldPublicAnnouncement(model, *f.announcement, world, f.children[0], eliminatedWorlds, depth + 1);
        default:
            throw std::runtime_error("Invalid Formula type");
    }
}

std::string printWorlds(const std::vector<std::reference_wrapper<KripkeWorld>>& worlds) {
    std::ostringstream ss;
    uint x = 1;
    ss << "[";
    for (auto &&w : worlds) {
        if (x++ != worlds.size()) {
            ss << w.get().toString() << ',';
        } else {
            ss << w.get().toString();
        }
    }
    ss << "]";
    return ss.str();
}

bool EMC::trueAtWorldKnowsThat(const KripkeModel& model, const Formula& f, const KripkeWorld &world, Variable_t agent, const std::set<int> &eliminatedWorlds, const int depth) {
    bool more_than_one = false;
    auto worlds = world.relations.at(agent);

    for (const auto &w : worlds) {
        if (eliminatedWorlds.contains(w.get().id)) {
            continue;
        }

        bool v = trueAtWorld(model, f, w, eliminatedWorlds, depth + 1);
        more_than_one = true;
        if (!v) {
            return false;
        }
    }
    return more_than_one;
}

bool EMC::trueAtWorldPublicAnnouncement(const KripkeModel& model, const Formula& announcement, const KripkeWorld& world, const Formula& f, const std::set<int> &eliminatedWorlds, int depth) {
    if (eliminatedWorlds.contains(world.id) || !trueAtWorld(model, announcement, world, eliminatedWorlds, depth + 1)) {
        return false;
    }

    std::set<int> newEliminations;
    for (const auto& w : model.worlds) {
        if (eliminatedWorlds.contains(w.id)) {
            continue;
        }

        if (!trueAtWorld(model, announcement, w, eliminatedWorlds, depth + 1)) {
            newEliminations.emplace(w.id);
        }
    }
    newEliminations.insert(eliminatedWorlds.begin(), eliminatedWorlds.end());

    return trueAtWorld(model, f, world, newEliminations, depth + 1);
}
void EMC::solveModel(const KripkeModel& model, const std::vector<Query>& queries) {
    for (auto&& q: queries) {
        switch (q.type) {
            case EMC::WHERE: {
                std::vector<int> worlds;
                for (int i = 0; i < model.worlds.size(); ++i) {
                    if (trueAtWorld(model, q.formula, model.worlds[i], std::set<int>(), 1)) {
                        worlds.push_back(i);
                    }
                }

                for (auto&& w: worlds) {
                    fmt::print("{}", model.worlds[w].toString());
                }
            }
                break;
            case EMC::VALID: {
                fmt::print("Is {} valid?\n", q.formula.toString());
                bool valid = true;
#pragma omp parallel for default(none) shared(model, q, valid) schedule(dynamic)
                for (int i = 0; i < model.worlds.size(); ++i) {
                    if (!trueAtWorld(model, q.formula, model.worlds[i], std::set<int>(), 1)) {
#pragma omp cancellation point for
                        valid = false;
#pragma omp cancel for
                    }
                }

                if (valid) {
                    fmt::print("Valid!");
                } else {
                    fmt::print("not valid...");
                }
            }
            break;
        }
    }
}

Query::Query(QueryType t, Formula f, std::set<Variable_t> props) : type(t), formula(std::move(f)), propositions(std::move(props)) {}

Query::Query(QueryType t, Formula f) : type(t), formula(std::move(f)) {}
