package com.ciudadania360.subsistemacomunicaciones.application.dto.respuestaencuesta;

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
public class RespuestaEncuestaResponse {
    private UUID id;
    private UUID encuestaId;
    private UUID ciudadanoId;
    private String respuestas;
    private Integer puntuacion;
    private String comentarios;
    private Instant fecha;
}
