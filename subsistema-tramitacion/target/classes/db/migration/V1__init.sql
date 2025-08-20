-- V1__init_tramitacion.sql
CREATE SCHEMA IF NOT EXISTS tramitacion;

CREATE TABLE IF NOT EXISTS tramitacion.carpeta (
  id uuid PRIMARY KEY,
  solicitud_id uuid,
  nombre varchar(255),
  descripcion varchar(255),
  tipo varchar(255),
  ruta varchar(255),
  permisos text,
  numero_expediente varchar(255),
  estado varchar(255),
  version bigint
);

CREATE TABLE tramitacion.contrata (
    id UUID PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    cif VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS tramitacion.documento (
  id uuid PRIMARY KEY,
  carpeta_id uuid,
  nombre varchar(255),
  tipo_mime varchar(255),
  uri_almacenamiento varchar(255),
  hash varchar(255),
  fecha_subida timestamp,
  origen varchar(255),
  firmado boolean,
  metadatos text,
  version bigint
);

CREATE TABLE IF NOT EXISTS tramitacion.flujo (
  id uuid PRIMARY KEY,
  nombre varchar(255),
  descripcion text,
  activo boolean,
  tipo varchar(255),
  sla_horas integer,
  pasos_definition text,
  version bigint
);

CREATE TABLE tramitacion.incidencia_contrata (
    id UUID PRIMARY KEY,
    contrata_id UUID,
    descripcion TEXT,
    estado VARCHAR(50)
);

CREATE TABLE tramitacion.integracion (
    id UUID PRIMARY KEY,
    sistema VARCHAR(255) NOT NULL,
    tipo VARCHAR(100),
    endpoint VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS tramitacion.paso (
  id uuid PRIMARY KEY,
  flujoId uuid,
  nombre varchar(255),
  orden integer,
  tipo varchar(255),
  responsable_role varchar(255),
  sla_horas integer,
  version bigint
);

CREATE TABLE IF NOT EXISTS tramitacion.proceso_bpm (
  id uuid PRIMARY KEY,
  engine_instance_id varchar(255),
  definition_key varchar(255),
  business_key uuid,
  estado varchar(255),
  inicio timestamp,
  fin timestamp,
  variables text,
  iniciador varchar(255),
  version bigint
);

CREATE TABLE IF NOT EXISTS tramitacion.tarea_bpm (
  id uuid PRIMARY KEY,
  proceso_bpm_id uuid,
  engine_task_id varchar(255),
  nombre varchar(255),
  estado varchar(255),
  assignee varchar(255),
  candidate_group varchar(255),
  dueDate timestamp,
  priority integer,
  variables text,
  created timestamp,
  completed timestamp,
  version bigint
);

ALTER TABLE tramitacion.proceso_bpm ADD COLUMN IF NOT EXISTS business_key uuid;

ALTER TABLE tramitacion.proceso_bpm ADD CONSTRAINT fk_procesobpm_businesskey FOREIGN KEY(business_key) REFERENCES ciudadano.solicitud(id);

CREATE INDEX IF NOT EXISTS idx_procesobpm_business_key ON tramitacion.proceso_bpm(business_key);

