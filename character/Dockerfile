FROM amazoncorretto:21.0.2-al2023

COPY --chown=185 target/*.jar character-service.jar

USER 185

ENTRYPOINT ["java", "-jar", "character-service.jar"]
