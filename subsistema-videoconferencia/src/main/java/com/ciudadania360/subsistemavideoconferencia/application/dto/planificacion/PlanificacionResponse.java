package com.ciudadania360.subsistemavideoconferencia.application.dto.planificacion;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanificacionResponse {
    private UUID id;
    private String nombre;
    private String descripcion;
}
