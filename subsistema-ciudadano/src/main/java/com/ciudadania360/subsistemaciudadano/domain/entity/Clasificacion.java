package com.ciudadania360.subsistemaciudadano.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "clasificacion", schema = "ciudadano")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clasificacion {

    @Id
    private UUID id;

    private String codigo; // código único interno para clasificación

    private String nombre; // nombre descriptivo

    @Column(columnDefinition = "text")
    private String descripcion; // descripción detallada

    private String tipo; // puede ser: queja, incidencia, solicitud de información, felicitación, sugerencia

    @ManyToOne
    @JoinColumn(name = "padre_id")
    private Clasificacion padre; // relación jerárquica

    @OneToMany(mappedBy = "padre")
    private List<Clasificacion> hijos = new ArrayList<>();

    @Column(columnDefinition = "jsonb")
    private String metadata; // información adicional, reglas automáticas, etiquetas

    @Version
    private Long version;
}
