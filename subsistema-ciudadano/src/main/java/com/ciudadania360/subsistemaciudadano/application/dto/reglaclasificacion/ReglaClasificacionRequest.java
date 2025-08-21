
package com.ciudadania360.subsistemaciudadano.application.dto.reglaclasificacion;

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
public class ReglaClasificacionRequest {
    private String nombre;
    private String expresion;
    private Integer prioridad;
    private Boolean activa;
    private UUID clasificacionId;
    private String condiciones;
    private String fuente;
    private Instant vigenciaDesde;
    private Instant vigenciaHasta;
}