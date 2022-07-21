FROM java:8
FROM maven:alpine

# image layer
WORKDIR /my-app
ADD pom.xml /my-app
RUN mvn verify clean --fail-never

# Image layer: with the application
COPY . /my-app
RUN mvn -v
RUN mvn clean install -DskipTests
EXPOSE 8080
ADD ./target/your.jar /developments/
ENTRYPOINT ["java","-jar","/developments/your.jar"]