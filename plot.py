import matplotlib.pyplot as plt
import matplotlib as mpl
import json
import os
from dataclasses import dataclass
from pathlib import Path
import sys

DARK_MODE = len(sys.argv) == 2 and sys.argv[1] == "dark"
DARK_FILE_SUFFIX = "_dark" if DARK_MODE else ""
if DARK_MODE:
    mpl.rcParams.update(
        {
            "text.color": "#fff",
            "axes.labelcolor": "#fff",
            "axes.edgecolor": "#777",
            "grid.color": "#777",
            "xtick.color": "#fff",
            "ytick.color": "#fff",
            "axes.facecolor": "#444",
            "figure.facecolor": "#444",
        }
    )


def set_colours_with_haskell():
    mpl.rcParams["axes.prop_cycle"] = mpl.cycler(
        color=[
            "#7b1fa2",
            "#b39ddb",
            "#1565c0",
            "#81d4fa",
            "#c2185b",
            "#e57373",
            "#388e3c",
            "#aed581",
            "#ffeb3b",
        ]
    )


def set_colours_without_haskell():
    mpl.rcParams["axes.prop_cycle"] = mpl.cycler(
        color=[
            "#7b1fa2",
            "#b39ddb",
            "#1565c0",
            "#81d4fa",
            "#388e3c",
            "#aed581",
            "#ffeb3b",
        ]
    )


@dataclass
class Result:
    num_children: int
    mean_runtime_in_s: float
    stddev: float


@dataclass
class ResultSet:
    name: str
    results: list[Result]


def filename_to_label(fname: str) -> str:
    names = {
        "cpp": "C++",
        "golang": "Go",
        "java": "Java",
        "python": "Python",
        "haskell": "Haskell",
        "haskell#symbolic": "Haskell Symb.",
    }
    return " ".join(
        [
            names.get(s, "1T")
            for s in fname.removeprefix("mc-")
            .removesuffix(".json")
            .replace("model-only-", "")
            .split("-")
        ]
    )


results: list[ResultSet] = []
model_only_results: list[ResultSet] = []
results_file = Path("./results")
for path, _, filenames in os.walk(results_file):
    for filename in filenames:
        if not "json" in filename:
            continue
        with open(Path(path) / filename) as f:
            res = json.load(f)
            cmd_results = [
                Result(int(r["parameters"]["num_children"]), r["mean"], r["stddev"])
                for r in res["results"]
            ]
            if "model" in filename:
                model_only_results.append(
                    ResultSet(filename_to_label(filename), cmd_results)
                )
            else:
                results.append(ResultSet(filename_to_label(filename), cmd_results))

set_colours_with_haskell()
fig = plt.figure()
for res in sorted(results, key=lambda e: e.name):
    plt.errorbar(
        [r.num_children for r in res.results],
        [r.mean_runtime_in_s for r in res.results],
        yerr=[r.stddev for r in res.results],
        label=res.name,
        capsize=4 if "symb." not in res.name.lower() else 0,
        linestyle="dashed" if "haskell" in res.name.lower() else "solid",
    )
plt.yscale("log")
plt.legend(loc="best")
plt.xlabel("Number of children")
plt.ylabel("Execution time (seconds)")
plt.grid()
filename = f"results/muddy_children{DARK_FILE_SUFFIX}.png"
plt.savefig(filename, dpi=300)
print(f"saved {filename}")

set_colours_without_haskell()
fig = plt.figure()
for res in sorted(model_only_results, key=lambda e: e.name):
    plt.errorbar(
        [r.num_children for r in res.results],
        [r.mean_runtime_in_s for r in res.results],
        yerr=[r.stddev for r in res.results],
        label=res.name,
        capsize=4,
    )
plt.yscale("log")
plt.legend(loc="best")
plt.xlabel("Number of children")
plt.ylabel("Execution time (seconds)")
plt.grid()
filename = f"results/muddy_children_model_only{DARK_FILE_SUFFIX}.png"
plt.savefig(filename, dpi=300)
print(f"saved {filename}")

set_colours_without_haskell()
rezzes: dict[str, list[float]] = {}
fig = plt.figure()
for res_model_only, res_full in zip(
    sorted(model_only_results, key=lambda e: e.name),
    sorted(
        [r for r in results if "haskell" not in r.name.lower()], key=lambda e: e.name
    ),
):
    rezzes[res_model_only.name] = [
        mo.mean_runtime_in_s / full.mean_runtime_in_s * 100
        for mo, full in zip(res_model_only.results, res_full.results)
    ]
    d = rezzes[res_model_only.name]
    plt.errorbar(
        range(3, len(d) + 3),
        d,
        label=res_model_only.name,
        linestyle="solid",
    )
plt.legend(loc="best")
plt.xlabel("Number of children")
plt.ylabel("Proportion of execution time spent on model generation (%)")
plt.grid()
plt.ylim([0, 100])
filename = f"results/muddy_children_relative_model_build_time{DARK_FILE_SUFFIX}.png"
plt.savefig(filename, dpi=300)
print(f"saved {filename}")
# @dataclass
# class StackedResult:
#     time_without_model: float
#     time_model_only: float
#     stddev: float
#     stddev_model_only: float


# width = 0.4
# rezzes: dict[str, list[StackedResult]] = {}
# for res_model_only, res_full in zip(
#     sorted(model_only_results, key=lambda e: e.name),
#     sorted(
#         [r for r in results if "haskell" not in r.name.lower()], key=lambda e: e.name
#     ),
# ):
#     fig = plt.figure(num=res_model_only.name)
#     rezzes[res_model_only.name] = [
#         StackedResult(
#             time_without_model=(full.mean_runtime_in_s - mo.mean_runtime_in_s),
#             time_model_only=mo.mean_runtime_in_s,
#             stddev=sqrt(full.stddev + mo.stddev),
#             stddev_model_only=mo.stddev,
#         )
#         for mo, full in zip(res_model_only.results, res_full.results)
#     ]
#     d = rezzes[res_model_only.name]
#     plt.bar(
#         range(3, len(d) + 3),
#         [r.time_model_only for r in d],
#         width,
#         yerr=[r.stddev_model_only for r in d],
#         label="Model Only",
#     )
#     plt.bar(
#         range(3, len(d) + 3),
#         [r.time_without_model for r in d],
#         width,
#         yerr=[r.stddev for r in d],
#         label="Answer Queries",
#         bottom=[r.time_model_only for r in d],
#     )
#     plt.yscale("log")
#     plt.legend(loc="best")
#     plt.xlabel("Number of children")
#     plt.ylabel("Execution time (seconds)")
#     plt.grid()
