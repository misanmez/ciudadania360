# ---------- Etapa de construcción ----------
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Copiamos poms para cache
COPY pom.xml .
COPY shared/pom.xml ./shared/
COPY subsistema-ciudadano/pom.xml ./subsistema-ciudadano/
COPY subsistema-tramitacion/pom.xml ./subsistema-tramitacion/
COPY subsistema-comunicaciones/pom.xml ./subsistema-comunicaciones/
COPY subsistema-videoconferencia/pom.xml ./subsistema-videoconferencia/
COPY subsistema-informacion/pom.xml ./subsistema-informacion/
COPY gestion-roles-permisos/pom.xml ./gestion-roles-permisos/
COPY ciudadania360-app/pom.xml ./ciudadania360-app/

RUN mvn -q -e -DskipTests dependency:go-offline

# Código fuente
COPY shared/src ./shared/src
COPY subsistema-ciudadano/src ./subsistema-ciudadano/src
COPY subsistema-tramitacion/src ./subsistema-tramitacion/src
COPY subsistema-comunicaciones/src ./subsistema-comunicaciones/src
COPY subsistema-videoconferencia/src ./subsistema-videoconferencia/src
COPY subsistema-informacion/src ./subsistema-informacion/src
COPY gestion-roles-permisos/src ./gestion-roles-permisos/src
COPY ciudadania360-app/src ./ciudadania360-app/src

# Construye SOLO el agregador y sus dependencias
RUN mvn -q -DskipTests clean package -pl ciudadania360-app -am

# ---------- Etapa de ejecución ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Seguridad: usuario no root
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

COPY --from=builder /app/ciudadania360-app/target/ciudadania360.jar app.jar
RUN chown appuser:appgroup /app/app.jar
USER appuser

EXPOSE 8080
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:+UseContainerSupport"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
