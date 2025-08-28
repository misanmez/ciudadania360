-- Script de inicialización de esquemas para Ciudadanía 360
-- Este script crea todos los esquemas necesarios para los subsistemas

-- Esquema para subsistema ciudadano
CREATE SCHEMA IF NOT EXISTS ciudadano;

-- Esquema para subsistema tramitación
CREATE SCHEMA IF NOT EXISTS tramitacion;

-- Esquema para subsistema comunicaciones
CREATE SCHEMA IF NOT EXISTS comunicaciones;

-- Esquema para subsistema videoconferencia
CREATE SCHEMA IF NOT EXISTS videoconferencia;

-- Esquema para subsistema información
CREATE SCHEMA IF NOT EXISTS informacion;

-- Esquema para gestión de roles y permisos
CREATE SCHEMA IF NOT EXISTS seguridad;

-- Esquema para subsistema IA (futuro)
CREATE SCHEMA IF NOT EXISTS ia;

-- Configurar permisos básicos
GRANT USAGE ON SCHEMA ciudadano TO postgres;
GRANT USAGE ON SCHEMA tramitacion TO postgres;
GRANT USAGE ON SCHEMA comunicaciones TO postgres;
GRANT USAGE ON SCHEMA videoconferencia TO postgres;
GRANT USAGE ON SCHEMA informacion TO postgres;
GRANT USAGE ON SCHEMA seguridad TO postgres;
GRANT USAGE ON SCHEMA ia TO postgres;

-- Habilitar extensión PostGIS si no está habilitada
CREATE EXTENSION IF NOT EXISTS postgis;
