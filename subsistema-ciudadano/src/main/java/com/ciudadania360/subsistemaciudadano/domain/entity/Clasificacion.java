package com.ciudadania360.subsistemaciudadano.domain.entity;

import jakarta.persistence.*;
import java.util.*;
import java.util.UUID;
import lombok.*;


@Entity
@Table(name = "clasificacion", schema = "ciudadano")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clasificacion {
    @Id
    private UUID id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String tipo;
    @ManyToOne
    @JoinColumn(name = "padre_id")
    private Clasificacion padre;
    @OneToMany(mappedBy = "padre")
    private List<Clasificacion> hijos;

    @Version
    private Long version;
}
