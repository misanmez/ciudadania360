package com.ciudadania360.subsistemainformacion.application.dto.recomendacion;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecomendacionResponse {
    private UUID id;
    private String titulo;
    private String detalle;
}
