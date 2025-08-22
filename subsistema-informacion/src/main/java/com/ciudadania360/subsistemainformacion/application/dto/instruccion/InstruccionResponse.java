package com.ciudadania360.subsistemainformacion.application.dto.instruccion;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstruccionResponse {
    private UUID id;
    private String titulo;
    private String pasos;
}
