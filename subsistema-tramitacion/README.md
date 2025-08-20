# subsistema-tramitacion

## Propósito
Ejecución procedimental con BPM (motor de procesos del esquema), modelado de flujos/pasos, integraciones con sistemas externos (Suite SOA, registro, gestor documental) y gestión documental (carpetas/expedientes, documentos, firmas).

## Entidades y atributos

### 1) Flujo
Plantilla funcional de tramitación (independiente del BPM).

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|---------------------|
| id | UUID | ✔️ | — |
| nombre | String | ✔️ | Catálogo de procesos (p. ej., “Incidencia vía pública”). |
| descripcion | Text | | — |
| version | Integer | ✔️ | Gobernanza de cambios. |
| activo | Boolean | ✔️ | Publicable. |
| tipo | String | | Ámbito (licencia, ayuda, incidencia…). |
| slaHoras | Integer | | Compromisos de servicio. |
| pasosDefinition | JSONB | ✔️ | Secuencia condicionada de pasos. |

### 2) Paso
Instancia parametrizada de un Flujo.

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|---------------------|
| id | UUID | ✔️ | — |
| flujoId | UUID | ✔️ | Plantilla. |
| nombre | String | ✔️ | — |
| orden | Integer | ✔️ | Secuencia. |
| tipo | ENUM(MANUAL,AUTOMATICO,BPM) | ✔️ | Quién lo ejecuta. |
| reglasEntrada/Salida | JSONB | | Validaciones y transiciones. |
| responsableRole | String | | Asignación por rol. |
| slaHoras | Integer | | Medición granular. |

### 3) Integracion
Conector y trazabilidad con sistemas externos (SOA/ERP/registro/third parties).

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|---------------------|
| id | UUID | ✔️ | — |
| nombre | String | ✔️ | — |
| proveedor | String | | Oracle SOA, etc. |
| sistema | String | ✔️ | Sistema destino. |
| tipo | ENUM(SOAP,REST,FILE,EVENT) | ✔️ | Tecnología. |
| endpoint | String | ✔️ | — |
| credencialRef | String | | Vault/secrets. |
| ultimaEjecucion | Instant | | Salud del conector. |
| estado | ENUM(OK,ERROR,DEGRADADO) | | Observabilidad. |
| trazabilidadId | String | | Correlación (OTel traceId). |

### 4) ProcesoBPM
Instancia en el engine BPM que ejecuta un flujo de negocio.

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|---------------------|
| id | UUID | ✔️ | — |
| engineInstanceId | String | ✔️ | ID del motor (Camunda/Activiti/Flowable). |
| definitionKey | String | ✔️ | Key de la definición BPMN. |
| businessKey | String | ✔️ | Generalmente solicitudId/expediente. |
| estado | ENUM(RUNNING,SUSPENDED,COMPLETED,CANCELED) | ✔️ | Gestión operativa. |
| inicio / fin | Instant | ✔️ | KPIs de duración. |
| variables | JSONB | | Contexto del proceso (decisiones, datos). |
| iniciador | String | | Usuario/servicio origen. |
| versionDef | Integer | ✔️ | Migraciones BPMN controladas. |

**¿Para qué sirve?**  
Registra y controla el ciclo de vida ejecutado por el motor BPM (no solo “flujo teórico”). Permite reintentos, suspensiones, métricas, y enlaza con TareaBPM.

### 5) TareaBPM
Tarea concreta dentro de un ProcesoBPM.

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|---------------------|
| id | UUID | ✔️ | — |
| procesoBpmId | UUID | ✔️ | Contexto. |
| engineTaskId | String | ✔️ | ID de la tarea en el motor. |
| nombre | String | ✔️ | Comprensible para agentes. |
| estado | ENUM(CREADA,ASIGNADA,EN_PROGRESO,COMPLETADA,CANCELADA) | ✔️ | Seguimiento. |
| assignee | String | | Usuario asignado (SSO/OAuth). |
| candidateGroup | String | | Asignación por rol. |
| dueDate | Instant | | Vencimientos/SLA. |
| priority | Integer | | Orden en bandeja. |
| variables | JSONB | | Datos de decisión. |
| created / completed | Instant | ✔️ | Métricas de eficiencia. |

**¿Para qué sirve?**  
Desglosa el proceso en unidades de trabajo gestionables, con asignación a roles/personas, vencimientos y auditoría de acciones.

### 6) Contrata
Proveedor externo que presta servicios municipales.

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|---------------------|
| id | UUID | ✔️ | — |
| nombre | String | ✔️ | — |
| cif | String | ✔️ | Fiscalidad. |
| contacto | String | | Responsable. |
| email/telefono | String | | Comunicación. |
| contratoReferencia | String | ✔️ | Marco contractual. |
| fechaInicio/fechaFin | Date | ✔️ | Vigencia. |
| ambito | String | | Lote/servicio. |
| direccion | String | | — |
| rating | Integer | | Evaluación desempeño. |

### 7) IncidenciaContrata
Seguimiento de incidencias sobre la contrata.

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|---------------------|
| id | UUID | ✔️ | — |
| contrataId | UUID | ✔️ | Relación. |
| descripcion | Text | ✔️ | — |
| severidad | ENUM(BAJA,MEDIA,ALTA,CRITICA) | ✔️ | Priorización. |
| estado | ENUM(ABIERTA,EN_PROCESO,RESUELTA,CERRADA) | ✔️ | Ciclo. |
| fechaApertura/fechaCierre | Instant | ✔️ | KPIs. |
| ubicacionId | UUID | | Contexto geográfico. |
| adjuntos | JSONB | | Evidencias. |
| resolucion | Text | | Cierre/acciones. |

### 8) Carpeta
Agrupa documentos (expediente digital).

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|---------------------|
| id | UUID | ✔️ | — |
| solicitudId | UUID | | Enlace al caso. |
| nombre | String | ✔️ | — |
| descripcion | String | | — |
| tipo | ENUM(EXPEDIENTE,ADJUNTO,PLANTILLA) | ✔️ | Diferencia legal/operativa. |
| ruta | String | | Interoperabilidad DMS. |
| permisos | JSONB | | Control de acceso granular. |
| numeroExpediente | String | | Identidad administrativa. |
| estado | ENUM(ABIERTO,CERRADO,ARCHIVADO) | ✔️ | Archivística. |

### 9) Documento
Archivo electrónico asociado.

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|---------------------|
| id | UUID | ✔️ | — |
| carpetaId | UUID | ✔️ | Agrupación. |
| nombre | String | ✔️ | — |
| tipoMime | String | ✔️ | Visualización/validación. |
| uriAlmacenamiento | String | ✔️ | S3/MinIO/Share. |
| hash | String | ✔️ | Integridad/antifraude. |
| version | Integer | ✔️ | Versionado legal. |
| fechaSubida | Instant | ✔️ | Auditoría. |
| origen | ENUM(CIUDADANO,SISTEMA,EMPLEADO) | ✔️ | Evidencia. |
| firmado | Boolean | | eFirma si aplica. |
| metadatos | JSONB | | Índices (OCR, tipología). |
