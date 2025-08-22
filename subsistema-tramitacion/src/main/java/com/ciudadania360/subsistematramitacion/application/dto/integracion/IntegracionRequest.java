// IntegracionRequest.java
package com.ciudadania360.subsistematramitacion.application.dto.integracion;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntegracionRequest {
    private String sistema;
    private String tipo;
    private String endpoint;
}
