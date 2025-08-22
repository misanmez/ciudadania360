package com.ciudadania360.subsistemainformacion.application.dto.informacion;

import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformacionResponse {
    private UUID id;
    private String titulo;
    private String contenido;
    private String etiquetas;
    private String audiencia;
    private String estadoPublicacion;
    private String propietario;
    private Integer versionNumber;
    private Instant fechaPublicacion;
}

