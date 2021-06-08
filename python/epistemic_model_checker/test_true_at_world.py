from epistemic_model_checker.internal import (
    Formula as F,
    FormulaType as FT,
    true_at_world,
    KripkeWorld as KW,
    KripkeModel as KM,
    visualise_worlds,
)

test_agents = ("albert", "bernard")
test_props = ("a", "b", "c")
test_worlds = [
    KW(1, frozenset(test_props), {}),
    KW(2, frozenset("a"), {}),
    KW(3, frozenset("b"), {}),
    KW(4, frozenset(), {}),
]
test_worlds[0].relations["albert"] = [test_worlds[1]]
test_worlds[1].relations["albert"] = [test_worlds[0]]

test_worlds[0].relations["bernard"] = [test_worlds[2]]
test_worlds[2].relations["bernard"] = [test_worlds[1]]

test_worlds[3].relations["albert"] = [test_worlds[2]]
test_worlds[2].relations["albert"] = [test_worlds[3]]

test_worlds[1].relations["bernard"] = [test_worlds[3]]
test_worlds[3].relations["bernard"] = [test_worlds[1]]

for w in test_worlds:
    for agent in test_agents:
        if agent not in w.relations:
            w.relations[agent] = []


def test_taw_formula_leaves():
    for w in test_worlds:
        assert true_at_world(F(FT.Top), w) is True
        assert true_at_world(F(FT.Bot), w) is False
        assert true_at_world(F(FT.Proposition, "a"), w) is ("a" in w.true_propositions)


def test_taw_formula_and():
    and_with_a_b = F(
        FT.And,
        children=[F(FT.Proposition, "a"), F(FT.Proposition, "b")],
    )
    assert true_at_world(and_with_a_b, test_worlds[0]) is True
    assert true_at_world(and_with_a_b, test_worlds[1]) is False
    assert true_at_world(and_with_a_b, test_worlds[2]) is False
    assert true_at_world(and_with_a_b, test_worlds[3]) is False


def test_taw_formula_or():
    or_with_a_b = F(
        FT.Or,
        children=[F(FT.Proposition, "a"), F(FT.Proposition, "b")],
    )
    assert true_at_world(or_with_a_b, test_worlds[0]) is True
    assert true_at_world(or_with_a_b, test_worlds[1]) is True
    assert true_at_world(or_with_a_b, test_worlds[2]) is True
    assert true_at_world(or_with_a_b, test_worlds[3]) is False


def test_taw_formula_not():
    not_with_true = F(
        FT.Not,
        children=[F(FT.Top)],
    )
    not_with_false = F(
        FT.Not,
        children=[F(FT.Bot)],
    )
    assert true_at_world(not_with_true, test_worlds[3]) is False
    assert true_at_world(not_with_false, test_worlds[3]) is True


def test_taw_formula_knows_that_simple():
    simple = F(FT.KnowsThat, children=[F(FT.Proposition, "a")], agent="albert")
    simple_world = KW(1, frozenset(["a"]), {"albert": [], "bernard": []})
    simple_world.relations["albert"] = [simple_world]
    simple_model = KM([simple_world])

    assert true_at_world(simple, simple_world) is True
    simple.agent = "bernard"
    assert true_at_world(simple, simple_world) is False

    simple_world = KW(1, frozenset(["b"]), {"albert": [], "bernard": []})
    simple_model = KM([simple_world])

    assert true_at_world(simple, simple_world) is False
    simple.agent = "albert"
    assert true_at_world(simple, simple_world) is False


def test_taw_formula_knows_that_complex():
    form = F(FT.KnowsThat, children=[F(FT.Proposition, "1")], agent="albert")
    form_neg = F(
        FT.KnowsThat,
        children=[F(FT.Not, children=[F(FT.Proposition, "1")])],
        agent="albert",
    )
    form_nested = F(
        FT.KnowsThat,
        children=[F(FT.KnowsThat, children=[F(FT.Proposition, "2")], agent="albert")],
        agent="bernard",
    )
    worlds = [
        KW(1, frozenset(["1", "2"]), {"albert": [], "bernard": []}),
        KW(2, frozenset(["2"]), {"albert": [], "bernard": []}),
    ]
    worlds[0].relations["bernard"] = [worlds[1], worlds[0]]
    worlds[1].relations["bernard"] = [worlds[0], worlds[1]]
    worlds[0].relations["albert"] = [worlds[0]]
    worlds[1].relations["albert"] = [worlds[1]]

    model = KM(worlds)

    assert true_at_world(form_nested, worlds[0]) is True

    assert true_at_world(form, worlds[0]) is True
    assert true_at_world(form_neg, worlds[1]) is True

    form.agent = "bernard"

    assert true_at_world(form, worlds[0]) is False
    assert true_at_world(form, worlds[1]) is False