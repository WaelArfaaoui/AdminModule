# Build stage
FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
# Separate the build steps to invalidate the cache
RUN mvn clean
RUN mvn package -DskipTests

# Runtime stage
FROM amazoncorretto:17

WORKDIR /app
COPY --from=build /build/target/Admin-module-*.jar /app/

EXPOSE 8090

ENV DB_URL=jdbc:postgresql://admin-module-db:5432/book_social_network
ENV MAILDEV_URL=localhost

CMD java -jar Admin-module-1.0.11.jar
