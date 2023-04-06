FROM openjdk:11-jdk-slim

RUN groupadd --gid 1000 gradle && useradd --uid 1000 --gid 1000 gradle
USER 1000:1000
COPY --chown=gradle:gradle . /app
WORKDIR /app
ENV GRADLE_USER_HOME=/app/.gradle

RUN chmod +x ./start.sh

ENTRYPOINT ["./start.sh"]
