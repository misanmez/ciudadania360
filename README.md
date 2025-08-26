# Ciudadano 360

Proyecto multi-módulo basado en **Spring Boot 3**, **Java 17** y **PostgreSQL/PostGIS**, orientado a la gestión integral de la relación entre la administración y la ciudadanía.

El sistema sigue una arquitectura de **microlitos**: cada subsistema es un módulo Maven independiente y se despliega en un puerto distinto, pero todos comparten una **base de datos única** con esquemas separados. Esto aporta modularidad sin la complejidad de microservicios puros.

---

## Justificación: Microlitos vs Microservicios

### Microlitos (implementado aquí)
- Cada subsistema tiene sus entidades, controladores, servicios, handlers y repositorios propios.
- Una única base de datos PostgreSQL con esquemas separados (`ciudadano`, `tramitacion`, etc.).
- Swagger/OpenAPI por cada subsistema.
- Despliegue más simple, con menor sobrecarga operativa.

### Microservicios puros (descartado en este pliego)
- Independencia total de BD y despliegue.
- Mayor complejidad de orquestación, monitorización y resiliencia.

**Conclusión:** La solución de **microlitos** cumple el pliego, reduce riesgos y costes de operación, y permite evolución modular.

---

## Requisitos

- Java 17+
- Maven 3.8+
- Docker y Docker Compose

---

## Levantar base de datos

```bash
docker compose up -d
```

---

## Ejecutar un microlito

```bash
mvn spring-boot:run -pl subsistema-ciudadano
```

Swagger disponible en:  
[http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)

---

## Tests

### Unitarios

```bash
mvn test
```

### Unitarios + Integración

```bash
mvn verify -Pintegration
```

---

## Puertos y Swagger

| Subsistema                     | Puerto | Swagger URL |
|--------------------------------|--------|-------------|
| subsistema-ciudadano           | 8082   | [Swagger](http://localhost:8082/swagger-ui.html) |
| subsistema-tramitacion         | 8083   | [Swagger](http://localhost:8083/swagger-ui.html) |
| subsistema-comunicaciones      | 8084   | [Swagger](http://localhost:8084/swagger-ui.html) |
| subsistema-videoconferencia    | 8085   | [Swagger](http://localhost:8085/swagger-ui.html) |
| subsistema-documental          | 8086   | [Swagger](http://localhost:8086/swagger-ui.html) |
| subsistema-analitica           | 8087   | [Swagger](http://localhost:8087/swagger-ui.html) |
| subsistema-informacion         | 8088   | [Swagger](http://localhost:8088/swagger-ui.html) |
| gestion-roles-permisos         | 8089   | [Swagger](http://localhost:8089/swagger-ui.html) |

---

## Subsistemas

- **subsistema-ciudadano** → Gestión de ciudadanos, solicitudes, interacciones, clasificaciones y ubicaciones.
- **subsistema-tramitacion** → Gestión de expedientes, procesos BPM y tareas BPM.
- **subsistema-comunicaciones** → Envío de notificaciones multicanal (email, SMS, push).
- **subsistema-videoconferencia** → Planificación y gestión de videollamadas con ciudadanos.
- **subsistema-documental** → Gestión de documentos, versiones y almacenamiento.
- **subsistema-analitica** → Métricas, cuadros de mando y analítica de uso.
- **subsistema-informacion** → Gestión de datasets, sugerencias y fuentes de datos externas.
- **gestion-roles-permisos** → Seguridad, control de accesos y perfiles de usuario.
