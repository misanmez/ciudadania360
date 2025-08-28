package com.ciudadania360.subsistematramitacion.domain.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;


@Entity
@Table(name = "flujo", schema = "tramitacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flujo {
    @Id
    private UUID id;
    private String nombre;
    @Column(columnDefinition = "text")
    private String descripcion;
    private Boolean activo;
    private String tipo;
    private Integer slaHoras;
    @Column(columnDefinition = "jsonb")
    private String pasosDefinition;

    @Version
    private Long version;
}
