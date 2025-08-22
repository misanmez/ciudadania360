package com.ciudadania360.subsistemainformacion.application.dto.dataset;

import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatasetRequest {
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
