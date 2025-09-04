package com.ciudadania360.subsistemaciudadano.application.dto.clasificacion;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClasificacionRequest {
    private String codigo;        // código único de la clasificación
    private String nombre;
    private String descripcion;
    private String tipo;          // tipo de clasificación
    private UUID padreId;         // referencia a la clasificación padre
}
