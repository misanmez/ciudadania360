package com.ciudadania360.subsistemainformacion.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.*;


@Entity
@Table(name = "dataset", schema = "informacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dataset {
    @Id
    private UUID id;
    private String nombre;
    @Column(columnDefinition = "text")
    private String descripcion;
    private String fuente;
    @Column(columnDefinition = "jsonb")
    private String esquema;
    private String periodicidad;
    private String licencia;
    @Column(name = "url_portal_datos")
    private String urlPortalDatos;
    private String formato;
    private String responsable;
    @Column(name = "ultima_actualizacion")
    private Instant ultimaActualizacion;

    @Version
    private Long version;
}
