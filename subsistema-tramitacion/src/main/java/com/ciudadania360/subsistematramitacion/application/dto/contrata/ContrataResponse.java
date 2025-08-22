// ContrataResponse.java
package com.ciudadania360.subsistematramitacion.application.dto.contrata;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContrataResponse {
    private UUID id;
    private String nombre;
    private String cif;
}
