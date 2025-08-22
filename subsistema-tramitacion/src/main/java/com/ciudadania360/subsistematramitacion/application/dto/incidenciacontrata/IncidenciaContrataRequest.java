// IncidenciaContrataRequest.java
package com.ciudadania360.subsistematramitacion.application.dto.incidenciacontrata;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidenciaContrataRequest {
    private UUID contrataId;
    private String descripcion;
    private String estado;
}
