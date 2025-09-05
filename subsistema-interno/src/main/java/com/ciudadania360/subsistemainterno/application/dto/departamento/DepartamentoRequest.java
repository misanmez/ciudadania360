package com.ciudadania360.subsistemainterno.application.dto.departamento;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartamentoRequest {
    private String nombre;
    private String descripcion;
}
