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
    private String canalPreferido; // canal preferido para notificaciones

    @Column(name = "direccion_postal")
    private String direccionPostal; // dirección principal

    @ManyToOne
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion; // ubicación geográfica

    @Column(name = "consentimiento_lopd", nullable = false)
    private Boolean consentimientoLOPD; // consentimiento de datos

    private String estado; // activo, inactivo, suspendido

    @Column(name = "external_id")
    private String externalId; // referencia a sistemas externos

    @Column(columnDefinition = "jsonb")
    private String metadata; // información adicional en JSON

    @OneToMany(mappedBy = "ciudadano", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Solicitud> solicitudes; // historial completo de solicitudes

    @OneToMany(mappedBy = "ciudadano", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Direccion> direcciones; // listado de direcciones asociadas

    @OneToMany(mappedBy = "ciudadano", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consentimiento> consentimientos; // historial de consentimientos

    @OneToMany(mappedBy = "ciudadano", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interaccion> interacciones; // historial de interacciones

    @Version
    private Long version;
}
