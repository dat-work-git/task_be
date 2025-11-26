FROM maven:3.8.5-openjdk-17-slim as build
WORKDIR /app
COPY ./salemanagement ./salemanagement
WORKDIR /app/salemanagement
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/salemanagement/target/*.jar salemanagement.jar
EXPOSE 9001
ENTRYPOINT ["java","-jar","salemanagement.jar"]


