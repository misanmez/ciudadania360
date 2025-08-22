package com.ciudadania360.subsistemainformacion.application.dto.dataset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatasetResponse {
    private UUID id;
    private String nombre;
    private String descripcion;
    private String fuente;
    private String esquema;
    private String periodicidad;
    private String licencia;
    private String urlPortalDatos;
    private String formato;
    private String responsable;
    private Instant ultimaActualizacion;
}
