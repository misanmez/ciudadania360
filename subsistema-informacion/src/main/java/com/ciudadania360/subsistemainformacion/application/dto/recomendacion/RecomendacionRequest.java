package com.ciudadania360.subsistemainformacion.application.dto.recomendacion;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecomendacionRequest {
    private String titulo;
    private String detalle;
}

