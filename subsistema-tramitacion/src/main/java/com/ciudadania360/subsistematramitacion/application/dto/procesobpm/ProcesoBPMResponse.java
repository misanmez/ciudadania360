// ProcesoBPMResponse.java
package com.ciudadania360.subsistematramitacion.application.dto.procesobpm;

import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcesoBPMResponse {
    private UUID id;
    private String engineInstanceId;
    private String definitionKey;
    private UUID businessKey;
    private String estado;
    private Instant inicio;
    private Instant fin;
    private String variables;
    private String iniciador;
}
