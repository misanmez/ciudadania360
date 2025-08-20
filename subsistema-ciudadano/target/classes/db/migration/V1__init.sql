-- V1__init.sql

-- Crear esquema
create schema if not exists ciudadano;

-- Tabla ciudadano
CREATE TABLE IF NOT EXISTS ciudadano.ciudadano (
  id uuid PRIMARY KEY,
  nif_nie varchar(255),
  nombre varchar(255),
  apellidos varchar(255),
  email varchar(255),
  telefono varchar(255),
  canal_preferido varchar(255),
  direccion_postal varchar(255),
  ubicacion_id uuid,
  consentimiento_lopd boolean,
  estado varchar(255),
  external_id varchar(255),
  metadata text,
  solicitudes varchar(255),
  version bigint
);

-- Tabla clasificacion
CREATE TABLE IF NOT EXISTS ciudadano.clasificacion (
  id uuid PRIMARY KEY,
  codigo varchar(255),
  nombre varchar(255),
  descripcion varchar(255),
  tipo varchar(255),
  padre varchar(255),
  hijos varchar(255),
  version bigint
);

-- Tabla consentimiento
create table if not exists ciudadano.consentimiento (
    id uuid primary key,
    ciudadano_id uuid references ciudadano.ciudadano(id),
    tipo varchar(100),
    otorgado boolean
);

-- Tabla direccion
create table if not exists ciudadano.direccion (
    id uuid primary key,
    ciudadano_id uuid references ciudadano.ciudadano(id),
    via varchar(200),
    numero varchar(20),
    cp varchar(20),
    municipio varchar(100),
    provincia varchar(100),
    lat double precision,
    lon double precision,
    principal boolean
);

-- Tabla interaccion
CREATE TABLE IF NOT EXISTS ciudadano.interaccion (
  id uuid PRIMARY KEY,
  solicitud varchar(255),
  ciudadano varchar(255),
  canal varchar(255),
  fecha timestamp,
  agente varchar(255),
  mensaje text,
  adjunto_uri varchar(255),
  visibilidad varchar(255),
  version bigint
);

-- Tabla regla_clasificacion
CREATE TABLE IF NOT EXISTS ciudadano.regla_clasificacion (
  id uuid PRIMARY KEY,
  nombre varchar(255),
  expresion text,
  prioridad integer,
  activa boolean,
  clasificacion_id uuid,
  condiciones text,
  fuente varchar(255),
  vigencia_desde timestamp,
  vigencia_hasta timestamp,
  version bigint
);

-- Tabla solicitud
CREATE TABLE IF NOT EXISTS ciudadano.solicitud (
  id uuid PRIMARY KEY,
  ciudadano varchar(255),
  titulo varchar(255),
  descripcion varchar(255),
  tipo varchar(255),
  canal_entrada varchar(255),
  estado varchar(255),
  prioridad varchar(255),
  clasificacion varchar(255),
  ubicacion varchar(255),
  numero_expediente varchar(255),
  fecha_registro timestamp,
  fecha_limite_sla timestamp,
  fecha_cierre timestamp,
  score_relevancia numeric,
  origen varchar(255),
  adjuntos_count integer,
  encuesta_enviada boolean,
  referencia_externa varchar(255),
  metadata text,
  version bigint
);

-- Tabla ubicacion
CREATE TABLE IF NOT EXISTS ciudadano.ubicacion (
  id uuid PRIMARY KEY,
  direccion varchar(255),
  municipio varchar(255),
  provincia varchar(255),
  cp varchar(255),
  lat numeric,
  lon numeric,
  precision integer,
  fuente varchar(255),
  version bigint
);

-- Foreign keys and indices for ciudadano schema

ALTER TABLE ciudadano.clasificacion ADD COLUMN IF NOT EXISTS padre_id uuid;

ALTER TABLE ciudadano.solicitud ADD COLUMN IF NOT EXISTS ciudadano_id uuid;

ALTER TABLE ciudadano.solicitud ADD COLUMN IF NOT EXISTS clasificacion_id uuid;

ALTER TABLE ciudadano.solicitud ADD COLUMN IF NOT EXISTS ubicacion_id uuid;

ALTER TABLE ciudadano.interaccion ADD COLUMN IF NOT EXISTS solicitud_id uuid;

ALTER TABLE ciudadano.interaccion ADD COLUMN IF NOT EXISTS ciudadano_id uuid;

ALTER TABLE ciudadano.regla_clasificacion ADD COLUMN IF NOT EXISTS clasificacion_id uuid;

ALTER TABLE ciudadano.clasificacion ADD CONSTRAINT fk_clasificacion_padre FOREIGN KEY(padre_id) REFERENCES ciudadano.clasificacion(id);

ALTER TABLE ciudadano.solicitud ADD CONSTRAINT fk_solicitud_ciudadano FOREIGN KEY(ciudadano_id) REFERENCES ciudadano.ciudadano(id);

ALTER TABLE ciudadano.solicitud ADD CONSTRAINT fk_solicitud_clasificacion FOREIGN KEY(clasificacion_id) REFERENCES ciudadano.clasificacion(id);

ALTER TABLE ciudadano.solicitud ADD CONSTRAINT fk_solicitud_ubicacion FOREIGN KEY(ubicacion_id) REFERENCES ciudadano.ubicacion(id);

ALTER TABLE ciudadano.interaccion ADD CONSTRAINT fk_interaccion_solicitud FOREIGN KEY(solicitud_id) REFERENCES ciudadano.solicitud(id);

ALTER TABLE ciudadano.interaccion ADD CONSTRAINT fk_interaccion_ciudadano FOREIGN KEY(ciudadano_id) REFERENCES ciudadano.ciudadano(id);

ALTER TABLE ciudadano.regla_clasificacion ADD CONSTRAINT fk_regla_clasificacion_clasificacion FOREIGN KEY(clasificacion_id) REFERENCES ciudadano.clasificacion(id);

CREATE INDEX IF NOT EXISTS idx_solicitud_ciudadano_id ON ciudadano.solicitud(ciudadano_id);

CREATE INDEX IF NOT EXISTS idx_solicitud_clasificacion_id ON ciudadano.solicitud(clasificacion_id);

CREATE INDEX IF NOT EXISTS idx_interaccion_solicitud_id ON ciudadano.interaccion(solicitud_id);

INSERT INTO ciudadano.ciudadano (id, nif_nie, nombre, apellidos, email, estado) VALUES ('7cfaf1e8-b41d-4599-8014-26fdec12406a','X1234567A','Juan','Perez','juan@example.com','ACTIVO') ON CONFLICT DO NOTHING;

INSERT INTO ciudadano.clasificacion (id, codigo, nombre) VALUES ('b0880483-36d2-4047-8f6a-b51d195c64d0','GEN','General') ON CONFLICT DO NOTHING;

INSERT INTO ciudadano.solicitud (id, ciudadano_id, titulo, descripcion, estado) VALUES ('4ea4540a-e1ed-4f08-ae78-2b8063401c89','7cfaf1e8-b41d-4599-8014-26fdec12406a','Incidencia ejemplo','Descripción de prueba','REGISTRADA') ON CONFLICT DO NOTHING;
