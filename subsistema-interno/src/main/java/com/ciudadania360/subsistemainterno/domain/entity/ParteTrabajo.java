package com.ciudadania360.subsistemainterno.domain.entity;

import com.ciudadania360.shared.domain.entity.Empleado;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "parte_trabajo", schema = "interno")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParteTrabajo {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String titulo;

    private String descripcion;

    @Column(nullable = false)
    private String estado;

    private String prioridad;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleadoAsignado; // apunta a shared

    @Column(columnDefinition = "jsonb")
    private String metadata;

    @OneToMany(mappedBy = "parteTrabajo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Adjunto> adjuntos;

    @Column(name = "solicitud_id")
    private UUID solicitudId; // referencia a Solicitud (subsistema-ciudadano)

    @Version
    private Long version;
}
