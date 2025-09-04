package com.ciudadania360.subsistemaciudadano.application.dto.notificacion;

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
public class NotificacionRequest {
    private UUID solicitudId;
    private String canal;
    private Instant fechaEnvio;
    private String estado;
    private String mensaje;
    private String metadata;
}
