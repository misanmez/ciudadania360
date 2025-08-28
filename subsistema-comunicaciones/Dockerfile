FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app
COPY pom.xml .
COPY shared/pom.xml ./shared/
COPY subsistema-comunicaciones/pom.xml ./subsistema-comunicaciones/
RUN mvn dependency:go-offline -B
COPY shared/src ./shared/src
COPY subsistema-comunicaciones/src ./subsistema-comunicaciones/src
RUN mvn clean package -pl subsistema-comunicaciones -am -DskipTests

FROM eclipse-temurin:17-jre
RUN groupadd -r appuser && useradd -r -g appuser appuser
WORKDIR /app
COPY --from=builder /app/subsistema-comunicaciones/target/subsistema-comunicaciones-*.jar app.jar
RUN chown appuser:appuser app.jar
USER appuser
EXPOSE 8084
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:+UseContainerSupport"
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/ciudadania360
ENV SPRING_DATASOURCE_USERNAME=ciudadania
ENV SPRING_DATASOURCE_PASSWORD=ciudadania
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
