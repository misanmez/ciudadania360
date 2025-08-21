package com.ciudadania360.gestionrolespermisos.application.dto.usuariorol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRolRequest {
    private UUID ciudadanoId;
    private UUID rolId;
    private String asignadoPor;
    private Instant fechaAsignacion;
    private Instant fechaCaducidad;
    private String origen;
    private String observaciones;
}
