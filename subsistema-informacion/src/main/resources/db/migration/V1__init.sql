-- V1__init_informacion.sql
CREATE SCHEMA IF NOT EXISTS informacion;

CREATE TABLE IF NOT EXISTS informacion.dataset (
  id uuid PRIMARY KEY,
  nombre varchar(255),
  descripcion text,
  fuente varchar(255),
  esquema jsonb,
  periodicidad varchar(255),
  licencia varchar(255),
  urlPortalDatos varchar(255),
  formato varchar(255),
  responsable varchar(255),
  ultimaActualizacion timestamp,
  version bigint
);


CREATE TABLE informacion.duda (
    id UUID PRIMARY KEY,
    pregunta TEXT NOT NULL,
    respuesta TEXT
);

CREATE TABLE IF NOT EXISTS informacion.indicador (
  id uuid PRIMARY KEY,
  codigo varchar(255),
  nombre varchar(255),
  descripcion varchar(255),
  definicionCalculo varchar(255),
  frecuencia varchar(255),
  unidad varchar(255),
  responsable varchar(255),
  kpi boolean,
  origenDatos varchar(255),
  datasetId uuid,
  version bigint
);

CREATE TABLE IF NOT EXISTS informacion.informacion (
  id uuid PRIMARY KEY,
  titulo varchar(255),
  contenido text,
  etiquetas varchar(255),
  audiencia varchar(255),
  estadoPublicacion varchar(255),
  propietario varchar(255),
  versionNumber integer,
  fechaPublicacion timestamp,
  version bigint
);

CREATE TABLE informacion.instruccion (
    id UUID PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    pasos TEXT
);

CREATE TABLE informacion.recomendacion (
    id UUID PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    detalle TEXT
);

CREATE TABLE IF NOT EXISTS informacion.sugerencia (
  id uuid PRIMARY KEY,
  titulo varchar(255),
  descripcion text,
  ciudadanoId uuid,
  estado varchar(255),
  prioridad varchar(255),
  areaResponsable varchar(255),
  fecha timestamp,
  version bigint
);


