package com.ciudadania360.subsistemainformacion.application.dto.instruccion;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstruccionRequest {
    private String titulo;
    private String pasos;
}

