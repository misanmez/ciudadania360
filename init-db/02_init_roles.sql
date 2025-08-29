-- Crear esquema si no existe
create schema if not exists roles;

-- Tabla Permiso
CREATE TABLE IF NOT EXISTS roles.rol (
  id uuid PRIMARY KEY,
  codigo varchar(255),
  nombre varchar(255),
  descripcion varchar(255),
  nivel varchar(255),
  activo boolean,
  version bigint
);

CREATE TABLE IF NOT EXISTS roles.permiso (
  id uuid PRIMARY KEY,
  codigo varchar(255),
  nombre varchar(255),
  descripcion varchar(255),
  scope varchar(255),
  recurso varchar(255),
  accion varchar(255),
  activo boolean,
  version bigint
);

CREATE TABLE IF NOT EXISTS roles.ciudadano_rol (
  id uuid PRIMARY KEY,
  ciudadano_id uuid,
  rol_id uuid,
  asignado_por varchar(255),
  fecha_asignacion timestamp,
  fecha_caducidad timestamp,
  origen varchar(255),
  observaciones text,
  version bigint
);

-- seed roles

INSERT INTO roles.rol (id,codigo,nombre,activo) VALUES ('280121cb-de7c-4055-be82-b6bb0d212033','ADMIN','Administrador',true) ON CONFLICT DO NOTHING;

INSERT INTO roles.rol (id,codigo,nombre,activo) VALUES ('fad300b4-9df1-4667-9b86-9f43eaf259a5','CIUDADANO','Ciudadano',true) ON CONFLICT DO NOTHING;

INSERT INTO roles.rol (id,codigo,nombre,activo) VALUES ('c2c05071-5037-4b41-90fa-a7ebabd0aa9e','GESTOR','Gestor',true) ON CONFLICT DO NOTHING;

