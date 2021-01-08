FROM gradle:6.6-jdk11 AS builder
COPY . /app
WORKDIR /app

FROM adoptopenjdk/openjdk11:latest
ADD . .

ARG HEAP_SIZE
ENV HEAP_SIZE=${HEAP_SIZE:-256M}
ARG NEW_SIZE
ENV NEW_SIZE=${NEW_SIZE:-256M}

## Copy JAR
RUN mkdir -p /build/libs
COPY --from=builder /app/build/libs /build/libs

RUN ["./gradlew", "clean", "build", "-x","test"]

ENTRYPOINT java -server -Xms${HEAP_SIZE} -Xmx${HEAP_SIZE} -XX:NewSize=${NEW_SIZE} -XX:MaxNewSize=${NEW_SIZE} -Djava.net.preferIPv4Stack=true -Djava.security.egd=file:/dev/./urandom  -jar /build/libs/korello-0.0.1-SNAPSHOT.jar