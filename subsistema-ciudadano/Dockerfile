# Etapa de construcción
FROM eclipse-temurin:17-jdk AS builder

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY shared/pom.xml ./shared/
COPY subsistema-ciudadano/pom.xml ./subsistema-ciudadano/

# Descargar dependencias (capa separada para cache)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY shared/src ./shared/src
COPY subsistema-ciudadano/src ./subsistema-ciudadano/src

# Construir aplicación
RUN mvn clean package -pl subsistema-ciudadano -am -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jre

# Crear usuario no-root para seguridad
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Establecer directorio de trabajo
WORKDIR /app

# Copiar JAR desde la etapa de construcción
COPY --from=builder /app/subsistema-ciudadano/target/*.jar app.jar

# Cambiar propietario del archivo
RUN chown appuser:appuser app.jar

# Cambiar al usuario no-root
USER appuser

# Exponer puerto
EXPOSE 8082

# Configurar JVM para contenedores
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:+UseContainerSupport"

# Comando de inicio
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
