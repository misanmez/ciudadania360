# ---------- Etapa de construcción ----------
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Copiamos POMs para aprovechar la cache de dependencias
COPY pom.xml ./
COPY shared/pom.xml ./shared/
COPY subsistema-ciudadano/pom.xml ./subsistema-ciudadano/
COPY subsistema-tramitacion/pom.xml ./subsistema-tramitacion/
COPY subsistema-comunicaciones/pom.xml ./subsistema-comunicaciones/
COPY subsistema-videoconferencia/pom.xml ./subsistema-videoconferencia/
COPY subsistema-informacion/pom.xml ./subsistema-informacion/
COPY subsistema-ia/pom.xml ./subsistema-ia/
COPY gestion-roles-permisos/pom.xml ./gestion-roles-permisos/
COPY subsistema-interno/pom.xml ./subsistema-interno/
COPY ciudadania360-app/pom.xml ./ciudadania360-app/

# Descargar dependencias offline (cache)
RUN mvn -B -q -e -DskipTests dependency:go-offline

# Copiar código fuente completo
COPY shared/src ./shared/src
COPY subsistema-ciudadano/src ./subsistema-ciudadano/src
COPY subsistema-tramitacion/src ./subsistema-tramitacion/src
COPY subsistema-comunicaciones/src ./subsistema-comunicaciones/src
COPY subsistema-videoconferencia/src ./subsistema-videoconferencia/src
COPY subsistema-informacion/src ./subsistema-informacion/src
COPY subsistema-ia/src ./subsistema-ia/src
COPY gestion-roles-permisos/src ./gestion-roles-permisos/src
COPY subsistema-interno/src ./subsistema-interno/src
COPY ciudadania360-app/src ./ciudadania360-app/src

# Construir solo el módulo agregador y sus dependencias
RUN mvn -B -q -DskipTests clean package -pl ciudadania360-app -am

# ---------- Etapa de ejecución ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Crear usuario no root
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

# Copiar jar construido desde el builder
COPY --from=builder /app/ciudadania360-app/target/ciudadania360.jar app.jar
RUN chown appuser:appgroup /app/app.jar

# Usar usuario no root
USER appuser

# Exponer puerto
EXPOSE 8080

# Variables de entorno para Java
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:+UseContainerSupport"

# Comando de inicio
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
