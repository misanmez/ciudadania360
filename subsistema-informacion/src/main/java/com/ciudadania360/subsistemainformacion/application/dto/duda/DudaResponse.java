package com.ciudadania360.subsistemainformacion.application.dto.duda;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DudaResponse {
    private UUID id;
    private String pregunta;
    private String respuesta;
}
