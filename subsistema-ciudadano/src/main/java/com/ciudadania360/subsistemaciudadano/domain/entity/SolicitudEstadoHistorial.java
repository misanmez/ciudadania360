package com.ciudadania360.subsistemaciudadano.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "solicitud_estado_historial", schema = "ciudadano")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudEstadoHistorial {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

    private String estadoAnterior;
    private String estadoNuevo;
    private Instant fechaCambio;
    private String agente; // quién realizó el cambio

    @Column(columnDefinition = "jsonb")
    private String metadata;

    @Version
    private Long version;
}
