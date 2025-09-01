-- 09_ia_schema.sql
CREATE SCHEMA IF NOT EXISTS ia AUTHORIZATION ciudadania;

-- Tabla opcional: logs de prompt/resultados
CREATE TABLE IF NOT EXISTS ia.prompt_log (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    created_at timestamptz DEFAULT now(),
    request_text text NOT NULL,
    response_text text,
    model text,
    metadata jsonb
);
