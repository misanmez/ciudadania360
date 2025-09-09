# =========================
# Etapa 1: Build con Maven
# =========================
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar solo los pom.xml para cachear dependencias
COPY pom.xml .
COPY ciudadania360-app/pom.xml ./ciudadania360-app/
COPY shared/pom.xml ./shared/
COPY subsistema-ciudadano/pom.xml ./subsistema-ciudadano/
COPY subsistema-tramitacion/pom.xml ./subsistema-tramitacion/
COPY subsistema-comunicaciones/pom.xml ./subsistema-comunicaciones/
COPY subsistema-videoconferencia/pom.xml ./subsistema-videoconferencia/
COPY subsistema-informacion/pom.xml ./subsistema-informacion/
COPY gestion-roles-permisos/pom.xml ./gestion-roles-permisos/
COPY subsistema-ia/pom.xml ./subsistema-ia/
COPY subsistema-interno/pom.xml ./subsistema-interno/

# Descargar dependencias para acelerar futuros builds
RUN mvn dependency:go-offline -B

# Copiar todo el código
COPY . .

# Compilar solo la app principal y sus dependencias
RUN mvn clean package -pl ciudadania360-app -am -DskipTests

# =========================
# Etapa 2: Imagen final
# =========================
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copiar el jar construido, sin hardcodear versión
COPY --from=build /app/ciudadania360-app/target/*.jar ./app.jar

EXPOSE 8080

# Variables de entorno flexibles para base de datos y perfil
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/ciudadania360
ENV SPRING_DATASOURCE_USERNAME=ciudadania
ENV SPRING_DATASOURCE_PASSWORD=ciudadania
ENV SPRING_PROFILES_ACTIVE=docker

# Comando para arrancar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
