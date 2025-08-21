
package com.ciudadania360.subsistemaciudadano.application.dto.interaccion;

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
public class InteraccionRequest {
    private UUID solicitudId;
    private UUID ciudadanoId;
    private String canal;
    private Instant fecha;
    private String agente;
    private String mensaje;
    private String adjuntoUri;
    private String visibilidad;
}