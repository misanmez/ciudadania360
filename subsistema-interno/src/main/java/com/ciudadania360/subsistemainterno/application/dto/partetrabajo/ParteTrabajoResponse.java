package com.ciudadania360.subsistemainterno.application.dto.partetrabajo;

import com.ciudadania360.subsistemainterno.application.dto.empleado.EmpleadoResponse;
import lombok.*;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParteTrabajoResponse {

    private UUID id;
    private String titulo;
    private String descripcion;
    private String estado;
    private String prioridad;
    private Date fechaInicio;
    private Date fechaFin;
    private EmpleadoResponse empleadoAsignado;
    private UUID solicitudId;
    private String metadata;
}
