//
// Created by gabe on 09/05/2021.
//

#ifndef MAYBETHEFASTEST_EPISTEMICMODELCHECKER_H
#define MAYBETHEFASTEST_EPISTEMICMODELCHECKER_H
#include <string>
#include <vector>
#include <memory>
#include <set>
#include <hash_map>
#include <set>
#include <map>
#include <sstream>

namespace EMC {
    typedef int Variable_t;
    enum FormulaType {
        Top,
        Bot,
        And,
        Or,
        Not,
        Proposition,
        KnowsThat,
        KnowsWhether,
        PubAnnounceBox,
        PubAnnounceDiamond
    };

    enum QueryType {
        WHERE,
        TRUE,
        VALID
    };

    class Formula {
    public:
        FormulaType type;
        Variable_t proposition{};
        std::vector<Formula> children;
        Variable_t agent{};
        std::shared_ptr<Formula> announcement;

        explicit Formula(FormulaType fType);
        Formula(FormulaType fType, Variable_t prop);
        Formula(FormulaType fType, std::vector<Formula> cs);
        Formula(FormulaType fType, std::vector<Formula> cs, std::shared_ptr<Formula> announce);
        Formula(FormulaType fType, std::vector<Formula> cs, Variable_t a);

        std::string toString() const;
        Formula(const Formula& rhs) = default;
        Formula(Formula &&rhs) noexcept = default;
    };

    class Query {
    public:
        QueryType type;
        Formula formula;
        std::set<Variable_t> propositions;

        Query(QueryType t, Formula f);
        Query(QueryType t, Formula f, std::set<Variable_t> props);
    };

    class KripkeModel;
    class KripkeWorld {
    public:
        int id;
        std::set<Variable_t> truePropositions;
        std::map<Variable_t, std::vector<std::reference_wrapper<KripkeWorld>>> relations;

        std::string toString() const;

        KripkeWorld(int id, std::set<Variable_t> props, const std::vector<Variable_t>& agents);

        KripkeWorld(KripkeWorld &&other) noexcept = default;

        std::vector<std::reference_wrapper<KripkeWorld>>& accessibleWorlds(Variable_t agent);
    };

    class KripkeModel {
    public:
        std::vector<KripkeWorld> worlds;

        std::string toString();

        explicit KripkeModel(std::vector<KripkeWorld> ws);

        KripkeModel(KripkeModel &&other) noexcept = default;
    };

    typedef std::tuple<std::vector<Variable_t>, Formula, std::unordered_map<Variable_t, std::vector<Variable_t>>, std::vector<Query>> ModelBits_t;

    ModelBits_t makeMuddyChildren(int n);
    KripkeModel generateModel(std::vector<Variable_t> &variables, Formula &state_law,
                                   std::unordered_map<Variable_t, std::vector<Variable_t>> &obs);
    bool trueAtWorld(const KripkeModel& model, const Formula& f, const KripkeWorld& world, const std::set<int>& eliminatedWorlds, int depth);
    bool trueAtWorldKnowsThat(const KripkeModel& model, const Formula& f, const KripkeWorld &world, Variable_t agent, const std::set<int> &eliminatedWorlds, const int depth);
    bool trueAtWorldPublicAnnouncement(const KripkeModel& model, const Formula& announcement, const KripkeWorld& world, const Formula& f, const std::set<int> &eliminatedWorlds, int depth);
    void solveModel(const KripkeModel& model, const std::vector<Query>& queries);
}

#endif //MAYBETHEFASTEST_EPISTEMICMODELCHECKER_H
