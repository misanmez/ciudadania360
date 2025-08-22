// ContrataRequest.java
package com.ciudadania360.subsistematramitacion.application.dto.contrata;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContrataRequest {
    private String nombre;
    private String cif;
}
