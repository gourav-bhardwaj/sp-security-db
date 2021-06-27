FROM gradle:7.1.0-jdk8 as builder
WORKDIR '/app'
COPY . .
RUN gradle clean build -x test

FROM openjdk:8
WORKDIR '/app'
COPY --from=builder /app/build/libs/sp-security-db-*-SNAPSHOT.jar app.jar
CMD [ "java", "-jar", "app.jar" ]