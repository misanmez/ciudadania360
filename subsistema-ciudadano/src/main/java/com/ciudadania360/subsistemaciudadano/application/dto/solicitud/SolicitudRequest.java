package com.ciudadania360.subsistemaciudadano.application.dto.solicitud;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudRequest {

    @NotNull
    private UUID ciudadanoId;

    @NotBlank
    private String titulo;

    private String descripcion;

    @NotBlank
    private String tipo;

    private String canalEntrada;

    private String estado;

    private String prioridad;

    private UUID clasificacionId;

    private UUID ubicacionId;

    private String numeroExpediente;

    private Instant fechaRegistro;

    private Instant fechaLimiteSLA;

    private String origen;

    private Integer adjuntosCount;

    private Boolean encuestaEnviada;

    private String referenciaExterna;

    private String metadata;

    private String agenteAsignado;
}
