package com.ciudadania360.subsistemainterno.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "adjunto", schema = "interno")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Adjunto {

    @Id
    private UUID id;

    @Column(name = "nombre_archivo", nullable = false)
    private String nombreArchivo;

    private String tipo;
    private Long tamanyo;
    private String ruta;

    @Column(columnDefinition = "jsonb")
    private String metadata;

    @ManyToOne
    @JoinColumn(name = "parte_trabajo_id")
    private ParteTrabajo parteTrabajo;

    @Version
    private Long version;
}
