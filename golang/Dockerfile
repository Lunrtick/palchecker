FROM golang:1.16-buster AS builder
WORKDIR /app
COPY epistemic_model_checker.go go.mod go.sum helpers.go main.go ./
RUN go build

FROM fedora:34
COPY --from=builder /app/bacprojgo /app/palcheckergo
ENTRYPOINT [ "./app/palcheckergo" ]