
package com.ciudadania360.subsistemaciudadano.application.dto.ubicacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UbicacionResponse {
    private UUID id;
    private String direccion;
    private String municipio;
    private String provincia;
    private String cp;
    private Double lat;
    private Double lon;
    private Integer precision;
    private String fuente;
}