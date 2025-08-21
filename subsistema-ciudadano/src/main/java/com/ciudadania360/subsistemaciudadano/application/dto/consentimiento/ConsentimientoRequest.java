package com.ciudadania360.subsistemaciudadano.application.dto.consentimiento;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsentimientoRequest {
    private UUID ciudadanoId;
    private String tipo;
    private Boolean otorgado;
}
