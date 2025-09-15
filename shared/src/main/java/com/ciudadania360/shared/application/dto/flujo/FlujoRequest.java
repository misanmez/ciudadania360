package com.ciudadania360.shared.application.dto.flujo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
