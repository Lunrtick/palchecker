# Quickstart

1. clone the repository locally
1. make sure docker and make are installed
1. `make build` - the Java container takes particularly long due to Maven's slow download speed (about 2mins on my machine)
1. `make bench` - it may be advisable to tweak the MAX_CHILDREN value, as some benchmarks (which run at least 3 times each) can take as long as 10 mins for each run.
1. `python plot.py` - this needs matplotlib to be installed, and requires python 3.8 or later. It generates plots in the results folder

# Building the benchmark containers

Run `make build` to build all of the benchmark containers. These all contain the model checker, and a copy of hyperfine for benchmarking. Running `make build-python`, for example, would build only the python container.

# Running the benchmarks

Once the containers are all built, `make bench` runs the muddy children benchmark for all the languages. Running `make bench-python` would run only the python benchmark.

`ctrl+c` won't stop a running benchmark. You need to run `docker ps` in a separate terminal, and then `docker stop <container_name>`.
