package com.ciudadania360.subsistemainformacion.application.dto.duda;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DudaRequest {
    private String pregunta;
    private String respuesta;
}
