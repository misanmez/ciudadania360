package com.ciudadania360.subsistemavideoconferencia.application.dto.citavideollamada;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaVideollamadaRequest {
    private UUID sesionId;
    private UUID ciudadanoId;
    private String empleadoId;
    private Instant fechaProgramadaInicio;
    private Instant fechaProgramadaFin;
    private String estado;
    private String canalNotificacion;
    private String notas;
}
