package com.ciudadania360.subsistemacomunicaciones.application.dto.comunicacion;

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
public class ComunicacionResponse {
    private UUID id;
    private String asunto;
    private String cuerpo;
    private String canal;
    private String estado;
    private String destinatario;
    private UUID ciudadanoId;
    private Instant programadaPara;
}
