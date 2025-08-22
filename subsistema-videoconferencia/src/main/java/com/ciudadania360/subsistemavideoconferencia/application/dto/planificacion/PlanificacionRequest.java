package com.ciudadania360.subsistemavideoconferencia.application.dto.planificacion;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanificacionRequest {
    private String nombre;
    private String descripcion;
}
