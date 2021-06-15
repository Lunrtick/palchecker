FROM haskell:8.10
WORKDIR /app
COPY package.yaml stack.yaml stack.yaml.lock smcdel.cabal /app/
RUN stack build --dependencies-only
COPY src/ /app/src/
COPY exec/ /app/exec/
COPY static/ /app/static/
COPY bench/ /app/bench/
COPY Examples/ /app/Examples/
RUN stack build --local-bin-path /app/bin/ --copy-bins
COPY hyperfine /usr/bin/hyperfine
ENV MIN_CHILDREN=3
ENV MAX_CHILDREN=13
ENV RESULTS_FILENAME="mc-haskell"
ENV SYMBOLIC_OR_EXPLICIT="explicit"
ENTRYPOINT hyperfine --warmup 1 -m 2 -M 50 --export-json /var/results/$RESULTS_FILENAME.json --parameter-scan num_children $MIN_CHILDREN $MAX_CHILDREN "./bin/smcdel-explicit-muddy $SYMBOLIC_OR_EXPLICIT {num_children}"