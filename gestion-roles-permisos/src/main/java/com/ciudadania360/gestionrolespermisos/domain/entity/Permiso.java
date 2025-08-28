package com.ciudadania360.gestionrolespermisos.domain.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;


@Entity
@Table(name = "permiso", schema = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permiso {
    @Id
    private UUID id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String scope;
    private String recurso;
    private String accion;
    private Boolean activo;

    @Version
    private Long version;
}
