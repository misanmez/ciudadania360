# 🔧 Script Unificado de Despliegue - Ciudadanía360

## 📋 Resumen de Cambios

Se ha **unificado** la funcionalidad de despliegue y verificación de servicios SOAP en un solo script: `build-and-run.sh`

### **✅ Cambios Realizados:**

#### **1. Script Unificado Mejorado**
- ✅ **Integrada verificación SOAP** en el comando `docker`
- ✅ **Nuevo comando `verify`** para verificar solo servicios SOAP
- ✅ **Verificación automática** de todos los endpoints SOAP
- ✅ **Mensajes informativos** sobre endpoints disponibles

#### **2. Funcionalidades del Script**
```bash
./build-and-run.sh [comando]

Comandos disponibles:
  dev     - Modo desarrollo (solo Postgres en Docker)
  docker  - Construir y ejecutar en Docker + verificación SOAP automática
  prod    - Construir para producción con Maven
  clean   - Limpiar contenedores y Maven
  reset   - Reset completo de Docker
  verify  - Verificar solo servicios SOAP
  help    - Mostrar ayuda
```

#### **3. Verificación SOAP Integrada**
- ✅ **Verificación automática** de CiudadanoWebService
- ✅ **Verificación automática** de TramitacionWebService  
- ✅ **Verificación automática** de ComunicacionesWebService
- ✅ **Validación de WSDL** para cada servicio
- ✅ **Reporte de estado** detallado

## 🚀 Uso del Script Unificado

### **Despliegue Completo con Verificación SOAP**
```bash
# Construir, ejecutar y verificar automáticamente
./build-and-run.sh docker
```

**Salida esperada:**
```
🐳 Construyendo y ejecutando Ciudadanía 360 en Docker...
✅ Base de datos lista (health=healthy)
⏳ Esperando a que la aplicación se inicie completamente...
✅ Ciudadanía 360 debería estar corriendo.

🔍 Verificando servicios SOAP...
✅ Aplicación ejecutándose correctamente
✅ CiudadanoWebService - WSDL disponible
✅ TramitacionWebService - WSDL disponible
✅ ComunicacionesWebService - WSDL disponible
✅ Todos los servicios SOAP funcionando correctamente

📋 Servicios SOAP disponibles:
  - Ciudadano: http://localhost:8080/services/ciudadano?wsdl
  - Tramitación: http://localhost:8080/services/tramitacion?wsdl
  - Comunicaciones: http://localhost:8080/services/comunicaciones?wsdl

🌐 Endpoints disponibles:
  📊 Aplicación: http://localhost:8080
  📖 Swagger UI: http://localhost:8080/swagger-ui.html
  🔍 Health Check: http://localhost:8080/actuator/health
  📋 API Docs: http://localhost:8080/v3/api-docs
```

### **Verificación Rápida de SOAP**
```bash
# Solo verificar servicios SOAP (app debe estar ejecutándose)
./build-and-run.sh verify
```

## 🎯 Ventajas del Script Unificado

### **✅ Beneficios:**
1. **Un solo punto de entrada** para todas las operaciones
2. **Verificación automática** de servicios SOAP
3. **Menos archivos** que mantener
4. **Consistencia** en el proceso de despliegue
5. **Información completa** sobre endpoints disponibles
6. **Manejo de errores** mejorado

### **🔧 Funcionalidades Técnicas:**
- **Detección automática** de Docker Compose v1/v2
- **Health checks** para PostgreSQL
- **Timeouts configurables** para verificación
- **Validación de WSDL** con grep
- **Colores y formato** para mejor legibilidad
- **Manejo de errores** robusto

## 📊 Comparación: Antes vs Después

### **❌ Antes (Scripts Separados):**
```bash
# Múltiples comandos necesarios
docker-compose up --build
./docker-verify-soap.sh
curl http://localhost:8080/services/ciudadano?wsdl
curl http://localhost:8080/services/tramitacion?wsdl
curl http://localhost:8080/services/comunicaciones?wsdl
```

### **✅ Después (Script Unificado):**
```bash
# Un solo comando para todo
./build-and-run.sh docker
```

## 🚀 Próximos Pasos

### **Para el Equipo de Desarrollo:**
1. **Usar el script unificado** para todos los despliegues
2. **Documentar** cualquier nueva funcionalidad en el script
3. **Mantener** la verificación SOAP actualizada
4. **Agregar** nuevos servicios SOAP al array de verificación

### **Para Producción:**
1. **Configurar** variables de entorno específicas
2. **Implementar** monitoreo continuo de servicios SOAP
3. **Agregar** alertas para fallos de servicios
4. **Documentar** procedimientos de rollback

## 📝 Notas Importantes

- **El script elimina** la necesidad de `docker-verify-soap.sh`
- **La verificación SOAP** es automática en modo `docker`
- **El comando `verify`** es independiente y reutilizable
- **Todos los endpoints** se muestran al final del despliegue
- **El script es compatible** con Docker Compose v1 y v2

