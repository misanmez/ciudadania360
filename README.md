# Ciudadano 360

Proyecto multi-módulo basado en **Spring Boot 3**, **Java 17** y **PostgreSQL/PostGIS**, orientado a la gestión integral de la relación entre la administración y la ciudadanía.

El sistema sigue una arquitectura de **microlitos**: cada subsistema es un módulo Maven independiente y se despliega en un puerto distinto, pero todos comparten una **base de datos única** con esquemas separados. Esto aporta modularidad sin la complejidad de microservicios puros.

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
.\build-and-run.ps1 docker   # Todos los servicios
.\build-and-run.ps1 prod     # Construcción para producción
```

**Linux/Mac:**
```bash
./build-and-run.sh dev       # Solo base de datos
./build-and-run.sh docker    # Todos los servicios
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

---

## 🏗️ Arquitectura Técnica

### Stack Tecnológico
- **Backend**: Spring Boot 3.3.3, Java 17
- **Base de Datos**: PostgreSQL 15 + PostGIS
- **Migraciones**: Flyway
- **Documentación**: SpringDoc OpenAPI (Swagger)
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
├── subsistema-ciudadano/            # Gestión de ciudadanos
├── subsistema-tramitacion/          # Procesos BPM
├── subsistema-comunicaciones/       # Notificaciones
├── subsistema-videoconferencia/     # Videollamadas
├── subsistema-informacion/          # Contenidos y datasets
├── gestion-roles-permisos/          # Seguridad
├── docker-compose.yml               # Infraestructura
├── build-and-run.ps1               # Script Windows
└── build-and-run.sh                # Script Linux/Mac
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
docker compose down
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
- ⚠️ **Tests**: Unitarios implementados, faltan tests de integración
- ❌ **CI/CD**: Pendiente de implementación
- ❌ **Monitoreo**: Configuración básica, falta centralización
- ❌ **Seguridad**: OAuth2 configurado, falta Keycloak
- ❌ **Integraciones**: Pendientes (BPM, LLM, proveedores externos)

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
