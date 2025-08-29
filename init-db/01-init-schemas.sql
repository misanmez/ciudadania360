-- Crear esquemas
CREATE SCHEMA IF NOT EXISTS ciudadano AUTHORIZATION ciudadania;
CREATE SCHEMA IF NOT EXISTS tramitacion AUTHORIZATION ciudadania;
CREATE SCHEMA IF NOT EXISTS comunicaciones AUTHORIZATION ciudadania;
CREATE SCHEMA IF NOT EXISTS roles AUTHORIZATION ciudadania;
CREATE SCHEMA IF NOT EXISTS informacion AUTHORIZATION ciudadania;
CREATE SCHEMA IF NOT EXISTS videoconferencia AUTHORIZATION ciudadania;

-- Privilegios básicos
GRANT USAGE ON SCHEMA ciudadano TO ciudadania;
GRANT USAGE ON SCHEMA tramitacion TO ciudadania;
GRANT USAGE ON SCHEMA comunicaciones TO ciudadania;
GRANT USAGE ON SCHEMA roles TO ciudadania;
GRANT USAGE ON SCHEMA informacion TO ciudadania;
GRANT USAGE ON SCHEMA videoconferencia TO ciudadania;

GRANT ALL PRIVILEGES ON SCHEMA ciudadano TO ciudadania;
GRANT ALL PRIVILEGES ON SCHEMA tramitacion TO ciudadania;
GRANT ALL PRIVILEGES ON SCHEMA comunicaciones TO ciudadania;
GRANT ALL PRIVILEGES ON SCHEMA roles TO ciudadania;
GRANT ALL PRIVILEGES ON SCHEMA informacion TO ciudadania;
GRANT ALL PRIVILEGES ON SCHEMA videoconferencia TO ciudadania;

CREATE EXTENSION IF NOT EXISTS postgis;