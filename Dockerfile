FROM openjdk:8-jdk
RUN apt-get update
RUN apt-get install -y maven
COPY pom.xml /usr/local/service/pom.xml
COPY src /usr/local/service/src
WORKDIR /usr/local/service
RUN mvn package assembly:single
CMD ["java","-cp","target/my-app-1.0-SNAPSHOT-jar-with-dependencies.jar","com.daifuku.app.App"]