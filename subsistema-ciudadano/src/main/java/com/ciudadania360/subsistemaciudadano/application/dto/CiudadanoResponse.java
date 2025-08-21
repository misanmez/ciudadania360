// CiudadanoResponse.java
package com.ciudadania360.subsistemaciudadano.application.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CiudadanoResponse {
    private UUID id;
    private String nifNie;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String canalPreferido;
    private String direccionPostal;
    private UUID ubicacionId;
    private Boolean consentimientoLOPD;
    private String estado;
    private String externalId;
    private String metadata;
}
