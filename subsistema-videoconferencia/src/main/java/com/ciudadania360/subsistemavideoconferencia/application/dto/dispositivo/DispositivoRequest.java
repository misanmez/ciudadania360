package com.ciudadania360.subsistemavideoconferencia.application.dto.dispositivo;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DispositivoRequest {
    private UUID ciudadanoId;
    private String tipo;
    private String sistemaOperativo;
    private String navegador;
    private String capacidadVideo;
    private Boolean microfono;
    private Boolean camara;
    private String red;
    private Boolean pruebaRealizada;
    private Instant ultimoCheck;
}