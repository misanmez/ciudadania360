// CarpetaRequest.java
package com.ciudadania360.subsistematramitacion.application.dto.carpeta;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarpetaRequest {
    private UUID solicitudId;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String ruta;
    private String permisos;
    private String numeroExpediente;
    private String estado;
}
