# Quickstart

1. clone the repository locally
1. make sure docker and make are installed
1. `make build` - the Java container takes particularly long due to Maven's slow download speed (about 2mins on my machine)
1. `make bench` - it may be advisable to tweak the MAX_CHILDREN value, as some benchmarks (which run at least 3 times each) can take as long as 10 mins for each run on my machine. This would need to be changed for every individual benchmark in the makefile.
1. `python plot.py` - this needs matplotlib to be installed, and requires python 3.8 or later. It generates plots in the results folder

# Building the benchmark containers

Run `make build` to build all of the benchmark containers. These all contain the model checker, and a copy of hyperfine for benchmarking. Running `make build-python`, for example, would build only the python container.

# Running the benchmarks

Once the containers are all built, `make bench` runs the muddy children benchmark for all the languages. Running `make bench-python` would run only the python benchmark.

`ctrl+c` won't stop a running benchmark. You need to run `docker ps` in a separate terminal, and then `docker stop <container_name>`.

# Parsing new input files

Both the Python and Java programs can parse input files. The C++ and Go parsers were not completed.

## Java

-   From inside the `<workspace_root>/java` folder (and after building the containers with `make build`) run `docker run --rm -it -v (pwd)/examples:/examples --entrypoint bash gabesoich/palchecker:java`
-   This mounts the `<workspace_root>/java/examples` folder to /examples in the container, and opens a shell in the container
-   Inside the container, run `java -jar target/EpistemicModelChecker-1.0-SNAPSHOT.jar 10 0 /examples/cheryls_birthday.txt` to parse the `cheryls_birthday.txt` file, and answer the queries. The `10 0` is necessary only because a more advanced argument passing method wasn't used.

## Python

-   From inside the `<workspace_root>/python` folder (and after building the containers with `make build`) run `docker run --rm -it -v (pwd)/epistemic_model_checker/examples:/examples --entrypoint bash gabesoich/palchecker:python`
-   This mounts the `<workspace_root>/python/epistemic_model_checker/examples` folder to /examples in the container, and opens a shell in the container
-   Inside the container, run `python epistemic_model_checker/main.py /examples/cheryls_birthday.txt` to parse the `cheryls_birthday.txt` file, and answer the queries.
