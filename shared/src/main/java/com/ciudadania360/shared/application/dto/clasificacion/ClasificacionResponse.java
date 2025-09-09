package com.ciudadania360.shared.application.dto.clasificacion;

import lombok.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClasificacionResponse {
    private UUID id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String tipo;
    private UUID padreId;                   // referencia a la clasificación padre
    private List<ClasificacionResponse> hijos; // lista de hijos para estructura jerárquica
    private Long version;
}
