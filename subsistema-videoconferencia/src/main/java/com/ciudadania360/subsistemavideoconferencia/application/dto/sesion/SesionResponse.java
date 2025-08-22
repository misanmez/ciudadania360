package com.ciudadania360.subsistemavideoconferencia.application.dto.sesion;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SesionResponse {
    private UUID id;
    private UUID solicitudId;
    private Instant fechaInicio;
    private Instant fechaFin;
    private String estado;
    private String plataforma;
    private String enlace;
    private String codigoAcceso;
    private String grabacionUri;
    private String motivo;
    private String operadorId;
}