-- Crear esquemas
CREATE SCHEMA IF NOT EXISTS shared AUTHORIZATION ciudadania;
CREATE SCHEMA IF NOT EXISTS ciudadano AUTHORIZATION ciudadania;
CREATE SCHEMA IF NOT EXISTS tramitacion AUTHORIZATION ciudadania;
CREATE SCHEMA IF NOT EXISTS comunicaciones AUTHORIZATION ciudadania;
CREATE SCHEMA IF NOT EXISTS roles AUTHORIZATION ciudadania;
CREATE SCHEMA IF NOT EXISTS informacion AUTHORIZATION ciudadania;
CREATE SCHEMA IF NOT EXISTS videoconferencia AUTHORIZATION ciudadania;
CREATE SCHEMA IF NOT EXISTS ia AUTHORIZATION ciudadania;
CREATE SCHEMA IF NOT EXISTS interno AUTHORIZATION ciudadania;

-- Privilegios básicos
GRANT USAGE ON SCHEMA shared TO ciudadania;
GRANT USAGE ON SCHEMA ciudadano TO ciudadania;
GRANT USAGE ON SCHEMA tramitacion TO ciudadania;
GRANT USAGE ON SCHEMA comunicaciones TO ciudadania;
GRANT USAGE ON SCHEMA roles TO ciudadania;
GRANT USAGE ON SCHEMA informacion TO ciudadania;
GRANT USAGE ON SCHEMA videoconferencia TO ciudadania;
GRANT USAGE ON SCHEMA ia TO ciudadania;
GRANT USAGE ON SCHEMA interno TO ciudadania;

GRANT ALL PRIVILEGES ON SCHEMA shared TO ciudadania;
GRANT ALL PRIVILEGES ON SCHEMA ciudadano TO ciudadania;
GRANT ALL PRIVILEGES ON SCHEMA tramitacion TO ciudadania;
GRANT ALL PRIVILEGES ON SCHEMA comunicaciones TO ciudadania;
GRANT ALL PRIVILEGES ON SCHEMA roles TO ciudadania;
GRANT ALL PRIVILEGES ON SCHEMA informacion TO ciudadania;
GRANT ALL PRIVILEGES ON SCHEMA videoconferencia TO ciudadania;
GRANT ALL PRIVILEGES ON SCHEMA ia TO ciudadania;
GRANT ALL PRIVILEGES ON SCHEMA interno TO ciudadania;

CREATE EXTENSION IF NOT EXISTS postgis;