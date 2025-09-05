CREATE SCHEMA IF NOT EXISTS shared;

CREATE TABLE shared.empleado (
  id uuid PRIMARY KEY,
  nombre varchar(150) NOT NULL,
  apellidos varchar(150) NOT NULL,
  email varchar(150) NOT NULL UNIQUE,
  telefono varchar(30),
  departamento_id uuid,
  rol varchar(50) NOT NULL,
  metadata jsonb,
  version bigint DEFAULT 0
);
