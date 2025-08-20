# Subsistema Ciudadano

## Propósito
Gestiona el ciclo de vida de solicitudes ciudadanas y su relación con la administración: registro, clasificación (manual/automática por LLM + motor de reglas), ubicación geográfica (PostGIS), interacción multicanal y cierre. Alimenta encuestas de satisfacción y analítica KPI.

---

## Entidades y atributos

### 1) Ciudadano
Representa a la persona que presenta solicitudes, quejas o sugerencias desde el Portal Ciudadano.

| Campo             | Tipo                               | Req | Descripción / Por qué                                           |
|------------------|-----------------------------------|-----|----------------------------------------------------------------|
| id               | UUID                               | ✔️  | Identificador único.                                           |
| nifNie           | String(15)                         | ✔️  | Identificación oficial; evita duplicados y facilita cruces con padrón/CRM. |
| nombre           | String(100)                        | ✔️  | Tratamiento personalizado en comunicaciones.                  |
| apellidos        | String(150)                        |     | —                                                              |
| email            | String(150)                        |     | Canal digital principal (notificaciones, validaciones).       |
| telefono         | String(30)                         |     | Recordatorios SMS/llamadas.                                    |
| canalPreferido   | ENUM(EMAIL,SMS,PUSH,LLAMADA,PRESENCIAL) |     | Cumplir preferencias de comunicación.                          |
| direccionPostal  | String(255)                        |     | Necesario en trámites presenciales/correspondencia.           |
| ubicacionId      | UUID(FK Ubicacion)                 |     | Georreferenciación para incidencias.                           |
| consentimientoLOPD | Boolean                           | ✔️  | Cumplimiento RGPD (tratamiento de datos).                     |
| estado           | ENUM(ACTIVO,BLOQUEADO,BAJA)        | ✔️  | Control de acceso y fraude.                                    |
| externalId       | String                             |     | Vínculo con CRM municipal/Suite SOA.                           |
| metadata         | JSONB                              |     | Preferencias y atributos no estructurados (barrio, accesibilidad, idioma). |
| (auditoría común)|                                   |     | Trazabilidad (OTel/Loki).                                      |

---

### 2) Solicitud
Unidad principal de trabajo (queja, incidencia, petición). Se clasifica por LLM + Motor de Reglas y puede derivar a BPM.

| Campo             | Tipo                               | Req | Descripción / Por qué                                           |
|------------------|-----------------------------------|-----|----------------------------------------------------------------|
| id               | UUID                               | ✔️  | —                                                              |
| ciudadanoId      | UUID(FK Ciudadano)                 | ✔️  | Titular.                                                       |
| titulo           | String(150)                        | ✔️  | Resumen para portales/BackOffice.                              |
| descripcion      | Text                               | ✔️  | Detalle para evaluación y derivación.                          |
| tipo             | ENUM(INCIDENCIA,QUEJA,INFORMACION,TRAMITE,OTROS) | ✔️ | Métricas por tipología.                                        |
| canalEntrada     | ENUM(WEB,APP,TELEFONO,PRESENCIAL,EMAIL) | ✔️ | Analítica omnicanal.                                           |
| estado           | ENUM(REGISTRADA,EN_TRAMITE,REQUIERE_INFO,RESUELTA,RECHAZADA,CERRADA) | ✔️ | Ciclo de vida homogéneo.                                       |
| prioridad        | ENUM(BAJA,MEDIA,ALTA,CRITICA)     |     | SLA y enrutamiento.                                            |
| clasificacionId  | UUID(FK Clasificacion)             |     | Categorización automática/manual.                               |
| ubicacionId      | UUID(FK Ubicacion)                 |     | Geolocalización de incidencias.                                 |
| numeroExpediente | String                             |     | Enlace con gestor documental/expediente.                       |
| fechaRegistro    | Instant                            | ✔️  | Punto de cronometraje SLA.                                     |
| fechaLimiteSLA   | Instant                            |     | Cálculo de SLA por regla/flujo.                                 |
| fechaCierre      | Instant                            |     | KPIs de resolución.                                            |
| scoreRelevancia  | Decimal                            |     | Resultado del clasificador (LLM).                               |
| origen           | String                             |     | Portal/app/API Gateway.                                        |
| adjuntosCount    | Integer                            |     | Volumen documental.                                            |
| encuestaEnviada  | Boolean                            |     | Ética/feedback de servicio.                                     |
| referenciaExterna| String                             |     | Integración con Suite SOA/Registro.                             |
| metadata         | JSONB                              |     | Campos ad-hoc de campañas.                                     |

---

### 3) Interacción
Histórico de comunicación en una solicitud (pública o interna).

| Campo       | Tipo                          | Req | Descripción / Por qué                  |
|------------|-------------------------------|-----|--------------------------------------|
| id         | UUID                           | ✔️  | —                                    |
| solicitudId| UUID(FK Solicitud)             | ✔️  | Contexto.                             |
| ciudadanoId| UUID(FK Ciudadano)             |     | Relación emisor/beneficiario.        |
| canal      | ENUM(COMENTARIO,EMAIL,SMS,LLAMADA,NOTA_INTERNA) | ✔️ | Trazabilidad multicanal.             |
| fecha      | Instant                        | ✔️  | Auditoría temporal.                  |
| agente     | String                         |     | Empleado responsable (SSO).          |
| mensaje    | Text                           | ✔️  | Contenido.                           |
| adjuntoUri | String                         |     | Evidencias.                          |
| visibilidad| ENUM(PUBLICA,INTERNA)         | ✔️  | Transparencia/seguridad.             |

---

### 4) Ubicación (PostGIS)
Georreferencia de una solicitud/ciudadano.

| Campo      | Tipo                    | Req | Descripción / Por qué          |
|-----------|-------------------------|-----|-------------------------------|
| id        | UUID                    | ✔️  | —                             |
| direccion | String(255)             |     | Lectura humana.               |
| municipio | String(120)             |     | Estadística territorial.      |
| provincia | String(120)             |     | —                             |
| cp        | String(10)              |     | —                             |
| geog      | GEOGRAPHY(Point,4326)   |     | Rutas y mapas.                |
| precision | Integer(m)              |     | Medir fiabilidad del punto.   |
| fuente    | ENUM(USUARIO,GPS,SIG,OPERADOR) |     | Calidad del dato.             |

---

### 5) Clasificación
Árbol de categorías/temas.

| Campo     | Tipo                           | Req | Descripción / Por qué             |
|----------|--------------------------------|-----|---------------------------------|
| id       | UUID                            | ✔️  | —                               |
| codigo   | String(50)                      | ✔️  | Referencia estable p/ETL e informes. |
| nombre   | String(120)                     |     | Catálogo visible.               |
| descripcion | String(255)                  |     | Ayuda a agentes.               |
| tipo     | ENUM(CATEGORIA,SUBCATEGORIA)    | ✔️  | Jerarquía.                     |
| padreId  | UUID(FK Clasificacion)          |     | Árbol N-niveles.               |

---

### 6) ReglaClasificacion
Reglas que asignan categorías, prioridades y SLAs.

| Campo          | Tipo                         | Req | Descripción / Por qué                   |
|---------------|-------------------------------|-----|---------------------------------------|
| id            | UUID                           | ✔️  | —                                     |
| nombre        | String(120)                   | ✔️  | Gestión funcional.                    |
| expresion     | Text                           | ✔️  | DSL/SpEL/JSON lógico.                 |
| prioridad     | Integer                        | ✔️  | Orden de evaluación.                  |
| activa        | Boolean                        |     | Control de despliegue.                |
| clasificacionId | UUID                         |     | Acción de clasificación.              |
| condiciones   | JSONB                          |     | Parámetros arbitrarios (palabras clave, canal, zona). |
| fuente        | ENUM(MANUAL,MOTOR_REGLAS,LLM) | ✔️  | Trazabilidad de decisión.             |
| vigenciaDesde/Hasta | Instant                  |     | Cambios de normativa/campañas.       |
| version       | Integer                        |     | Governance del catálogo de reglas.    |

