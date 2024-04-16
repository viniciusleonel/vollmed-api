FROM openjdk:17

WORKDIR /voll_med_api

COPY target/*.jar /voll_med_api/api-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD java -XX:+UseContainerSupport -jar api-0.0.1-SNAPSHOT.jar