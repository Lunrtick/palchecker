from dataclasses import dataclass
from enum import Enum, auto, unique
from multiprocessing.managers import SyncManager
from typing import (
    FrozenSet,
    Iterator,
    Optional,
    TypeVar,
    Union,
    List,
    Dict,
    AbstractSet,
    Tuple,
)
from itertools import combinations, chain
from graphviz.dot import Digraph
import multiprocessing as mp
from functools import lru_cache

T = TypeVar("T")


def _raise(ex: Exception):
    raise ex


def first(xs: Iterator[T]) -> Optional[T]:
    try:
        return next(xs)
    except StopIteration:
        return None


def flatten(l: Iterator[List[T]]) -> Iterator[T]:
    return chain.from_iterable(l)


def powerset(s: List[T]) -> Iterator[Tuple[T, ...]]:
    return chain.from_iterable(combinations(s, r) for r in range(len(s) + 1))


Variable = str


@unique
class FormulaType(Enum):
    Top = auto()
    Bot = auto()
    And = auto()
    Or = auto()
    Not = auto()
    Proposition = auto()
    KnowsThat = auto()
    KnowsWhether = auto()
    PubAnnounceBox = auto()
    PubAnnounceDiamond = auto()


FT = FormulaType


@unique
class QueryType(Enum):
    WHERE = auto()
    TRUE = auto()
    VALID = auto()


@dataclass
class Formula:
    type: FormulaType
    proposition: Optional[Variable] = None
    children: Optional[List["Formula"]] = None
    agent: Optional[Variable] = None
    announcement: Optional["Formula"] = None

    _id: Optional[int] = None

    def __iter__(self):
        if self.type not in {FormulaType.And, FormulaType.Or, FormulaType.KnowsThat}:
            raise Exception(f"This Formula of type {self.type} is not iterable")
        for c in self.children:
            yield c

    def __repr__(self) -> str:
        return str(self)

    def __str__(self) -> str:
        matcher = {
            FormulaType.Top: lambda: "⊤",
            FormulaType.Bot: lambda: "⊥",
            FormulaType.And: lambda: " ∧ ".join([str(c) for c in self]),
            FormulaType.Or: lambda: " ∨ ".join([str(c) for c in self]),
            FormulaType.Not: lambda: f"¬{str(self.children[0])}",
            FormulaType.Proposition: lambda: self.proposition,
            FormulaType.KnowsThat: lambda: f"{self.agent} knows that {self.children[0]}",
            FormulaType.KnowsWhether: lambda: f"{self.agent} knows whether {self.children[0]}",
            FormulaType.PubAnnounceDiamond: lambda: f"<!{self.announcement}> {self.children[0]}",
            FormulaType.PubAnnounceBox: lambda: f"[!{self.announcement}] {self.children[0]}",
        }
        form = matcher.get(
            self.type, lambda: _raise(Exception(f"unknown Formula type: {self.type}"))
        )()

        if self.type in [FormulaType.KnowsThat, FormulaType.KnowsWhether] or (
            self.children is not None and len(self.children) > 1
        ):
            return f"({form})"
        else:
            return form

    def __hash__(self) -> int:
        if self._id is not None:
            return self._id
        else:
            self._id = hash(str(self))
            return self._id


@dataclass
class Query:
    type: QueryType
    formula: Formula
    propositions: Optional[List[Variable]] = None


def valid(f: Formula, true_propositions: AbstractSet[Variable]):
    if f.type == FormulaType.Top:
        return True
    elif f.type == FormulaType.Bot:
        return False
    elif f.type == FormulaType.And:
        return all([valid(c, true_propositions) for c in f])
    elif f.type == FormulaType.Or:
        return any([valid(c, true_propositions) for c in f])
    elif f.type == FormulaType.Not:
        return not valid(f.children[0], true_propositions)
    elif f.type == FormulaType.Proposition:
        return f.proposition in true_propositions
    else:
        raise Exception(f"unknown Formula type: {f.type}")


@dataclass
class KripkeWorld:
    id: int
    true_propositions: FrozenSet[str]
    relations: Dict[Variable, List["KripkeWorld"]]
    model: Optional["KripkeModel"] = None

    @property
    def name(self):
        return " ".join(sorted(self.true_propositions))

    def accessible_worlds(
        self, agent: Variable, eliminated_worlds: FrozenSet["KripkeWorld"]
    ):
        for w in self.relations[agent]:
            if w not in eliminated_worlds:
                yield w

    def __repr__(self) -> str:
        return f"W{{{self.name}}}"

    def __str__(self) -> str:
        return self.name

    def __hash__(self) -> int:
        return self.id


def generate_worlds(
    variables: List[Variable], state_law: Formula, observations: Dict[str, List[str]]
):
    perms = powerset(variables)
    valid_worlds = [
        KripkeWorld(idx, frozenset(w), {agent: [] for agent in observations.keys()})
        for idx, w in enumerate(perms)
        if valid(state_law, set(w))
    ]

    all_vars = frozenset(variables)
    for w in valid_worlds:
        for agent, obs in observations.items():
            unobserved = all_vars.difference(obs)
            observably_true = w.true_propositions.difference(unobserved)
            for w1 in valid_worlds:
                if observably_true == w1.true_propositions.difference(unobserved):
                    w.relations[agent].append(w1)
    return valid_worlds


def visualise_worlds(worlds: List[KripkeWorld]):
    g = Digraph("Worlds", engine="fdp")
    for w in worlds:
        g.node(str(w))
    for w in worlds:
        for agent, rels in w.relations.items():
            for w1 in rels:
                if w != w1:
                    g.edge(str(w), str(w1), label=agent[0:1])
    g.view()


def true_at_world(
    f: Formula, world: KripkeWorld, eliminated_worlds: FrozenSet[KripkeWorld] = None
) -> bool:
    if eliminated_worlds == None:
        eliminated_worlds = frozenset()

    if f.type == FormulaType.Top:
        return True
    elif f.type == FormulaType.Bot:
        return False
    elif f.type == FormulaType.And:
        for c in f:
            if not true_at_world(c, world, eliminated_worlds):
                return False
        return True
    elif f.type == FormulaType.Or:
        for c in f:
            if true_at_world(c, world, eliminated_worlds):
                return True
        return False
    elif f.type == FormulaType.Not:
        return not true_at_world(f.children[0], world, eliminated_worlds)
    elif f.type == FormulaType.Proposition:
        return f.proposition in world.true_propositions
    elif f.type == FormulaType.KnowsThat:
        return taw_knows_that(f.children[0], world, f.agent, eliminated_worlds)
    elif f.type == FormulaType.KnowsWhether:
        return taw_knows_that(
            f.children[0], world, f.agent, eliminated_worlds
        ) or taw_knows_that(
            Formula(FormulaType.Not, children=f.children),
            world,
            f.agent,
            eliminated_worlds,
        )
    elif f.type == FormulaType.PubAnnounceBox:
        return not taw_pub_announce(
            world,
            f.announcement,
            Formula(FormulaType.Not, children=f.children),
            eliminated_worlds,
        )
    elif f.type == FormulaType.PubAnnounceDiamond:
        return taw_pub_announce(
            world,
            f.announcement,
            f.children[0],
            eliminated_worlds,
        )
    else:
        raise Exception(f"unknown Formula type: {f.type}")


def taw_knows_that(
    f: Formula,
    world: KripkeWorld,
    agent: Variable,
    eliminated_worlds: FrozenSet[KripkeWorld],
) -> bool:
    at_least_one = False
    for w in world.accessible_worlds(agent, eliminated_worlds):
        at_least_one = True
        if not true_at_world(f, w, eliminated_worlds):
            return False
    return at_least_one


def taw_pub_announce(
    world: KripkeWorld,
    announcement: Formula,
    formula: Formula,
    eliminated_worlds: FrozenSet[KripkeWorld],
):
    if not true_at_world(announcement, world, eliminated_worlds):
        return False
    new_elims = set()
    for w in world.model.all_worlds_except(eliminated_worlds):
        if not true_at_world(announcement, w, eliminated_worlds):
            new_elims.add(w)
    all_elims = eliminated_worlds.union(new_elims)
    if world in all_elims:
        return False
    return true_at_world(formula, world, all_elims)


class KripkeModel:
    def __init__(self, worlds: List[KripkeWorld]) -> None:
        self._worlds = worlds

        for w in self._worlds:
            w.model = self

    def all_worlds_except(self, eliminated_worlds: FrozenSet[KripkeWorld]):
        for w in self._worlds:
            if w not in eliminated_worlds:
                yield w