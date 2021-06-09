import matplotlib.pyplot as plt
import json
import os
from dataclasses import dataclass
from pathlib import Path


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
    names = {"cpp": "C++", "golang": "Golang", "java": "Java", "python": "Python"}
    return " ".join(
        [
            names.get(s, "Single Threaded")
            for s in fname.removeprefix("mc-").removesuffix(".json").split("-")
        ]
    )


results: list[ResultSet] = []
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
            results.append(ResultSet(filename_to_label(filename), cmd_results))
fig = plt.figure()
for res in sorted(results, key=lambda e: e.name):
    plt.errorbar(
        [r.num_children for r in res.results],
        [r.mean_runtime_in_s for r in res.results],
        yerr=[r.stddev for r in res.results],
        label=res.name,
        capsize=4,
    )
plt.yscale("log")
plt.legend(loc="best")
plt.grid()

if __name__ == "__main__":
    plt.savefig("results/muddy_children.png", dpi=300)
    print("saved plot to ./results/muddy_children.png")