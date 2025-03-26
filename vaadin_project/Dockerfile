
FROM eclipse-temurin:19-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy Maven wrapper and POM file first (for efficient caching)
COPY mvnw pom.xml ./

COPY .mvn .mvn

COPY src src

# Expose the port Vaadin runs on (default is 8080)
EXPOSE 8080

CMD ["./mvnw", "test"]
