FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app
COPY pom.xml .
COPY shared/pom.xml ./shared/
COPY subsistema-videoconferencia/pom.xml ./subsistema-videoconferencia/
RUN mvn dependency:go-offline -B
COPY shared/src ./shared/src
COPY subsistema-videoconferencia/src ./subsistema-videoconferencia/src
RUN mvn clean package -pl subsistema-videoconferencia -am -DskipTests

FROM eclipse-temurin:17-jre
RUN groupadd -r appuser && useradd -r -g appuser appuser
WORKDIR /app
COPY --from=builder /app/subsistema-videoconferencia/target/subsistema-videoconferencia-*.jar app.jar
RUN chown appuser:appuser app.jar
USER appuser
EXPOSE 8085
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:+UseContainerSupport"
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/ciudadania360
ENV SPRING_DATASOURCE_USERNAME=ciudadania
ENV SPRING_DATASOURCE_PASSWORD=ciudadania
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
