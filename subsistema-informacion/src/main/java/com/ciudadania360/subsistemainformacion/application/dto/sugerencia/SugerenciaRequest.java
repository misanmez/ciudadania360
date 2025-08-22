package com.ciudadania360.subsistemainformacion.application.dto.sugerencia;

import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SugerenciaRequest {
    private String titulo;
    private String descripcion;
    private UUID ciudadanoId;
    private String estado;
    private String prioridad;
    private String areaResponsable;
    private Instant fecha;
}

