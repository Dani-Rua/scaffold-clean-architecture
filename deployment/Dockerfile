FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY applications/app-service/build/libs/statistics-stats.jar statistics-stats.jar
# Replace with a non-root user to avoid running the container with excessive privileges
ENV JAVA_OPTS=" -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
RUN adduser -D appuser
USER appuser
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar statistics-stats.jar" ]
