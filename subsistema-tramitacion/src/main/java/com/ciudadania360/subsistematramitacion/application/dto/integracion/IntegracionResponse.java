// IntegracionResponse.java
package com.ciudadania360.subsistematramitacion.application.dto.integracion;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntegracionResponse {
    private UUID id;
    private String sistema;
    private String tipo;
    private String endpoint;
}
