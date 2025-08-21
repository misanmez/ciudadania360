
package com.ciudadania360.subsistemaciudadano.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UbicacionRequest {
    private String direccion;
    private String municipio;
    private String provincia;
    private String cp;
    private Double lat;
    private Double lon;
    private Integer precision;
    private String fuente;
}