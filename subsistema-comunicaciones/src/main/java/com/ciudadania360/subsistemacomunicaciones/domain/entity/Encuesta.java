package com.ciudadania360.subsistemacomunicaciones.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.*;


@Entity
@Table(name = "encuesta", schema = "comunicaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Encuesta {
    @Id
    private UUID id;
    private String titulo;
    @Column(columnDefinition = "text")
    private String descripcion;
    @Column(columnDefinition = "jsonb")
    private String preguntas;
    private String estado;
    private String audiencia;
    @Column(name = "fecha_inicio")
    private Instant fechaInicio;
    @Column(name = "fecha_fin")
    private Instant fechaFin;
    @Column(name = "vinculada_solicitud_id")
    private UUID vinculadaSolicitudId;

    @Version
    private Long version;
}
