package com.ciudadania360.gestionrolespermisos.application.dto.rol;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolRequest {
    private String codigo;
    private String nombre;
    private String descripcion;
    private String nivel;
    private Boolean activo;
}