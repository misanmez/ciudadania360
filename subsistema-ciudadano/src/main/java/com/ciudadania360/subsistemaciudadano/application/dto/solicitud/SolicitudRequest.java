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

    @NotNull(message = "El ciudadanoId es obligatorio")
    private UUID ciudadanoId;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    private String descripcion;

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    private String canalEntrada;

    private String estado;

    private String prioridad; // ALTA, MEDIA, BAJA

    private UUID clasificacionId;

    private UUID ubicacionId;

    private String numeroExpediente;

    private Instant fechaRegistro;

    private Instant fechaLimiteSLA;

    private Instant fechaCierre;

    private String origen;

    private Integer adjuntosCount;

    private Boolean encuestaEnviada;

    private String referenciaExterna;

    private String metadata;

    private String agenteAsignado;
}
