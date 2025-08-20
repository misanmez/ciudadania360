package com.ciudadania360.subsistemainformacion.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;
import java.util.UUID;
import lombok.*;

import lombok.*;

@Entity
@Table(name = "sugerencia", schema = "informacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sugerencia {
    @Id
    private UUID id;
    private String titulo;
    @Column(columnDefinition = "text")
    private String descripcion;
    @Column(name = "ciudadano_id")
    private UUID ciudadanoId;
    private String estado;
    private String prioridad;
    private String areaResponsable;
    private Instant fecha;

    @Version
    private Long version;
}
