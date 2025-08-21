
package com.ciudadania360.subsistemaciudadano.application.dto.solicitud;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudRequest {
    private UUID ciudadanoId;
    private String titulo;
    private String descripcion;
    private String tipo;
    private String canalEntrada;
    private String estado;
    private String prioridad;
    private UUID clasificacionId;
    private UUID ubicacionId;
    private String numeroExpediente;
    private Instant fechaLimiteSLA;
    private String origen;
    private String referenciaExterna;
    private String metadata;
}