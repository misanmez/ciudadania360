package com.ciudadania360.subsistemaciudadano.application.dto.interaccion;

import com.ciudadania360.subsistemaciudadano.application.dto.ciudadano.CiudadanoResponse;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitud.SolicitudResponse;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemainterno.application.dto.empleado.EmpleadoResponse;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InteraccionResponse {
    private UUID id;             // ID de la interacción
    private SolicitudResponse solicitud;    // solicitud asociada
    private CiudadanoResponse ciudadano;    // ciudadano asociado
    private EmpleadoResponse empleadoResponsable;      // empleado responsable asociado
    private String canal;        // Canal de comunicación
    private Instant fecha;       // Fecha de la interacción
    private String agente;       // Agente asignado
    private String mensaje;      // Contenido
    private String adjuntoUri;   // URI del adjunto
    private String visibilidad;  // Pública o privada
    private Long version;        // Versión para control de concurrencia
}
