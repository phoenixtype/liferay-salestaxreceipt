# Use an official Maven image with JDK as a parent image
FROM maven:3.8.3-openjdk-11 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src/ /app/src/

# Build the application without running tests
RUN mvn clean package -DskipTests

# Use an official OpenJDK image as the base image for the final container
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build container
COPY --from=build /app/target/liferay-salestaxreceipt-1.0-SNAPSHOT.jar /app/salestaxreceipt.jar

# Set the entry point to run your application
ENTRYPOINT ["java", "-jar", "salestaxreceipt.jar"]