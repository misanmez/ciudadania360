# Ciudadano 360

Proyecto multi-módulo con Spring Boot 3, Java 17, PostgreSQL/PostGIS.
Formato automático con Spotless, cobertura mínima del 80% con Jacoco.
Tests JUnit 5: unitarios por defecto e integración con perfil `integration`.

## Requisitos
- Java 17+
- Maven 3.8+
- Docker y Docker Compose

## Levantar base de datos
```bash
docker compose up -d
```

## Ejecutar un microlito
```bash
mvn spring-boot:run -pl microlito-ciudadano
```
Swagger: http://localhost:8082/swagger-ui.html

## Tests
- Unitarios: `mvn test`
- Unitarios+Integración: `mvn verify -Pintegration`

## Puertos y Swagger
- microlito-ciudadano → 8082 → http://localhost:8082/swagger-ui.html
- microlito-tramitacion → 8083 → http://localhost:8083/swagger-ui.html
- microlito-comunicaciones → 8084 → http://localhost:8084/swagger-ui.html
- microlito-videoconferencia → 8085 → http://localhost:8085/swagger-ui.html
- microlito-documental → 8086 → http://localhost:8086/swagger-ui.html
- microlito-analitica → 8087 → http://localhost:8087/swagger-ui.html
- microlito-informacion → 8088 → http://localhost:8088/swagger-ui.html
