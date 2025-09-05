package com.ciudadania360.subsistemainterno.application.dto.empleado;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoRequest {

    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private UUID departamentoId;
    private String rol;
    private String metadata;
}
