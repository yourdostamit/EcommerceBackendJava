# ---------- build stage ----------
FROM maven:3.8.7-openjdk-17 AS build
WORKDIR /workspace
# copy only what Maven needs for faster builds (optional optimization)
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN mvn -N -B dependency:go-offline

# copy sources and build
COPY src ./src
RUN mvn -B -DskipTests clean package

# ---------- runtime stage ----------
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# copy the jar from the build stage (adjust name if your artifactId/version differs)
COPY --from=build /workspace/target/*.jar app.jar

# expose (optional) the port your app binds to (Render uses PORT env var)
EXPOSE 8080

# run the JAR
ENTRYPOINT ["java","-jar","/app/app.jar"]
