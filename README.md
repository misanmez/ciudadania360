# subsistema-videoconferencia

## Propósito
Planifica y atiende videollamadas (soporte al ciudadano/teleasistencia), integra plataformas externas (Teams/Meet/Webex), y recoge evidencias (grabación, calidad de conexión) para analítica y mejora del servicio.

## Entidades y atributos

### 1) Sesion
Sesión técnica de videoconferencia.

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|----------------------|
| id | UUID | ✔️ | Identificador único de la sesión. |
| solicitudId | UUID |  | Contexto de atención. |
| fechaInicio / fechaFin | Instant |  | Tiempos reales de la sesión. |
| estado | ENUM(PLANIFICADA,EN_CURSO,FINALIZADA,CANCELADA) | ✔️ | Seguimiento operativo. |
| plataforma | ENUM(TEAMS,MEET,WEBEX,OTRA) | ✔️ | Plataforma utilizada. |
| enlace | String | ✔️ | Invitación para unirse a la sesión. |
| codigoAcceso | String |  | Seguridad de la sesión. |
| grabacionUri | String |  | Evidencia / QA. |
| motivo | String |  | Propósito de la videollamada. |
| operadorId | String |  | Agente responsable de la sesión. |

### 2) Dispositivo
Contexto técnico del participante.

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|----------------------|
| id | UUID | ✔️ | Identificador del dispositivo. |
| ciudadanoId | UUID |  | Usuario/participante. |
| tipo | ENUM(MOVIL,TABLET,PORTATIL,KIOSKO) | ✔️ | Tipo de dispositivo. |
| sistemaOperativo | String | ✔️ | Compatibilidad del dispositivo. |
| navegador | String |  | Opcional para diagnóstico. |
| version | String |  | Versión del sistema o navegador. |
| capacidadVideo | ENUM(SD,HD,FHD) |  | Calidad de transmisión. |
| microfono / camara | Boolean |  | Comprobación de disponibilidad. |
| red | ENUM(WIFI,4G,5G,ETHERNET) |  | Estabilidad de la conexión. |
| pruebaRealizada | Boolean |  | Onboarding digital previo. |
| ultimoCheck | Instant |  | Última verificación para soporte y QA. |

### 3) CitaVideollamada
Compromiso de atención agendado.

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|----------------------|
| id | UUID | ✔️ | Identificador único de la cita. |
| sesionId | UUID | ✔️ | Enlace a la sesión técnica. |
| ciudadanoId | UUID |  | Usuario atendido. |
| empleadoId | String | ✔️ | Profesional asignado. |
| fechaProgramadaInicio / Fin | Instant | ✔️ | Ventana agendada para la cita. |
| estado | ENUM(PENDIENTE,CONFIRMADA,REPROGRAMADA,CANCELADA,ATENDIDA) | ✔️ | Seguimiento y métricas. |
| canalNotificacion | ENUM(EMAIL,SMS,PUSH) |  | Recordatorios automáticos. |
| notas | Text |  | Información adicional. |

### 4) Planificacion
Disponibilidad y reglas de slots para empleados.

| Campo | Tipo | Req | Descripción / Por qué |
|-------|------|-----|----------------------|
| id | UUID | ✔️ | Identificador único de la planificación. |
| empleadoId | String | ✔️ | Propietario de la agenda. |
| franjaInicio / franjaFin | Instant | ✔️ | Ventanas de disponibilidad. |
| periodicidad | ENUM(UNICA,DIARIA,SEMANAL,MENSUAL) | ✔️ | Repetición de slots. |
| capacidadSlots | Integer |  | Gestión de overbooking o colisiones. |
| zonaHoraria | String | ✔️ | Multi-TZ si aplica. |
| reglasConflictos | JSONB |  | Evita solapes en la agenda. |
| diasExcluidos | JSONB |  | Festivos o bloqueos. |
| disponibilidad | ENUM(ACTIVA,PAUSADA) | ✔️ | Control operativo de la agenda. |

