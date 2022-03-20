FROM openjdk:11.0.13-jre-slim
EXPOSE 8080
WORKDIR target/
ARG APPJAR=target/*.jar
COPY ${APPJAR} app.jar
ENTRYPOINT ["java","-cp","app:app/lib/*", "-jar", "app.jar"]

