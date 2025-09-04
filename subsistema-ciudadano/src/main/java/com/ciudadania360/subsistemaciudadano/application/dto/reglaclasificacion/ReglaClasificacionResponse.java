package com.ciudadania360.subsistemaciudadano.application.dto.reglaclasificacion;

import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReglaClasificacionResponse {
    private UUID id;                 // ID de la regla
    private String nombre;           // Nombre de la regla
    private String expresion;        // Expresión lógica de la regla
    private Integer prioridad;       // Prioridad
    private Boolean activa;          // Activa/inactiva
    private UUID clasificacionId;    // Clasificación asociada
    private String condiciones;      // Condiciones en JSON
    private String fuente;           // Fuente de la regla
    private Instant vigenciaDesde;   // Vigencia desde
    private Instant vigenciaHasta;   // Vigencia hasta
    private Long version;            // Control de versión para concurrencia
}
