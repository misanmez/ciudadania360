package com.ciudadania360.subsistemaciudadano.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DireccionRequest {
    private UUID ciudadanoId;
    private String via;
    private String numero;
    private String cp;
    private String municipio;
    private String provincia;
    private Double lat;
    private Double lon;
    private Boolean principal;
}
