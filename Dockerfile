FROM maven:3.8-openjdk-17 AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

ARG SPRING_PROFILES_ACTIVE
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-default}

COPY src/main/resources/application-prod.properties src/main/resources/

RUN apt-get install maven -y
RUN mvn clean install -P${SPRING_PROFILES_ACTIVE}

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /target/algamoney_api-0.0.1-SNAPSHOT.jar algamoney_api.jar

ENTRYPOINT ["java", "-jar", "algamoney_api.jar"]