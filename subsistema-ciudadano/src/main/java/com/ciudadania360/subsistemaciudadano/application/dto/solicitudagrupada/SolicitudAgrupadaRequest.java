package com.ciudadania360.subsistemaciudadano.application.dto.solicitudagrupada;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudAgrupadaRequest {
    private UUID solicitudPadreId;
    private UUID solicitudHijaId;
    private String metadata;
}
