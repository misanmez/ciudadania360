package com.ciudadania360.subsistemaciudadano.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "regla_clasificacion", schema = "ciudadano")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReglaClasificacion {

    @Id
    private UUID id;

    private String nombre; // nombre descriptivo de la regla

    @Column(columnDefinition = "text")
    private String expresion; // expresión lógica para evaluar la regla (ej: palabras clave, condiciones)

    private Integer prioridad; // prioridad de la regla para resolver conflictos entre varias reglas

    private Boolean activa; // indica si la regla está activa o no

    @ManyToOne
    @JoinColumn(name = "clasificacion_id")
    private Clasificacion clasificacion; // clasificación asociada si la regla se cumple

    @Column(columnDefinition = "jsonb")
    private String condiciones; // condiciones adicionales en formato JSON para evaluaciones complejas

    private String fuente; // origen de la regla (manual, automática, importada)

    @Column(name = "vigencia_desde")
    private Instant vigenciaDesde; // fecha desde la cual la regla es válida

    @Column(name = "vigencia_hasta")
    private Instant vigenciaHasta; // fecha hasta la cual la regla es válida

    @Version
    private Long version;
}
