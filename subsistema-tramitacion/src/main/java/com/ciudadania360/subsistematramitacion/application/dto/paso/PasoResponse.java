// PasoResponse.java
package com.ciudadania360.subsistematramitacion.application.dto.paso;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasoResponse {
    private UUID id;
    private UUID flujoId;
    private String nombre;
    private Integer orden;
    private String tipo;
    private String responsableRole;
    private Integer slaHoras;
}
