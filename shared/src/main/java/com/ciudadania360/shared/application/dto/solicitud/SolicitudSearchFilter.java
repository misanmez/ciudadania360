package com.ciudadania360.shared.application.dto.solicitud;

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
public class SolicitudSearchFilter {
    private UUID ciudadanoId;
    private String estado;
    private String prioridad;
    private Instant fechaDesde;
    private Instant fechaHasta;
    private String agenteAsignado;
}
