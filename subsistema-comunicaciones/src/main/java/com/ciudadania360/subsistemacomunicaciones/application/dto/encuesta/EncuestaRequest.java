package com.ciudadania360.subsistemacomunicaciones.application.dto.encuesta;

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
public class EncuestaRequest {
    private String titulo;
    private String descripcion;
    private String preguntas;
    private String estado;
    private String audiencia;
    private Instant fechaInicio;
    private Instant fechaFin;
    private UUID vinculadaSolicitudId;
}
