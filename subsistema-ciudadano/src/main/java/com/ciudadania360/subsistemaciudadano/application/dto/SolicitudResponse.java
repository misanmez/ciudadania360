
package com.ciudadania360.subsistemaciudadano.application.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudResponse {
    private UUID id;
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
    private Instant fechaRegistro;
    private Instant fechaLimiteSLA;
    private Instant fechaCierre;
    private BigDecimal scoreRelevancia;
    private String origen;
    private Integer adjuntosCount;
    private Boolean encuestaEnviada;
    private String referenciaExterna;
    private String metadata;
    private Long version;
}