package com.ciudadania360.subsistemaciudadano.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InteraccionRequest {
    
    @NotNull(message = "El ciudadano es obligatorio")
    private UUID ciudadanoId;
    
    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;
    
    private String tipo;
    
    private String canal;
    
    private UUID empleadoResponsableId;
    
    private LocalDateTime fechaInteraccion;
    
    private String observaciones;
    
    private Boolean requiereSeguimiento;
}
