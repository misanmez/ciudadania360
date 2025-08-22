package com.ciudadania360.subsistemainformacion.application.dto.informacion;

import lombok.*;
import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformacionRequest {
    private String titulo;
    private String contenido;
    private String etiquetas;
    private String audiencia;
    private String estadoPublicacion;
    private String propietario;
    private Integer versionNumber;
    private Instant fechaPublicacion;
}

