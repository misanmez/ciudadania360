package com.ciudadania360.gestionrolespermisos.application.dto.rol;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolResponse {
    private UUID id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String nivel;
    private Boolean activo;
}
