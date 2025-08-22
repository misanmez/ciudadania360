// FlujoRequest.java
package com.ciudadania360.subsistematramitacion.application.dto.flujo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlujoRequest {
    private String nombre;
    private String descripcion;
    private Boolean activo;
    private String tipo;
    private Integer slaHoras;
    private String pasosDefinition;
}
