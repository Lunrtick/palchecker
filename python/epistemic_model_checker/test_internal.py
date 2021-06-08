from epistemic_model_checker.internal import Formula as F, FormulaType as FT


def test_func_printing():
    a = F(FT.Proposition, proposition="a")
    b = F(FT.Proposition, proposition="b")
    assert str(a) == "a"
    assert str(F(FT.Not, children=[a])) == "¬a"
    assert str(F(FT.And, children=[a, b])) == "(a ∧ b)"
    assert str(F(FT.Or, children=[a, b])) == "(a ∨ b)"

    assert str(F(FT.And, children=[F(FT.Or, children=[a, b]), b])) == "((a ∨ b) ∧ b)"
    assert (
        str(F(FT.And, children=[F(FT.Or, children=[a, b]), F(FT.Not, children=[b])]))
        == "((a ∨ b) ∧ ¬b)"
    )
    assert (
        str(
            F(
                FT.And,
                children=[
                    F(FT.Or, children=[a, b]),
                    F(FT.Not, children=[F(FT.Or, children=[a, b])]),
                ],
            )
        )
        == "((a ∨ b) ∧ ¬(a ∨ b))"
    )
