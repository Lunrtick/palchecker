from epistemic_model_checker.internal import Formula, FormulaType as FT, valid


def test_valid_Formula_leaves():
    assert valid(Formula(FT.Top), set()) is True
    assert valid(Formula(FT.Bot), set()) is False
    assert valid(Formula(FT.Proposition, "a"), {"a"}) is True
    assert valid(Formula(FT.Proposition, "a"), {"b"}) is False


def test_valid_Formula_and():
    and_with_a_b = Formula(
        FT.And,
        children=[Formula(FT.Proposition, "a"), Formula(FT.Proposition, "b")],
    )
    assert valid(and_with_a_b, {"a", "b"}) is True
    assert valid(and_with_a_b, {"a"}) is False
    assert valid(and_with_a_b, {"b"}) is False
    assert valid(and_with_a_b, set()) is False


def test_valid_Formula_or():
    or_with_a_b = Formula(
        FT.Or,
        children=[Formula(FT.Proposition, "a"), Formula(FT.Proposition, "b")],
    )
    assert valid(or_with_a_b, {"a", "b"}) is True
    assert valid(or_with_a_b, {"a"}) is True
    assert valid(or_with_a_b, {"b"}) is True
    assert valid(or_with_a_b, set()) is False


def test_valid_Formula_not():
    not_with_true = Formula(
        FT.Not,
        children=[Formula(FT.Top)],
    )
    not_with_false = Formula(
        FT.Not,
        children=[Formula(FT.Bot)],
    )
    assert valid(not_with_true, set()) is False
    assert valid(not_with_false, set()) is True