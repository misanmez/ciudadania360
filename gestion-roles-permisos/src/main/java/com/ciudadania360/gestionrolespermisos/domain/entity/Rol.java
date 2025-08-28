package com.ciudadania360.gestionrolespermisos.domain.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;


@Entity
@Table(name = "rol", schema = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol {
    @Id
    private UUID id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String nivel;
    private Boolean activo;

    @Version
    private Long version;
}
