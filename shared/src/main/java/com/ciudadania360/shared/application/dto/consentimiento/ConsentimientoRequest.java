package com.ciudadania360.shared.application.dto.consentimiento;

import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsentimientoRequest {
    private UUID ciudadanoId;          // ID del ciudadano al que pertenece
    private String tipo;               // Tipo de consentimiento
    private Boolean otorgado;          // Si está otorgado o no
    private Instant fechaOtorgamiento; // Fecha de otorgamiento del consentimiento
}
