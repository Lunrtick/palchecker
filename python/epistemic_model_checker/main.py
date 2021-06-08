from typing import List
from graphviz import Digraph, Graph
from sys import argv
from epistemic_model_checker.examples.cheryls_birthday import cheryls_birthday_state_law
from epistemic_model_checker.internal import (
    KripkeModel,
    Query,
    QueryType,
    generate_worlds,
    true_at_world,
    first,
)
from epistemic_model_checker.parser import get_model_data
from itertools import dropwhile


def solve_model(model: KripkeModel, queries: List[Query]):
    for q in queries:
        if q.type == QueryType.WHERE:
            print(f"Where?: {q.formula}\n")
            print(
                "\t>"
                + str(
                    [
                        w
                        for w in model.all_worlds_except(frozenset())
                        if true_at_world(q.formula, w)
                    ]
                )
            )
            print("\n\n")
        elif q.type == QueryType.VALID:
            print(f"Valid?: {q.formula}\n")
            valid = True
            for w in model.all_worlds_except(frozenset()):
                if not true_at_world(q.formula, w):
                    valid = False
                    break
            print("\t>Yep, that's valid" if valid else "\t>Nope, invalid.")
            print("\n\n")
        elif q.type == QueryType.TRUE:
            true_props = frozenset(q.propositions or [])
            world = first(
                dropwhile(
                    lambda w: w.true_propositions != true_props,
                    model.all_worlds_except(set()),
                )
            )
            if world is None:
                raise Exception(f"No world with props {true_props} found")
            print(f"True?: At {repr(world)}, {q.formula}\n")
            is_true = true_at_world(q.formula, world)
            print("\t> Yep, that's true" if is_true else "\t> Nope, not true.")
            print("\n\n")


if __name__ == "__main__":
    vars, state_law, obs, queries = get_model_data(argv[1])
    worlds = generate_worlds(vars, state_law, dict(obs))
    model = KripkeModel(worlds)
    solve_model(model, queries)