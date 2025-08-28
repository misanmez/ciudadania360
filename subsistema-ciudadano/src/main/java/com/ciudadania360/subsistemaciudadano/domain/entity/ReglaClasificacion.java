package com.ciudadania360.subsistemaciudadano.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.*;


@Entity
@Table(name = "regla_clasificacion", schema = "ciudadano")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReglaClasificacion {
    @Id
    private UUID id;
    private String nombre;
    @Column(columnDefinition = "text")
    private String expresion;
    private Integer prioridad;
    private Boolean activa;
    @Column(name = "clasificacion_id")
    private UUID clasificacionId;
    @Column(columnDefinition = "jsonb")
    private String condiciones;
    private String fuente;
    @Column(name = "vigencia_desde")
    private Instant vigenciaDesde;
    @Column(name = "vigencia_hasta")
    private Instant vigenciaHasta;

    @Version
    private Long version;
}
