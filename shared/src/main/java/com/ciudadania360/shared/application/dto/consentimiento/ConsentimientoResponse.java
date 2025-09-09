package com.ciudadania360.shared.application.dto.consentimiento;

import lombok.*;
import java.time.Instant;
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
    private Instant fechaOtorgamiento; // Fecha de otorgamiento reflejada en la respuesta
    private Long version;              // Versión para control optimista
}
