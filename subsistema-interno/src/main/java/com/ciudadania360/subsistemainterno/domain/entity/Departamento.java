package com.ciudadania360.subsistemainterno.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "departamento", schema = "interno")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Departamento {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String descripcion;

    @Version
    private Long version;
}
