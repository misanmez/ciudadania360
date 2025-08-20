package com.ciudadania360.subsistemainformacion.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;
import java.util.UUID;
import lombok.*;

import lombok.*;

@Entity
@Table(name = "informacion", schema = "informacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Informacion {
    @Id
    private UUID id;
    private String titulo;
    @Column(columnDefinition = "text")
    private String contenido;
    private String etiquetas;
    private String audiencia;
    @Column(name = "estado_publicacion")
    private String estadoPublicacion;
    private String propietario;
    @Column(name = "version_number")
    private Integer versionNumber;
    @Column(name = "fecha_publicacion")
    private Instant fechaPublicacion;

    @Version
    private Long version;
}
