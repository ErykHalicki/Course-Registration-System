
FROM eclipse-temurin:19-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy Maven wrapper and POM file first (for efficient caching)
COPY mvnw pom.xml ./

COPY .mvn .mvn

COPY src src

# Install dependencies (without building the whole project)
RUN ./mvnw install

# Copy the entire project

# Build the project
# Expose the port Vaadin runs on (default is 8080)
EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]
