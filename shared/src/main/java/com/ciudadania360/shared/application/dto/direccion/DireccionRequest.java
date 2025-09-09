package com.ciudadania360.shared.application.dto.direccion;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DireccionRequest {
    private UUID ciudadanoId;   // ID del ciudadano al que pertenece
    private String via;         // Nombre de la calle o vía
    private String numero;      // Número de la calle
    private String cp;          // Código postal
    private String municipio;   // Municipio
    private String provincia;   // Provincia
    private Double lat;         // Latitud geográfica
    private Double lon;         // Longitud geográfica
    private Boolean principal;  // Indica si es la dirección principal del ciudadano
}
