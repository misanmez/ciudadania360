package com.ciudadania360.subsistemainformacion.application.dto.indicador;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndicadorRequest {
    private String codigo;
    private String nombre;
    private String descripcion;
    private String definicionCalculo;
    private String frecuencia;
    private String unidad;
    private String responsable;
    private Boolean kpi;
    private String origenDatos;
    private UUID datasetId;
}

