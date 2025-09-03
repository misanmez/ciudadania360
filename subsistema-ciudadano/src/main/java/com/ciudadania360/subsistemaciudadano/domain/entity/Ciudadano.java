package com.ciudadania360.subsistemaciudadano.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "ciudadano", schema = "ciudadano")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ciudadano {

    @Id
    private UUID id;

    @Column(name = "nif_nie", nullable = false)
    private String nifNie;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    private String email;
    private String telefono;

    @Column(name = "canal_preferido")
    private String canalPreferido;

    @Column(name = "direccion_postal")
    private String direccionPostal;

    @Column(name = "ubicacion_id")
    private UUID ubicacionId;

    @Column(name = "consentimiento_lopd", nullable = false)
    private Boolean consentimientoLOPD;

    private String estado;

    @Column(name = "external_id")
    private String externalId;

    @Column(columnDefinition = "jsonb")
    private String metadata;

    @OneToMany(mappedBy = "ciudadano", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Solicitud> solicitudes;

    @Version
    private Long version;
}
