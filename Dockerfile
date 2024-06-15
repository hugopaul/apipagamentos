#
# Build stage
#
FROM maven:3.8.6-openjdk-18 AS build
USER root
WORKDIR /home/app/
COPY . .
RUN mvn  clean package

#
# Package stage
#
FROM openjdk:20-slim
USER root
COPY --from=build /home/app/target/apipagamentos-0.0.1-SNAPSHOT.jar /usr/local/lib/
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","/usr/local/lib/apipagamentos-0.0.1-SNAPSHOT.jar"]