-- V2__add_agente_asignado.sql
ALTER TABLE ciudadano.solicitud
ADD COLUMN IF NOT EXISTS agente_asignado varchar(255);

