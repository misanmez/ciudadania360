-- V3__fix_ubicacion_lat_lon_types.sql
ALTER TABLE ciudadano.ubicacion
  ALTER COLUMN lat TYPE float(53),
  ALTER COLUMN lon TYPE float(53);

