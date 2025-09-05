package com.ciudadania360.subsistemainterno.application.dto.departamento;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartamentoResponse {
    private UUID id;
    private String nombre;
    private String descripcion;
}
