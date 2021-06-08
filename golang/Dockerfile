FROM golang:1.16-buster AS builder
WORKDIR /app
COPY epistemic_model_checker.go go.mod go.sum helpers.go main.go ./
RUN go build

FROM fedora:34
COPY --from=builder /app/bacprojgo /app/palcheckergo
COPY hyperfine /usr/bin/hyperfine
ENV minchildren=3
ENTRYPOINT ["hyperfine", "--warmup", "1", "-m", "2", "-M", "50", "--export-json", "/var/results/mc-golang.json", "--parameter-scan", "num_children", "3", "11", "./app/palcheckergo -c {num_children}"]