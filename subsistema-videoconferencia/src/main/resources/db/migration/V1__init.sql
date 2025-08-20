-- V1__init_videoconferencia.sql
CREATE SCHEMA IF NOT EXISTS videoconferencia;

CREATE TABLE IF NOT EXISTS videoconferencia.cita_videollamada (
  id uuid PRIMARY KEY,
  sesion_id uuid,
  ciudadano_id uuid,
  empleado_id varchar(255),
  fecha_programada_inicio timestamp,
  fecha_programada_fin timestamp,
  estado varchar(255),
  canal_notificacion varchar(255),
  notas text,
  version bigint
);

CREATE TABLE IF NOT EXISTS videoconferencia.dispositivo (
  id uuid PRIMARY KEY,
  ciudadano_id uuid,
  tipo varchar(255),
  sistema_operativo varchar(255),
  navegador varchar(255),
  capacidad_video varchar(255),
  microfono boolean,
  camara boolean,
  red varchar(255),
  prueba_realizada boolean,
  ultimo_check timestamp,
  version bigint
);

CREATE TABLE videoconferencia.planificacion (
    id UUID PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT
);

CREATE TABLE IF NOT EXISTS videoconferencia.sesion (
  id uuid PRIMARY KEY,
  solicitud_id uuid,
  fecha_inicio timestamp,
  fecha_fin timestamp,
  estado varchar(255),
  plataforma varchar(255),
  enlace varchar(255),
  codigo_acceso varchar(255),
  grabacion_uri varchar(255),
  motivo varchar(255),
  operador_id varchar(255),
  version bigint
);

ALTER TABLE videoconferencia.sesion ADD COLUMN IF NOT EXISTS solicitud_id uuid;

ALTER TABLE videoconferencia.sesion ADD CONSTRAINT fk_sesion_solicitud FOREIGN KEY(solicitud_id) REFERENCES ciudadano.solicitud(id);

CREATE INDEX IF NOT EXISTS idx_sesion_solicitud_id ON videoconferencia.sesion(solicitud_id);

ALTER TABLE videoconferencia.cita_videollamada ADD COLUMN IF NOT EXISTS ciudadano_id uuid;

ALTER TABLE videoconferencia.cita_videollamada ADD CONSTRAINT fk_cita_ciudadano FOREIGN KEY(ciudadano_id) REFERENCES ciudadano.ciudadano(id);

CREATE INDEX IF NOT EXISTS idx_cita_ciudadano_id ON videoconferencia.cita_videollamada(ciudadano_id);
