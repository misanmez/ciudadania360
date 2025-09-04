-- V2__init_completo.sql
-- Migración completa para el subsistema de ciudadanos con nuevas entidades

-- Crear esquema
CREATE SCHEMA IF NOT EXISTS ciudadano;

-- Tabla ubicacion
CREATE TABLE ciudadano.ubicacion (
  id uuid PRIMARY KEY,
  direccion varchar(255),
  municipio varchar(120),
  provincia varchar(120),
  cp varchar(10),
  lat double precision,
  lon double precision,
  precision integer,
  fuente varchar(50),
  version bigint DEFAULT 0
);

-- Tabla ciudadano
CREATE TABLE ciudadano.ciudadano (
  id uuid PRIMARY KEY,
  nif_nie varchar(15),
  nombre varchar(100) NOT NULL,
  apellidos varchar(150) NOT NULL,
  email varchar(150),
  telefono varchar(30),
  canal_preferido varchar(50),
  direccion_postal varchar(255),
  ubicacion_id uuid,
  estado varchar(50) NOT NULL DEFAULT 'ACTIVO',
  external_id varchar(255),
  metadata jsonb,
  version bigint DEFAULT 0,
  CONSTRAINT fk_ciudadano_ubicacion FOREIGN KEY(ubicacion_id) REFERENCES ciudadano.ubicacion(id)
);

-- Tabla clasificacion
CREATE TABLE ciudadano.clasificacion (
  id uuid PRIMARY KEY,
  codigo varchar(50) NOT NULL UNIQUE,
  nombre varchar(120),
  descripcion varchar(255),
  tipo varchar(50) NOT NULL,
  padre_id uuid,
  version bigint DEFAULT 0,
  CONSTRAINT fk_clasificacion_padre FOREIGN KEY(padre_id) REFERENCES ciudadano.clasificacion(id)
);

-- Tabla solicitud
CREATE TABLE ciudadano.solicitud (
  id uuid PRIMARY KEY,
  ciudadano_id uuid NOT NULL,
  titulo varchar(150) NOT NULL,
  descripcion text,
  tipo varchar(50) NOT NULL,
  canal_entrada varchar(50) NOT NULL,
  estado varchar(50) NOT NULL DEFAULT 'REGISTRADA',
  prioridad varchar(20),
  clasificacion_id uuid,
  ubicacion_id uuid,
  numero_expediente varchar(255),
  fecha_registro timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_limite_sla timestamp,
  fecha_cierre timestamp,
  score_relevancia numeric(5,2),
  origen varchar(255),
  adjuntos_count integer DEFAULT 0,
  encuesta_enviada boolean DEFAULT false,
  referencia_externa varchar(255),
  metadata jsonb,
  agente_asignado varchar(255),
  version bigint DEFAULT 0,
  CONSTRAINT fk_solicitud_ciudadano FOREIGN KEY(ciudadano_id) REFERENCES ciudadano.ciudadano(id),
  CONSTRAINT fk_solicitud_clasificacion FOREIGN KEY(clasificacion_id) REFERENCES ciudadano.clasificacion(id),
  CONSTRAINT fk_solicitud_ubicacion FOREIGN KEY(ubicacion_id) REFERENCES ciudadano.ubicacion(id)
);

-- Tabla interaccion
CREATE TABLE ciudadano.interaccion (
  id uuid PRIMARY KEY,
  solicitud_id uuid NOT NULL,
  ciudadano_id uuid,
  canal varchar(50) NOT NULL,
  fecha timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  agente varchar(255),
  mensaje text NOT NULL,
  adjunto_uri varchar(255),
  visibilidad varchar(20) NOT NULL DEFAULT 'PUBLICA',
  version bigint DEFAULT 0,
  CONSTRAINT fk_interaccion_solicitud FOREIGN KEY(solicitud_id) REFERENCES ciudadano.solicitud(id),
  CONSTRAINT fk_interaccion_ciudadano FOREIGN KEY(ciudadano_id) REFERENCES ciudadano.ciudadano(id)
);

-- Tabla regla_clasificacion
CREATE TABLE ciudadano.regla_clasificacion (
  id uuid PRIMARY KEY,
  nombre varchar(120) NOT NULL,
  expresion text NOT NULL,
  prioridad integer NOT NULL DEFAULT 0,
  activa boolean NOT NULL DEFAULT true,
  clasificacion_id uuid,
  condiciones jsonb,
  fuente varchar(50) NOT NULL,
  vigencia_desde timestamp,
  vigencia_hasta timestamp,
  version bigint DEFAULT 0,
  CONSTRAINT fk_regla_clasificacion_clasificacion FOREIGN KEY(clasificacion_id) REFERENCES ciudadano.clasificacion(id)
);

-- Tabla consentimiento
CREATE TABLE ciudadano.consentimiento (
  id uuid PRIMARY KEY,
  ciudadano_id uuid NOT NULL,
  tipo varchar(100) NOT NULL,
  otorgado boolean NOT NULL DEFAULT false,
  fecha_otorgamiento timestamp,
  version bigint DEFAULT 0,
  CONSTRAINT fk_consentimiento_ciudadano FOREIGN KEY(ciudadano_id) REFERENCES ciudadano.ciudadano(id)
);

-- Tabla direccion
CREATE TABLE ciudadano.direccion (
  id uuid PRIMARY KEY,
  ciudadano_id uuid NOT NULL,
  via varchar(200),
  numero varchar(20),
  cp varchar(20),
  municipio varchar(100),
  provincia varchar(100),
  lat double precision,
  lon double precision,
  principal boolean DEFAULT false,
  version bigint DEFAULT 0,
  CONSTRAINT fk_direccion_ciudadano FOREIGN KEY(ciudadano_id) REFERENCES ciudadano.ciudadano(id)
);

-- Nueva tabla SolicitudAgrupada
CREATE TABLE ciudadano.solicitud_agrupada (
  id uuid PRIMARY KEY,
  solicitud_padre_id uuid NOT NULL,
  solicitud_hija_id uuid NOT NULL,
  metadata jsonb,
  version bigint DEFAULT 0,
  CONSTRAINT fk_solicitud_agrupada_padre FOREIGN KEY(solicitud_padre_id) REFERENCES ciudadano.solicitud(id),
  CONSTRAINT fk_solicitud_agrupada_hija FOREIGN KEY(solicitud_hija_id) REFERENCES ciudadano.solicitud(id)
);

-- Nueva tabla Notificacion
CREATE TABLE ciudadano.notificacion (
  id uuid PRIMARY KEY,
  solicitud_id uuid NOT NULL,
  canal varchar(50) NOT NULL,
  fecha_envio timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  estado varchar(50) NOT NULL DEFAULT 'PENDIENTE',
  mensaje text,
  metadata jsonb,
  version bigint DEFAULT 0,
  CONSTRAINT fk_notificacion_solicitud FOREIGN KEY(solicitud_id) REFERENCES ciudadano.solicitud(id)
);

-- Nueva tabla Encuesta
CREATE TABLE ciudadano.encuesta (
    id uuid PRIMARY KEY,
    solicitud_id uuid NOT NULL,
    tipo varchar(255) NOT NULL,              -- Tipo de encuesta
    estado varchar(50) NOT NULL,             -- ENVIADA, PENDIENTE, COMPLETADA
    fecha_envio timestamp,
    fecha_respuesta timestamp,
    respuestas jsonb,                        -- JSON con las respuestas
    metadata jsonb,                           -- Datos adicionales
    version bigint DEFAULT 0,
    CONSTRAINT fk_encuesta_solicitud FOREIGN KEY(solicitud_id)
        REFERENCES ciudadano.solicitud(id)
);


-- Nueva tabla SolicitudEstadoHistorial
CREATE TABLE ciudadano.solicitud_estado_historial (
  id uuid PRIMARY KEY,
  solicitud_id uuid NOT NULL,
  estado_anterior varchar(50),
  estado_nuevo varchar(50),
  fecha_cambio timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  agente varchar(255),
  metadata jsonb,
  version bigint DEFAULT 0,
  CONSTRAINT fk_estado_historial_solicitud FOREIGN KEY(solicitud_id) REFERENCES ciudadano.solicitud(id)
);

-- Crear índices
CREATE INDEX idx_solicitud_ciudadano_id ON ciudadano.solicitud(ciudadano_id);
CREATE INDEX idx_solicitud_clasificacion_id ON ciudadano.solicitud(clasificacion_id);
CREATE INDEX idx_solicitud_estado ON ciudadano.solicitud(estado);
CREATE INDEX idx_solicitud_fecha_registro ON ciudadano.solicitud(fecha_registro);
CREATE INDEX idx_interaccion_solicitud_id ON ciudadano.interaccion(solicitud_id);
CREATE INDEX idx_interaccion_fecha ON ciudadano.interaccion(fecha);
CREATE INDEX idx_ciudadano_nif_nie ON ciudadano.ciudadano(nif_nie);
CREATE INDEX idx_ciudadano_email ON ciudadano.ciudadano(email);
CREATE INDEX idx_notificacion_solicitud_id ON ciudadano.notificacion(solicitud_id);
CREATE INDEX idx_encuesta_solicitud_id ON ciudadano.encuesta(solicitud_id);
CREATE INDEX idx_estado_historial_solicitud_id ON ciudadano.solicitud_estado_historial(solicitud_id);
CREATE INDEX idx_solicitud_agrupada_padre_id ON ciudadano.solicitud_agrupada(solicitud_padre_id);
CREATE INDEX idx_solicitud_agrupada_hija_id ON ciudadano.solicitud_agrupada(solicitud_hija_id);
