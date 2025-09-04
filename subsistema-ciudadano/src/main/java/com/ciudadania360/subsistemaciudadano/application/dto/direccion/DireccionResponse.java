package com.ciudadania360.subsistemaciudadano.application.dto.direccion;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DireccionResponse {
    private UUID id;            // ID de la dirección
    private UUID ciudadanoId;   // ID del ciudadano al que pertenece
    private String via;         // Calle
    private String numero;      // Número
    private String cp;          // Código postal
    private String municipio;   // Municipio
    private String provincia;   // Provincia
    private Double lat;         // Latitud
    private Double lon;         // Longitud
    private Boolean principal;  // Dirección principal
    private Long version;       // Versión para control optimista
}
