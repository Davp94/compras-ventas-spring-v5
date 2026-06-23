FROM maven:3.9.15-amazoncoretto-25-alpine as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncoretto:25-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 9500

ENTRYPOINT ["sh", "-c", "java -jar app.jar"]