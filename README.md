# Ciudadano 360

Proyecto multi-módulo basado en **Spring Boot 3**, **Java 17** y **PostgreSQL/PostGIS**, orientado a la gestión integral de la relación entre la administración y la ciudadanía.

El sistema sigue una arquitectura de **microlitos**: cada subsistema es un módulo Maven independiente y se despliega en un puerto distinto, pero todos comparten una **base de datos única** con esquemas separados. Esto aporta modularidad sin la complejidad de microservicios puros.

El agregador principal `ciudadania360-app` actúa como **front controller**, levantando todos los microlitos y ofreciendo un punto central de acceso en el puerto **8080**.

---

## 🚀 Inicio Rápido

### Prerrequisitos
- Java 17+
- Maven 3.8+
- Docker y Docker Compose

### Ejecución Automática (Recomendado)

**Windows (PowerShell):**
```powershell
.\build-and-run.ps1 dev      # Solo base de datos
.\build-and-run.ps1 docker   # Todos los servicios y agregador
.\build-and-run.ps1 prod     # Construcción para producción
```

**Linux/Mac:**
```bash
./build-and-run.sh dev       # Solo base de datos
./build-and-run.sh docker    # Todos los servicios y agregador
./build-and-run.sh prod      # Construcción para producción
```

### Ejecución Manual

#### 1. Levantar base de datos
```bash
docker compose up -d postgres
```

#### 2. Ejecutar un microlito
```bash
mvn spring-boot:run -pl subsistema-ciudadano
```

Swagger disponible en:  
[http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)

---

## Justificación: Microlitos vs Microservicios

### Microlitos (implementado aquí)
- Cada subsistema tiene sus entidades, controladores, servicios, handlers y repositorios propios.
- Una única base de datos PostgreSQL con esquemas separados (`ciudadano`, `tramitacion`, `comunicaciones`, `videoconferencia`, `informacion`, `roles`).
- Swagger/OpenAPI por cada subsistema y agrupación automática por paquetes.
- Despliegue más simple, con menor sobrecarga operativa.

### Microservicios puros (descartado)
- Independencia total de BD y despliegue.
- Mayor complejidad de orquestación, monitorización y resiliencia.

**Conclusión:** La solución de **microlitos** cumple el pliego, reduce riesgos y costes de operación, y permite evolución modular.

---

## Levantar Base de Datos

```bash
docker compose up -d
```

---

## Ejecutar el agregador principal

```bash
mvn spring-boot:run -pl ciudadania360-app
```

Swagger central disponible en:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

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

| Servicio / Subsistema         | Puerto | Swagger URL |
|-------------------------------|--------|-------------|
| ciudadania360-app             | 8080   | [Swagger](http://localhost:8080/swagger-ui.html) |
| subsistema-ciudadano           | 8082   | [Swagger](http://localhost:8082/swagger-ui.html) |
| subsistema-tramitacion         | 8083   | [Swagger](http://localhost:8083/swagger-ui.html) |
| subsistema-comunicaciones      | 8084   | [Swagger](http://localhost:8084/swagger-ui.html) |
| subsistema-videoconferencia    | 8085   | [Swagger](http://localhost:8085/swagger-ui.html) |
| subsistema-informacion         | 8088   | [Swagger](http://localhost:8088/swagger-ui.html) |
| gestion-roles-permisos         | 8089   | [Swagger](http://localhost:8089/swagger-ui.html) |

---

## Subsistemas

- **ciudadania360-app** → Agregador principal que coordina todos los microlitos.
- **subsistema-ciudadano** → Gestión de ciudadanos, solicitudes, interacciones, clasificaciones y ubicaciones.
- **subsistema-tramitacion** → Gestión de expedientes, procesos BPM y tareas BPM.
- **subsistema-comunicaciones** → Envío de notificaciones multicanal (email, SMS, push).
- **subsistema-videoconferencia** → Planificación y gestión de videollamadas con ciudadanos.
- **subsistema-informacion** → Gestión de datasets, sugerencias y fuentes de datos externas.
- **gestion-roles-permisos** → Seguridad, control de accesos y perfiles de usuario.

---

## 🏗️ Arquitectura Técnica

### Stack Tecnológico
- **Backend**: Spring Boot 3.3.3, Java 17
- **Base de Datos**: PostgreSQL 15 + PostGIS
- **Migraciones**: Flyway
- **Documentación**: SpringDoc OpenAPI (Swagger) con agrupación por paquetes
- **Mapeo**: MapStruct
- **Utilidades**: Lombok
- **Cobertura**: JaCoCo

### Configuración de Entornos
- **Desarrollo**: `application-dev.yml`
- **Docker**: `application-docker.yml`
- **Producción**: `application-prod.yml`

### Monitoreo y Métricas
- **Health Checks**: Spring Boot Actuator
- **Métricas**: Micrometer + Prometheus
- **Logging**: Configuración por entornos

---

## 🔧 Desarrollo

### Estructura del Proyecto
```
ciudadania360/
├── shared/                          # DTOs y utilidades comunes
├── ciudadania360-app/               # Agregador principal
├── subsistema-ciudadano/            # Gestión de ciudadanos
├── subsistema-tramitacion/          # Procesos BPM
├── subsistema-comunicaciones/       # Notificaciones
├── subsistema-videoconferencia/     # Videollamadas
├── subsistema-informacion/          # Contenidos y datasets
├── gestion-roles-permisos/          # Seguridad
├── docker-compose.yml               # Infraestructura
├── build-and-run.ps1                # Script Windows
└── build-and-run.sh                  # Script Linux/Mac
```

### Comandos Útiles

**Construir proyecto completo:**
```bash
mvn clean install
```

**Ejecutar tests:**
```bash
mvn test
```

**Limpiar recursos:**
```bash
docker compose down -v
mvn clean
```

**Ver logs de un servicio:**
```bash
docker compose logs -f subsistema-ciudadano
```

---

## 📊 Estado del Proyecto

- ✅ **Estructura base**: Completamente implementada
- ✅ **Entidades y APIs**: Implementadas en todos los subsistemas
- ✅ **Base de datos**: Configurada con esquemas separados
- ✅ **Docker**: Configuración completa para desarrollo
- ⚠️ **Tests**: Unitarios implementados, integración pendiente
- ❌ **CI/CD**: Pendiente de implementación
- ❌ **Monitoreo**: Configuración básica, falta centralización
- ❌ **Seguridad**: OAuth2 configurado, falta Keycloak
- ❌ **Integraciones externas**: BPM, LLM y otros proveedores

---

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

---

## 📝 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.
