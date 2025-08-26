package com.ciudadania360.subsistemaciudadano.application.dto.solicitud;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudTransitionRequest {
    private String nuevoEstado;
}
