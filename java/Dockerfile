FROM maven:3.8.1-openjdk-16-slim AS builder
WORKDIR /app
COPY ./src ./src
COPY pom.xml .
RUN mvn -T 1C clean install
RUN mvn -T 1C compile
RUN mvn -T 1C package

FROM openjdk:16-slim
WORKDIR /app
COPY --from=builder /app/target/ ./target
COPY hyperfine /usr/bin/hyperfine
ENTRYPOINT ["hyperfine", "--warmup", "2", "-m", "2", "-M", "50", "--export-json", "/var/results/mc-java.json", "--parameter-scan", "num_children", "3", "12", "java -jar target/EpistemicModelChecker-1.0-SNAPSHOT.jar {num_children}"]
