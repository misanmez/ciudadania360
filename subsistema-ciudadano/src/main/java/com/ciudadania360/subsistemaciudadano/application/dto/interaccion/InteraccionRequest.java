package com.ciudadania360.subsistemaciudadano.application.dto.interaccion;

import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InteraccionRequest {
    private UUID solicitudId;    // ID de la solicitud asociada
    private UUID ciudadanoId;    // ID del empleado que genera la interacción
    private UUID empleadoResponsableId; // ID del empleado responsable de la interacción
    private String canal;        // Canal de comunicación (web, app, presencial, teléfono, redes sociales)
    private Instant fecha;       // Fecha y hora de la interacción
    private String agente;       // Nombre o identificador del agente que gestiona la interacción
    private String mensaje;      // Contenido de la interacción
    private String adjuntoUri;   // URI del archivo adjunto
    private String visibilidad;  // Pública o privada
}
