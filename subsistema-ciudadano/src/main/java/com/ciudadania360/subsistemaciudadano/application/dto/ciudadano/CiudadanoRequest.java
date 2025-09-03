package com.ciudadania360.subsistemaciudadano.application.dto.ciudadano;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CiudadanoRequest {

    @NotBlank(message = "El NIF/NIE es obligatorio")
    private String nifNie;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @Email(message = "Email inválido")
    private String email;

    private String telefono;
    private String canalPreferido;
    private String direccionPostal;
    private UUID ubicacionId;

    @NotNull(message = "Consentimiento LOPD obligatorio")
    private Boolean consentimientoLOPD;

    private String estado;
    private String externalId;
    private String metadata;
}
