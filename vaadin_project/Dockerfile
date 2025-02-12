# Use an official OpenJDK 17 image (Vaadin requires Java 17+)
FROM eclipse-temurin:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY . /app

# Install dependencies and build the project
RUN ./mvnw package 

# Expose the port Vaadin runs on (default is 8080)
EXPOSE 8080

CMD ["java", "-jar", "target/spring-skeleton-1.0-SNAPSHOT.jar"]
