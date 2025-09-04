package com.ciudadania360.subsistemaciudadano.application.dto.encuesta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncuestaResponse {
    private UUID id;
    private UUID solicitudId;
    private String tipo;
    private String estado;
    private Instant fechaEnvio;
    private Instant fechaRespuesta;
    private String respuestas;
    private String metadata;
    private Long version;  // Para control de concurrencia
}
