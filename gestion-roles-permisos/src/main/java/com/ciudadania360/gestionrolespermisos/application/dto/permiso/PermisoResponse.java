package com.ciudadania360.gestionrolespermisos.application.dto.permiso;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermisoResponse {
    private UUID id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String scope;
    private String recurso;
    private String accion;
    private Boolean activo;
}
