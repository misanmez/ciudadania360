package com.ciudadania360.subsistemainterno.application.dto.adjunto;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdjuntoResponse {
    private UUID id;
    private String nombreArchivo;
    private String tipo;
    private Long tamanyo;
    private String ruta;
    private String metadata;
    private UUID parteTrabajoId;
}
