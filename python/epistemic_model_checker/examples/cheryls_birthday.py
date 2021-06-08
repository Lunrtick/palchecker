from epistemic_model_checker.internal import Formula as F, FormulaType as FT

__all__ = ["cheryls_birthday_state_law"]

months = [(5, 6), (5, 7), (5, 8), (6, 7), (6, 8), (7, 8)]
days = [
    (14, 15),
    (14, 16),
    (14, 17),
    (14, 18),
    (14, 19),
    (15, 16),
    (15, 17),
    (15, 18),
    (15, 19),
    (16, 17),
    (16, 18),
    (16, 19),
    (17, 18),
    (17, 19),
    (18, 19),
]


def prop_converter(prop: int):
    return {5: "May", 6: "June", 7: "July", 8: "August"}.get(prop, str(prop))


birthday_possibilites = [
    F(FT.And, children=[F(FT.Proposition, prop_converter(p)) for p in pair])
    for pair in [
        (15, 5),
        (16, 5),
        (19, 5),
        (17, 6),
        (18, 6),
        (14, 7),
        (16, 7),
        (14, 8),
        (15, 8),
        (17, 8),
    ]
]
month_possibilities = [
    F(FT.And, children=[F(FT.Proposition, prop_converter(p)) for p in pair])
    for pair in months
]
day_possibilities = [
    F(FT.And, children=[F(FT.Proposition, prop_converter(p)) for p in pair])
    for pair in days
]


cheryls_birthday_state_law = F(
    FT.And,
    children=[
        F(FT.Or, children=birthday_possibilites),
        F(FT.Not, children=[F(FT.Or, children=month_possibilities)]),
        F(FT.Not, children=[F(FT.Or, children=day_possibilities)]),
    ],
)