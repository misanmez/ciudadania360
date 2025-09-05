package com.ciudadania360.shared.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "empleado", schema = "shared")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empleado {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false, unique = true)
    private String email;

    private String telefono;

    @Column(name = "departamento_id")
    private UUID departamentoId; // solo ID, sin dependencia directa a Departamento

    @Column(nullable = false)
    private String rol;

    @Column(columnDefinition = "jsonb")
    private String metadata;

    @Version
    private Long version;
}
