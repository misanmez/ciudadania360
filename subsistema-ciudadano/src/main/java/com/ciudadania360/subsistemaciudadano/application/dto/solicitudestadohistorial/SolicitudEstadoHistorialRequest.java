package com.ciudadania360.subsistemaciudadano.application.dto.solicitudestadohistorial;

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
public class SolicitudEstadoHistorialRequest {
    private UUID solicitudId;
    private String estadoAnterior;
    private String estadoNuevo;
    private Instant fechaCambio;
    private String agente;
    private String metadata;
}
