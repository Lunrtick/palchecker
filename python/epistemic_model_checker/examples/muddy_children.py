from typing import FrozenSet
from epistemic_model_checker.main import solve_model
from epistemic_model_checker.internal import (
    Formula as F,
    FormulaType as FT,
    KripkeModel,
    Query,
    QueryType,
    generate_worlds,
)
import timeit
import os, sys


class HiddenPrints:
    def __enter__(self):
        self._original_stdout = sys.stdout
        sys.stdout = open(os.devnull, "w")

    def __exit__(self, exc_type, exc_val, exc_tb):
        sys.stdout.close()
        sys.stdout = self._original_stdout


def build_var(child: int):
    # return f"{child}_is_dirty"
    return f"{child}"


def build_announcements(vars: FrozenSet[str], depth: int):
    if depth == len(vars):
        return [F(FT.And, children=[F(FT.Proposition, proposition=c) for c in vars])]
    else:
        return [
            F(
                FT.PubAnnounceBox,
                announcement=F(
                    FT.Not,
                    children=[
                        F(
                            FT.Or,
                            children=[
                                F(
                                    FT.KnowsWhether,
                                    agent=str(c),
                                    children=[
                                        F(FT.Proposition, proposition=build_var(c))
                                    ],
                                )
                                for c in range(1, len(vars) + 1)
                            ],
                        )
                    ],
                ),
                children=build_announcements(vars, depth + 1),
            )
        ]


def build_query(vars: FrozenSet[str]):
    return Query(
        QueryType.VALID,
        formula=F(
            FT.PubAnnounceBox,
            announcement=F(
                FT.Or, children=[F(FT.Proposition, proposition=c) for c in vars]
            ),
            children=build_announcements(vars, 1),
        ),
    )


def build_muddy_children(n=3):
    vars = frozenset(build_var(c) for c in range(1, n + 1))
    law = F(FT.Top)
    obs = {str(c): list(vars.difference({build_var(c)})) for c in range(1, n + 1)}
    queries = [build_query(vars)]
    return vars, law, obs, queries


def benchmark_mc(n=3):
    vars, state_law, obs, queries = build_muddy_children(n)
    worlds = generate_worlds(list(vars), state_law, obs)
    model = KripkeModel(worlds)
    solve_model(model, queries)


if __name__ == "__main__":
    num_kids = int(sys.argv[1])
    num_iters = int(sys.argv[2])
    time = 0.0
    try:
        if sys.argv[3] == "bench":
            with HiddenPrints():
                time = timeit.Timer(lambda: benchmark_mc(num_kids)).timeit(
                    number=num_iters
                )
            print(
                f"for {num_kids} children it took an average of {time / num_iters:.2f} seconds over {num_iters} runs"
            )
        else:
            benchmark_mc(num_kids)
    except IndexError:
        benchmark_mc(num_kids)