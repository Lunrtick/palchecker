FROM golang:1.16-buster AS builder
WORKDIR /app
COPY epistemic_model_checker.go go.mod go.sum helpers.go main.go ./
RUN go build

FROM fedora:34
COPY --from=builder /app/bacprojgo /app/palcheckergo
COPY hyperfine /usr/bin/hyperfine
ENV MIN_CHILDREN=3
ENV MAX_CHILDREN=12
ENV RESULTS_FILENAME="mc-golang"
ENTRYPOINT hyperfine --warmup 1 -m 2 -M 50 --export-json /var/results/$RESULTS_FILENAME.json --parameter-scan num_children $MIN_CHILDREN $MAX_CHILDREN "./app/palcheckergo -c {num_children}"