package com.ciudadania360.subsistemacomunicaciones.application.dto.notificacion;

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
public class NotificacionResponse {
    private UUID id;
    private UUID ciudadanoId;
    private String titulo;
    private String mensaje;
    private String canal;
    private String prioridad;
    private String estado;
    private String referencia;
    private Instant fechaEntrega;
}
