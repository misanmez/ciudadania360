package com.ciudadania360.subsistemaciudadano.application.dto.solicitud;

import com.ciudadania360.subsistemaciudadano.application.dto.ciudadano.CiudadanoResponse;
import com.ciudadania360.subsistemaciudadano.application.dto.clasificacion.ClasificacionResponse;
import com.ciudadania360.subsistemaciudadano.application.dto.interaccion.InteraccionResponse;
import com.ciudadania360.subsistemainterno.application.dto.empleado.EmpleadoResponse;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudResponse {
    private UUID id;
    private CiudadanoResponse ciudadano;
    private String titulo;
    private String descripcion;
    private String tipo;
    private String canalEntrada;
    private String estado;
    private String prioridad;
    private ClasificacionResponse clasificacion;
    private UUID ubicacionId;
    private String numeroExpediente;
    private Instant fechaRegistro;
    private Instant fechaLimiteSLA;
    private Instant fechaCierre;
    private BigDecimal scoreRelevancia;  // Se calcula en el sistema
    private String origen;
    private Integer adjuntosCount;
    private Boolean encuestaEnviada;
    private String referenciaExterna;
    private String metadata;
    private EmpleadoResponse agenteAsignado;
    private List<InteraccionResponse> interacciones;
    private Long version; // Control de concurrencia optimista
}
