LABEL org.opencontainers.image.source https://github.com/reisi007/pdf2stichwort
# ---- Build Stage ----
FROM gradle:8.10-jdk21-alpine AS builder
WORKDIR /app
COPY . .
RUN gradle shadowJar --no-daemon

# ---- Runtime Stage ----
FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=builder /app/app/build/libs/app-all.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]