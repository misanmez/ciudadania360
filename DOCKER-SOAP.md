# 🐳 Docker + SOAP Services - Ciudadanía360

## 📋 Resumen

Este documento explica cómo ejecutar Ciudadanía360 con servicios SOAP en Docker y las modificaciones necesarias para que funcionen correctamente.

## 🔧 Modificaciones Realizadas

### 1. **Configuración SOAP en Docker**
- ✅ Agregada configuración SOAP al perfil `application-docker.yml`
- ✅ Incluidos todos los servicios web (Ciudadano, Tramitación, Comunicaciones)
- ✅ Configuración CXF para contenedores Docker

### 2. **Dockerfile Actualizado**
- ✅ Incluidos módulos SOAP en el proceso de build
- ✅ Agregados POMs de servicios web para cache de dependencias
- ✅ Optimizado para incluir todos los módulos AyWebFwk

### 3. **Docker Compose Mejorado**
- ✅ Variables de entorno para configuración SOAP
- ✅ Configuración de puertos y dependencias
- ✅ Health checks para PostgreSQL

## 🚀 Cómo Ejecutar

### **Opción 1: Script Unificado (Recomendado)**
```bash
# Construir y ejecutar con verificación automática de SOAP
./build-and-run.sh docker

# Solo verificar servicios SOAP (si la app ya está ejecutándose)
./build-and-run.sh verify

# Ver todas las opciones disponibles
./build-and-run.sh help
```

### **Opción 2: Docker Compose Directo**
```bash
# Construir y ejecutar todos los servicios
docker-compose up --build

# O en segundo plano
docker-compose up --build -d
```

### **Paso 2: Verificar Servicios SOAP**
```bash
# Verificar servicios SOAP (integrado en el script principal)
./build-and-run.sh verify

# O verificar manualmente
curl http://localhost:8080/services/ciudadano?wsdl
curl http://localhost:8080/services/tramitacion?wsdl
curl http://localhost:8080/services/comunicaciones?wsdl
```

### **Paso 3: Verificar Manualmente**
```bash
# Verificar salud de la aplicación
curl http://localhost:8080/actuator/health

# Verificar Swagger UI
curl http://localhost:8080/swagger-ui.html
```

## 🌐 Endpoints Disponibles

### **Servicios SOAP**
- **CiudadanoWebService**: `http://localhost:8080/services/ciudadano?wsdl`
- **TramitacionWebService**: `http://localhost:8080/services/tramitacion?wsdl`
- **ComunicacionesWebService**: `http://localhost:8080/services/comunicaciones?wsdl`

### **APIs REST**
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/v3/api-docs`
- **Health Check**: `http://localhost:8080/actuator/health`

## 🔍 Troubleshooting

### **Problema: Servicios SOAP no responden**
```bash
# Verificar logs de la aplicación
docker-compose logs ciudadania360-app

# Verificar que CXF esté configurado
docker-compose exec ciudadania360-app curl http://localhost:8080/services/ciudadano?wsdl
```

### **Problema: Base de datos no conecta**
```bash
# Verificar estado de PostgreSQL
docker-compose logs postgres

# Verificar conectividad
docker-compose exec ciudadania360-app ping postgres
```

### **Problema: Puerto 8080 ocupado**
```bash
# Verificar procesos en puerto 8080
netstat -ano | findstr :8080

# Detener procesos
docker-compose down
```

## 📊 Arquitectura Docker

```
┌─────────────────────────────────────────────────────────┐
│                    Docker Network                       │
│                                                         │
│  ┌─────────────────┐    ┌─────────────────────────────┐ │
│  │   PostgreSQL    │    │    Ciudadanía360 App        │ │
│  │   Port: 5432    │◄───┤    Port: 8080               │ │
│  │                 │    │                             │ │
│  │ - Base de datos │    │ - APIs REST                 │ │
│  │ - PostGIS       │    │ - Servicios SOAP            │ │
│  │ - Health check  │    │ - Swagger UI                │ │
│  └─────────────────┘    │ - Actuator                  │ │
│                         └─────────────────────────────┘ │
└─────────────────────────────────────────────────────────┘
```

## 🎯 Características Clave

### **✅ Ventajas del Sistema Híbrido en Docker**
- **Flexibilidad**: REST para frontend moderno, SOAP para integración legacy
- **Escalabilidad**: Contenedores independientes y escalables
- **Portabilidad**: Funciona en cualquier entorno con Docker
- **Mantenibilidad**: Configuración centralizada y versionada

### **🔧 Configuración SOAP Específica**
- **CXF Path**: `/services` (configurable via `CXF_PATH`)
- **Endpoints**: Relativos para compatibilidad con Docker
- **Namespaces**: Estándar AyWebFwk del Ayuntamiento de València
- **WSDL**: Disponible en todos los servicios

## 📝 Notas Importantes

1. **Primera ejecución**: Puede tardar más tiempo debido a la descarga de dependencias
2. **Memoria**: Asegúrate de tener al menos 4GB de RAM disponible
3. **Puertos**: Verifica que 8080 y 5432 estén libres
4. **Logs**: Usa `docker-compose logs -f` para seguir los logs en tiempo real

## 🚀 Próximos Pasos

- [ ] Configurar SSL/TLS para producción
- [ ] Implementar load balancing
- [ ] Agregar monitoreo con Prometheus
- [ ] Configurar backup automático de base de datos
