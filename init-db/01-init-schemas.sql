-- =========================
-- Crear esquemas si no existen y asignar propietario
-- =========================
DO
$$
DECLARE
    esquema TEXT;
    esquemas TEXT[] := ARRAY[
        'shared', 'ciudadano', 'tramitacion', 'comunicaciones',
        'roles', 'informacion', 'videoconferencia', 'ia', 'interno'
    ];
BEGIN
    FOREACH esquema IN ARRAY esquemas LOOP
        EXECUTE format('CREATE SCHEMA IF NOT EXISTS %I AUTHORIZATION ciudadania;', esquema);
        EXECUTE format('GRANT ALL PRIVILEGES ON SCHEMA %I TO ciudadania;', esquema);
        EXECUTE format('GRANT USAGE ON SCHEMA %I TO ciudadania;', esquema);
    END LOOP;
END
$$;

-- =========================
-- Crear extensión PostGIS si no existe
-- =========================
CREATE EXTENSION IF NOT EXISTS postgis;
