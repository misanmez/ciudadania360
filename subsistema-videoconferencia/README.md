# Subsistema Videoconferencia

## Propósito
Este módulo permite **planificar y atender videollamadas** para dar soporte al ciudadano o prestar servicios de teleasistencia.  
Integra plataformas externas (Microsoft Teams, Google Meet, Webex, u otras), y recoge **evidencias técnicas** (grabaciones, calidad de conexión, incidencias técnicas) para analítica y mejora continua del servicio.

---

## Entidades y atributos

### 1) Sesion
Representa la sesión técnica de una videoconferencia.

| Campo             | Tipo                                      | Req | Descripción / Por qué |
|-------------------|-------------------------------------------|-----|-----------------------|
| id                | UUID                                      | ✔️  | Identificador único de la sesión. |
| solicitudId       | UUID                                      |     | Contexto de atención o trámite asociado. |
| fechaInicio / fin | Instant                                   |     | Tiempos reales de inicio y finalización. |
| estado            | ENUM(PLANIFICADA, EN_CURSO, FINALIZADA, CANCELADA) | ✔️  | Permite el seguimiento operativo. |
| plataforma        | ENUM(TEAMS, MEET, WEBEX, OTRA)            | ✔️  | Plataforma externa utilizada. |
| enlace            | String                                    | ✔️  | URL de invitación a la reunión. |
| codigoAcceso      | String                                    |     | Código adicional de seguridad. |
| grabacionUri      | String                                    |     | Referencia a la grabación para QA y auditoría. |
| motivo            | String                                    |     | Razón de la videollamada (soporte, cita, trámite). |
| operadorId        | String                                    |     | Agente o empleado asignado. |

---

### 2) Dispositivo
Describe el contexto técnico del participante (ciudadano).

| Campo            | Tipo                             | Req | Descripción / Por qué |
|------------------|----------------------------------|-----|-----------------------|
| id               | UUID                             | ✔️  | Identificador único. |
| ciudadanoId      | UUID                             |     | Relación con el ciudadano. |
| tipo             | ENUM(MOVIL, TABLET, PORTATIL, KIOSKO) | ✔️  | Tipo de dispositivo usado. |
| sistemaOperativo | String                           | ✔️  | Compatibilidad técnica. |
| navegador        | String                           |     | Navegador en uso. |
| version          | String                           |     | Versión del sistema/navegador. |
| capacidadVideo   | ENUM(SD, HD, FHD)                |     | Calidad de transmisión posible. |
| microfono/camara | Boolean                          |     | Diagnóstico previo de dispositivos. |
| red              | ENUM(WIFI, 4G, 5G, ETHERNET)     |     | Medio de conexión para estabilidad. |
| pruebaRealizada  | Boolean                          |     | Indica si pasó onboarding técnico. |
| ultimoCheck      | Instant                          |     | Última revisión para soporte/QA. |

---

### 3) CitaVideollamada
Compromiso de atención (agenda entre ciudadano y agente).

| Campo                   | Tipo                                         | Req | Descripción / Por qué |
|-------------------------|----------------------------------------------|-----|-----------------------|
| id                      | UUID                                         | ✔️  | Identificador único. |
| sesionId                | UUID                                         | ✔️  | Enlace técnico a la sesión de videollamada. |
| ciudadanoId             | UUID                                         | ✔️  | Identificador del ciudadano. |
| empleadoId              | String                                       | ✔️  | Empleado asignado. |
| fechaProgramadaInicio/Fin | Instant                                    | ✔️  | Franja horaria de la cita. |
| estado                  | ENUM(PENDIENTE, CONFIRMADA, REPROGRAMADA, CANCELADA, ATENDIDA) | ✔️  | Estado de la cita para métricas y gestión de agendas. |
| canalNotificacion       | ENUM(EMAIL, SMS, PUSH)                       |     | Canal usado para recordatorios. |
| notas                   | Text                                         |     | Contexto adicional. |

---

### 4) Planificacion
Define disponibilidad y reglas de slots de agenda para empleados.

| Campo           | Tipo                                  | Req | Descripción / Por qué |
|-----------------|---------------------------------------|-----|-----------------------|
| id              | UUID                                  | ✔️  | Identificador único. |
| empleadoId      | String                                | ✔️  | Propietario de la agenda. |
| franjaInicio/Fin| Instant                               | ✔️  | Ventanas de disponibilidad. |
| periodicidad    | ENUM(UNICA, DIARIA, SEMANAL, MENSUAL) | ✔️  | Patrón de repetición. |
| capacidadSlots  | Integer                               |     | Permite controlar overbooking o colisiones. |
| zonaHoraria     | String                                | ✔️  | Soporte multi-timezone. |
| reglasConflictos| JSONB                                 |     | Políticas para evitar solapes. |
| diasExcluidos   | JSONB                                 |     | Días no laborables o festivos. |
| disponibilidad  | ENUM(ACTIVA, PAUSADA)                 | ✔️  | Estado operativo de la agenda. |

---

## Relación entre entidades
- Una **Planificacion** define la agenda de un empleado.
- De esa planificación se generan **CitasVideollamada**, que se enlazan con **Sesiones** técnicas.
- Cada sesión puede tener asociados **Dispositivos** de los participantes para diagnósticos y soporte.

---

## Datos iniciales
- Ejemplo de planificación preconfigurada para un agente.
- Estado inicial de sesiones y citas como **PLANIFICADA/PENDIENTE**.  

