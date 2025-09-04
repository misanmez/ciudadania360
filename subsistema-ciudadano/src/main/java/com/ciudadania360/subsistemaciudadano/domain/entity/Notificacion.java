package com.ciudadania360.subsistemaciudadano.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notificacion", schema = "ciudadano")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

    private String canal; // SMS, Email, App
    private Instant fechaEnvio;
    private String estado; // enviado, pendiente, error
    @Column(columnDefinition = "text")
    private String mensaje;

    @Column(columnDefinition = "jsonb")
    private String metadata; // datos extra

    @Version
    private Long version;
}
