-- =========================
-- Otorgar permisos al usuario 'ciudadania'
-- =========================

-- Dar todos los privilegios sobre la base de datos ciudadania360
GRANT ALL PRIVILEGES ON DATABASE ciudadania360 TO ciudadania;

-- Dar permisos sobre todos los esquemas, tablas y secuencias existentes
DO
$$
DECLARE
    r RECORD;
BEGIN
    -- Esquemas
    FOR r IN
        SELECT schema_name
        FROM information_schema.schemata
        WHERE schema_name NOT IN ('pg_catalog', 'information_schema')
    LOOP
        EXECUTE format('GRANT ALL PRIVILEGES ON SCHEMA %I TO ciudadania;', r.schema_name);
    END LOOP;

    -- Tablas
    FOR r IN
        SELECT table_schema, table_name
        FROM information_schema.tables
        WHERE table_schema NOT IN ('pg_catalog', 'information_schema')
    LOOP
        EXECUTE format('GRANT ALL PRIVILEGES ON TABLE %I.%I TO ciudadania;', r.table_schema, r.table_name);
    END LOOP;

    -- Secuencias
    FOR r IN
        SELECT sequence_schema, sequence_name
        FROM information_schema.sequences
        WHERE sequence_schema NOT IN ('pg_catalog', 'information_schema')
    LOOP
        EXECUTE format('GRANT ALL PRIVILEGES ON SEQUENCE %I.%I TO ciudadania;', r.sequence_schema, r.sequence_name);
    END LOOP;
END
$$;
