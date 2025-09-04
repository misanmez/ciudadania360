package com.ciudadania360.subsistemaciudadano.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.*;

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

    private String canal; // web, app, presencial, telefónico, redes sociales

    private Instant fecha;

    private String agente; // agente asignado que atiende

    @Column(columnDefinition = "text")
    private String mensaje; // detalle de la interacción

    @Column(name = "adjunto_uri")
    private String adjuntoUri; // enlace a documento o archivo asociado

    private String visibilidad; // interna, pública, restringida

    @Column(columnDefinition = "jsonb")
    private String metadata; // historial, etiquetas, acciones automáticas

    @Version
    private Long version;
}
