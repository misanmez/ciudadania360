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
public class EncuestaRequest {
    private UUID solicitudId;         // ID de la solicitud asociada
    private String tipo;              // Tipo de encuesta
    private String estado;            // Estado actual de la encuesta (ENVIADA, PENDIENTE, COMPLETADA)
    private Instant fechaEnvio;       // Fecha en la que se envió la encuesta
    private Instant fechaRespuesta;   // Fecha de recepción de la respuesta
    private String respuestas;        // JSON con las respuestas
    private String metadata;          // Datos adicionales en formato JSON
}
