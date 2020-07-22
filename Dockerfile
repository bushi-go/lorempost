FROM maven:3.6.3-jdk-11 as build
WORKDIR /opt/build
ENV REACT_APP_API_URL="http://localhost:8080/api"
ENV NODE_ENV="production"
COPY pom.xml .
COPY ./api/pom.xml ./api/
COPY ./ui/pom.xml ./ui/
RUN mvn dependency:go-offline
COPY ./api/src ./api/src
COPY ./ui/*.json ./ui/
COPY ./ui/src   ./ui/src
COPY ./ui/public ./ui/public

RUN mvn clean install

FROM openjdk:11
WORKDIR /app

COPY --from=build /opt/build/api/target/*.jar app.jar
CMD ["java", "-jar", "/app/app.jar"]
EXPOSE 8080
