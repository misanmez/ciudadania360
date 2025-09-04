package com.ciudadania360.subsistemaciudadano.application.dto.ciudadano;

import com.ciudadania360.subsistemaciudadano.application.dto.consentimiento.ConsentimientoRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.direccion.DireccionRequest;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;
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

    // Nuevos campos relacionados con subsistema ciudadano ampliado
    private List<DireccionRequest> direcciones; // lista de direcciones asociadas
    private List<ConsentimientoRequest> consentimientos; // historial de consentimientos
}
