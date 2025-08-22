// DocumentoResponse.java
package com.ciudadania360.subsistematramitacion.application.dto.documento;

import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentoResponse {
    private UUID id;
    private UUID carpetaId;
    private String nombre;
    private String tipoMime;
    private String uriAlmacenamiento;
    private String hash;
    private Integer versionNumber;
    private Instant fechaSubida;
    private String origen;
    private Boolean firmado;
    private String metadatos;
}
