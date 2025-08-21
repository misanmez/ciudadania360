package com.ciudadania360.gestionrolespermisos.application.dto.permiso;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermisoRequest {
    private String codigo;
    private String nombre;
    private String descripcion;
    private String scope;
    private String recurso;
    private String accion;
    private Boolean activo;
}
