package com.ciudadania360.shared.application.dto.empleado;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoResponse {

    private UUID id;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String rol;
    private String metadata;
    private UUID departamentoId;
}
