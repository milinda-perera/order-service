FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/order-service-*.jar app.jar
EXPOSE 8083
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["java", "-jar", "app.jar"]
