package com.ciudadania360.subsistemaciudadano.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.UUID;
import lombok.*;

import lombok.*;

@Entity
@Table(name = "solicitud", schema = "ciudadano")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Solicitud {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "ciudadano_id")
    private Ciudadano ciudadano;
    private String titulo;
    private String descripcion;
    private String tipo;
    @Column(name = "canal_entrada")
    private String canalEntrada;
    private String estado;
    private String prioridad;
    @ManyToOne
    @JoinColumn(name = "clasificacion_id")
    private Clasificacion clasificacion;
    @ManyToOne
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;
    @Column(name = "numero_expediente")
    private String numeroExpediente;
    @Column(name = "fecha_registro")
    private Instant fechaRegistro;
    @Column(name = "fecha_limite_sla")
    private Instant fechaLimiteSLA;
    @Column(name = "fecha_cierre")
    private Instant fechaCierre;
    @Column(name = "score_relevancia")
    private BigDecimal scoreRelevancia;
    private String origen;
    @Column(name = "adjuntos_count")
    private Integer adjuntosCount;
    @Column(name = "encuesta_enviada")
    private Boolean encuestaEnviada;
    @Column(name = "referencia_externa")
    private String referenciaExterna;
    @Column(columnDefinition = "jsonb")
    private String metadata;

    @Version
    private Long version;
}
