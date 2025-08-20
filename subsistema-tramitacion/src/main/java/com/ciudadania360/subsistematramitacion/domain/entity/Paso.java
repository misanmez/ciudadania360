package com.ciudadania360.subsistematramitacion.domain.entity;

import jakarta.persistence.*;
import java.util.*;
import java.util.UUID;
import lombok.*;

import lombok.*;

@Entity
@Table(name = "paso", schema = "tramitacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paso {
    @Id
    private UUID id;
    @Column(name = "flujo_id")
    private UUID flujoId;
    private String nombre;
    private Integer orden;
    private String tipo;
    private String responsableRole;
    private Integer slaHoras;

    @Version
    private Long version;
}
