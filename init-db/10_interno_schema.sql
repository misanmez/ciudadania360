-- V1__init_interno.sql
-- Migración completa para el subsistema interno

-- Crear esquemas
CREATE SCHEMA IF NOT EXISTS shared;
CREATE SCHEMA IF NOT EXISTS interno;

-- ------------------------
-- Esquema shared
-- ------------------------
-- Tabla empleado (solo referencia a departamento_id, sin FK directa)
CREATE TABLE IF NOT EXISTS shared.empleado (
  id uuid PRIMARY KEY,
  nombre varchar(100) NOT NULL,
  apellidos varchar(150) NOT NULL,
  email varchar(150) NOT NULL UNIQUE,
  telefono varchar(30),
  departamento_id uuid, -- solo UUID, sin FK directa
  rol varchar(50) NOT NULL,
  metadata jsonb,
  version bigint DEFAULT 0
);

-- ------------------------
-- Esquema interno
-- ------------------------
-- Tabla departamento
CREATE TABLE interno.departamento (
  id uuid PRIMARY KEY,
  nombre varchar(100) NOT NULL UNIQUE,
  descripcion varchar(255),
  version bigint DEFAULT 0
);

-- Tabla parte_trabajo
CREATE TABLE interno.parte_trabajo (
  id uuid PRIMARY KEY,
  titulo varchar(150) NOT NULL,
  descripcion text,
  estado varchar(50) NOT NULL,
  prioridad varchar(20),
  fecha_inicio timestamp,
  fecha_fin timestamp,
  empleado_id uuid,
  metadata jsonb,
  solicitud_id uuid,
  version bigint DEFAULT 0,
  CONSTRAINT fk_parte_trabajo_empleado FOREIGN KEY(empleado_id) REFERENCES shared.empleado(id)
  -- nota: solicitud_id es referencia a ciudadano.solicitud(id), sin FK directa para flexibilidad
);

-- Tabla adjunto
CREATE TABLE interno.adjunto (
  id uuid PRIMARY KEY,
  nombre_archivo varchar(255) NOT NULL,
  tipo varchar(50),
  tamanyo bigint,
  ruta varchar(500),
  metadata jsonb,
  parte_trabajo_id uuid,
  version bigint DEFAULT 0,
  CONSTRAINT fk_adjunto_parte_trabajo FOREIGN KEY(parte_trabajo_id) REFERENCES interno.parte_trabajo(id)
);

-- Crear índices
CREATE INDEX idx_parte_trabajo_empleado_id ON interno.parte_trabajo(empleado_id);
CREATE INDEX idx_parte_trabajo_solicitud_id ON interno.parte_trabajo(solicitud_id);
CREATE INDEX idx_adjunto_parte_trabajo_id ON interno.adjunto(parte_trabajo_id);
CREATE INDEX idx_departamento_nombre ON interno.departamento(nombre);
