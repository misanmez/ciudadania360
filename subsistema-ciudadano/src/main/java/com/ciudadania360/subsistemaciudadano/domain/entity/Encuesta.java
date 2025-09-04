package com.ciudadania360.subsistemaciudadano.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "encuesta", schema = "ciudadano")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Encuesta {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "solicitud_id", nullable = false)
    private Solicitud solicitud;

    private String tipo;              // Tipo de encuesta
    private String estado;            // ENVIADA, PENDIENTE, COMPLETADA
    private Instant fechaEnvio;
    private Instant fechaRespuesta;

    @Column(columnDefinition = "jsonb")
    private String respuestas;        // JSON con las respuestas

    @Column(columnDefinition = "jsonb")
    private String metadata;          // Datos adicionales en JSON

    @Version
    private Long version;
}
