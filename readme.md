# Building the benchmark containers

Run `make build` to build all of the benchmark containers. These all contain the model checker, and a copy of hyperfine for benchmarking. Running `make build-python`, for example, would build only the python container.

# Running the benchmarks

Once the containers are all built, `make bench` runs the muddy children benchmark for all the languages. Running `make bench-python` would run only the python benchmark.

`ctrl+c` won't stop a running benchmark. You need to run `docker ps` in a separate terminal, and then `docker stop <container_name>`.
