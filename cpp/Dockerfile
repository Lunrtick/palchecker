FROM fedora:34 AS builder
RUN dnf install -yb gcc g++ git cmake make
COPY cmake/ /app/cmake/
COPY CMakeLists.txt EpistemicModelChecker.* main.cpp /app/
WORKDIR /app
RUN cmake . -B ./cmake-release -DCMAKE_BUILD_TYPE=Release
WORKDIR /app/cmake-release
RUN make -j 11

FROM fedora:34
RUN mkdir /app
COPY --from=builder /app/cmake-release/maybethefastest /app/pal-model-checker
COPY hyperfine /usr/bin/hyperfine
WORKDIR /app
ENV MIN_CHILDREN=3
ENV MAX_CHILDREN=12
ENV RESULTS_FILENAME="mc-cpp"
ENTRYPOINT hyperfine --warmup 1 -m 2 -M 50 --export-json /var/results/$RESULTS_FILENAME.json --parameter-scan num_children $MIN_CHILDREN $MAX_CHILDREN "./pal-model-checker {num_children}"