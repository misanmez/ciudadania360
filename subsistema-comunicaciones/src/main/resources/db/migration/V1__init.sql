-- Crear esquema
create schema if not exists comunicaciones;

-- Tabla comunicacion
CREATE TABLE IF NOT EXISTS comunicaciones.comunicacion (
  id uuid PRIMARY KEY,
  asunto varchar(255),
  cuerpo text,
  canal varchar(255),
  estado varchar(255),
  destinatario varchar(255),
  ciudadano_id uuid,
  programada_para timestamp,
  version bigint
);

-- Tabla encuesta
CREATE TABLE IF NOT EXISTS comunicaciones.encuesta (
  id uuid PRIMARY KEY,
  titulo varchar(255),
  descripcion text,
  preguntas jsonb,
  estado varchar(255),
  audiencia varchar(255),
  fecha_inicio timestamp,
  fecha_fin timestamp,
  vinculada_solicitud_id uuid,
  version bigint
);

-- Tabla notificacion
CREATE TABLE IF NOT EXISTS comunicaciones.notificacion (
  id uuid PRIMARY KEY,
  ciudadano_id uuid,
  titulo varchar(255),
  mensaje varchar(255),
  canal varchar(255),
  prioridad varchar(255),
  estado varchar(255),
  referencia varchar(255),
  fecha_entrega timestamp,
  version bigint
);

-- Tabla respuesta_encuesta
CREATE TABLE IF NOT EXISTS comunicaciones.respuesta_encuesta (
  id uuid PRIMARY KEY,
  encuesta_id uuid,
  ciudadano_id uuid,
  respuestas jsonb,
  puntuacion integer,
  comentarios varchar(255),
  fecha timestamp,
  version bigint
);

-- Tabla suscripcion
create table if not exists comunicaciones.suscripcion (
    id uuid primary key,
    ciudadano_id uuid,
    tema varchar,
    activo boolean
);

ALTER TABLE comunicaciones.encuesta ADD COLUMN IF NOT EXISTS vinculada_solicitud_id uuid;

ALTER TABLE comunicaciones.respuesta_encuesta ADD CONSTRAINT fk_respuesta_encuesta_encuesta FOREIGN KEY(encuesta_id) REFERENCES comunicaciones.encuesta(id);

ALTER TABLE comunicaciones.encuesta ADD CONSTRAINT fk_encuesta_vinculada_solicitud FOREIGN KEY(vinculada_solicitud_id) REFERENCES ciudadano.solicitud(id);

CREATE INDEX IF NOT EXISTS idx_encuesta_vinculada_solicitud_id ON comunicaciones.encuesta(vinculada_solicitud_id);
