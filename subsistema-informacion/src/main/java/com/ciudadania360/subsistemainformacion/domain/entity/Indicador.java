package com.ciudadania360.subsistemainformacion.domain.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;


@Entity
@Table(name = "indicador", schema = "informacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Indicador {
    @Id
    private UUID id;
    private String codigo;
    private String nombre;
    private String descripcion;
    @Column(name = "definicion_calculo")
    private String definicionCalculo;
    private String frecuencia;
    private String unidad;
    private String responsable;
    private Boolean kpi;
    @Column(name = "origen_datos")
    private String origenDatos;
    @Column(name = "dataset_id")
    private UUID datasetId;

    @Version
    private Long version;
}
