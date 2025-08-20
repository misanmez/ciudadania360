# subsistema-comunicaciones

## Propósito
Orquesta mensajería multicanal (email, SMS, push, carta), suscripciones temáticas, notificaciones transaccionales y encuestas de satisfacción/servicio. Conecta con el módulo de ciudadano y analítica.

## Entidades y atributos

### 1) Comunicacion
Campaña o envío transaccional.

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|---------------------|
| id | UUID | ✔️ | — |
| asunto | String(150) | ✔️ | — |
| cuerpo | Text(HTML/Plantilla) | ✔️ | Renderizable. |
| canal | ENUM(EMAIL,SMS,PUSH,CARTA) | ✔️ | Elección de proveedor. |
| estado | ENUM(BORRADOR,PLANIFICADA,ENVIADA,ERROR,CANCELADA) | ✔️ | Seguimiento. |
| destinatario | String | | Email/teléfono/alias tópico. |
| ciudadanoId | UUID | | Personalización. |
| programadaPara | Instant | | Envío diferido. |
| remitente | String | | Dirección/identificador autorizado. |
| plantillaId | String | | Versionado de plantillas. |
| metrica | JSONB | | Aperturas, clics, rebotes. |

### 2) Suscripcion
Preferencias para recibir información.

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|---------------------|
| id | UUID | ✔️ | — |
| ciudadanoId | UUID | ✔️ | Titular. |
| tema | String | ✔️ | Canal temático (p. ej., “obras”, “cultura”). |
| canalPreferido | ENUM(EMAIL,SMS,PUSH) | ✔️ | Respeta preferencias. |
| frecuencia | ENUM(INSTANT,DIARIA,SEMANAL) | ✔️ | Ruido vs. actualidad. |
| activo | Boolean | ✔️ | Consentimiento/opt-in. |
| filtros | JSONB | | Segmentación por barrio, tags. |
| fechaAlta / fechaBaja | Instant | | Auditoría. |

### 3) Notificacion
Aviso transaccional asociado a un evento (alta de solicitud, cita, resolución).

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|---------------------|
| id | UUID | ✔️ | — |
| ciudadanoId | UUID | ✔️ | Destinatario. |
| titulo | String(120) | ✔️ | Breve. |
| mensaje | String(500) | ✔️ | — |
| canal | ENUM(EMAIL,SMS,PUSH) | ✔️ | — |
| prioridad | ENUM(BAJA,MEDIA,ALTA) | | SLA de envío. |
| estado | ENUM(PENDIENTE,ENVIADA,ERROR,LEIDA) | ✔️ | Trazabilidad. |
| referencia | String | | Enlace a solicitud/proceso. |
| fechaEntrega / fechaLectura | Instant | | Métricas de efectividad. |

### 4) Encuesta
Cuestionario configurable para satisfacción/servicio.

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|---------------------|
| id | UUID | ✔️ | — |
| titulo | String | ✔️ | — |
| descripcion | Text | | Contexto. |
| preguntas | JSONB | ✔️ | Estructura dinámica (tipos, opciones). |
| estado | ENUM(BORRADOR,ACTIVA,CERRADA) | ✔️ | Control de publicación. |
| audiencia | ENUM(GENERAL,SOLO_AFECTADOS,AGENTES) | ✔️ | Segmentación. |
| fechaInicio / fechaFin | Instant | | Ventana de respuestas. |
| vinculadaSolicitudId | UUID | | Enviar tras resolver. |

### 5) RespuestaEncuesta
Respuesta individual.

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|---------------------|
| id | UUID | ✔️ | — |
| encuestaId | UUID | ✔️ | Relación. |
| ciudadanoId | UUID | | Anónimo permitido. |
| respuestas | JSONB | ✔️ | Soporta variados tipos de pregunta. |
| puntuacion | Integer(0-10) | | NPS/CSAT. |
| comentarios | Text | | Insight cualitativo. |
| fecha | Instant | ✔️ | — |
