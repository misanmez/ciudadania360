package com.ciudadania360.subsistemaciudadano.domain.entity;

import com.ciudadania360.shared.domain.entity.Empleado;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "interaccion", schema = "ciudadano")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Interaccion {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "solicitud_id", nullable = false)
    private Solicitud solicitud;

    @ManyToOne
    @JoinColumn(name = "ciudadano_id", nullable = false)
    private Ciudadano ciudadano;

    @ManyToOne
    @JoinColumn(name = "empleado_responsable_id", nullable = false)
    private Empleado empleadoResponsable;

    private String canal;

    private Instant fecha;

    private String agente; // nombre del agente (opcional)

    @Column(columnDefinition = "text")
    private String mensaje;

    @Column(name = "adjunto_uri")
    private String adjuntoUri;

    private String visibilidad;

    @Column(columnDefinition = "jsonb")
    private String metadata;

    @Version
    private Long version;
}
