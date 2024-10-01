FROM maven:3.9.8-eclipse-temurin-21 AS build

RUN mkdir /opt/app

COPY . /opt/app

WORKDIR /opt/app

ENV POSTGRES_DB_TEST_URL=${POSTGRES_DB_TEST_URL}

RUN mvn clean package

FROM eclipse-temurin:21-jre-alpine

RUN mkdir /opt/app

COPY --from=build  /opt/app/target/vollmed-0.0.1-SNAPSHOT.jar /opt/app/vollmed-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

ENV PROFILE=prd

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "vollmed-0.0.1-SNAPSHOT.jar"]
