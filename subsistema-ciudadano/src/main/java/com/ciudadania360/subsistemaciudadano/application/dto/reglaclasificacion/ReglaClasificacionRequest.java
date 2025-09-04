package com.ciudadania360.subsistemaciudadano.application.dto.reglaclasificacion;

import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReglaClasificacionRequest {
    private String nombre;           // Nombre de la regla
    private String expresion;        // Expresión lógica de la regla
    private Integer prioridad;       // Prioridad de la regla
    private Boolean activa;          // Estado activo/inactivo
    private UUID clasificacionId;    // Clasificación asociada
    private String condiciones;      // Condiciones adicionales en JSON
    private String fuente;           // Fuente de datos de la regla
    private Instant vigenciaDesde;   // Fecha desde la que es válida
    private Instant vigenciaHasta;   // Fecha hasta la que es válida
}
