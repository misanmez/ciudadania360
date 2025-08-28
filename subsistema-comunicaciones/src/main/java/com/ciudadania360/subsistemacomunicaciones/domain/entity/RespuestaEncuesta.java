package com.ciudadania360.subsistemacomunicaciones.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.*;


@Entity
@Table(name = "respuesta_encuesta", schema = "comunicaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespuestaEncuesta {
    @Id
    private UUID id;
    @Column(name = "encuesta_id")
    private UUID encuestaId;
    @Column(name = "ciudadano_id")
    private UUID ciudadanoId;
    @Column(columnDefinition = "jsonb")
    private String respuestas;
    private Integer puntuacion;
    private String comentarios;
    private Instant fecha;

    @Version
    private Long version;
}
