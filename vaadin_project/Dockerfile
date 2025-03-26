
FROM eclipse-temurin:19-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy Maven wrapper and POM file first (for efficient caching)
COPY mvnw pom.xml ./

COPY .mvn .mvn

COPY src src
RUN apt-get update && apt-get install -y curl
RUN curl -fsSL https://nodejs.org/dist/v22.13.1/node-v22.13.1-linux-x64.tar.gz | tar -xz -C /usr/local --strip-components=1
RUN node --version && npm --version

# Expose the port Vaadin runs on (default is 8080)
EXPOSE 8080

CMD ["./mvnw", "test"]
