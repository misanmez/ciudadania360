package com.ciudadania360.gestionrolespermisos.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.*;


@Entity
@Table(name = "ciudadano_rol", schema = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRol {
    @Id
    private UUID id;
    @Column(name = "ciudadano_id")
    private UUID ciudadanoId;
    @Column(name = "rol_id")
    private UUID rolId;
    private String asignadoPor;
    @Column(name = "fecha_asignacion")
    private Instant fechaAsignacion;
    @Column(name = "fecha_caducidad")
    private Instant fechaCaducidad;
    private String origen;
    @Column(columnDefinition = "text")
    private String observaciones;

    @Version
    private Long version;
}
