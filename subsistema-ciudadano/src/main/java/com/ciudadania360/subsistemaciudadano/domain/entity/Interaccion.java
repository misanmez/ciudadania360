package com.ciudadania360.subsistemaciudadano.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.*;


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
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;
    @ManyToOne
    @JoinColumn(name = "ciudadano_id")
    private Ciudadano ciudadano;
    private String canal;
    private Instant fecha;
    private String agente;
    @Column(columnDefinition = "text")
    private String mensaje;
    @Column(name = "adjunto_uri")
    private String adjuntoUri;
    private String visibilidad;

    @Version
    private Long version;
}
