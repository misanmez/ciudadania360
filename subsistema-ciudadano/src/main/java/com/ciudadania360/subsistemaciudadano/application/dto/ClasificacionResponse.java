package com.ciudadania360.subsistemaciudadano.application.dto;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClasificacionResponse {
    private UUID id;
    private String nombre;
    private String descripcion;
    private Long version;
}
