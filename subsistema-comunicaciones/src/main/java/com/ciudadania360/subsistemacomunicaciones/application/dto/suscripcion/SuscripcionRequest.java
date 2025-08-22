package com.ciudadania360.subsistemacomunicaciones.application.dto.suscripcion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuscripcionRequest {
    private UUID ciudadanoId;
    private String tema;
    private Boolean activo;
}
