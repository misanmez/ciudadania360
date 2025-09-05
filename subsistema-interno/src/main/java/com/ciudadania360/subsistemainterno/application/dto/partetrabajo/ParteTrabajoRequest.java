package com.ciudadania360.subsistemainterno.application.dto.partetrabajo;

import lombok.*;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParteTrabajoRequest {

    private String titulo;
    private String descripcion;
    private String estado;
    private String prioridad;
    private Date fechaInicio;
    private Date fechaFin;
    private UUID empleadoAsignadoId;
    private UUID solicitudId;
    private String metadata;
}
