package com.ciudadania360.subsistemaciudadano.application.dto.clasificacion;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClasificacionRequest {
    private String nombre;
    private String descripcion;
}
