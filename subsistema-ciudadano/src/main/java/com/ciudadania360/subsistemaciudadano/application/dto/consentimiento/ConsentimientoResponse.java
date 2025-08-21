package com.ciudadania360.subsistemaciudadano.application.dto.consentimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsentimientoResponse {

    private UUID id;
    private UUID ciudadanoId;
    private String tipo;
    private Boolean otorgado;
}
