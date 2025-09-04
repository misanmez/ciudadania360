package com.ciudadania360.subsistemaciudadano.application.dto.interaccion;

import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InteraccionResponse {
    private UUID id;             // ID de la interacción
    private UUID solicitudId;    // ID de la solicitud asociada
    private UUID ciudadanoId;    // ID del ciudadano
    private String canal;        // Canal de comunicación
    private Instant fecha;       // Fecha de la interacción
    private String agente;       // Agente asignado
    private String mensaje;      // Contenido
    private String adjuntoUri;   // URI del adjunto
    private String visibilidad;  // Pública o privada
    private Long version;        // Versión para control de concurrencia
}
