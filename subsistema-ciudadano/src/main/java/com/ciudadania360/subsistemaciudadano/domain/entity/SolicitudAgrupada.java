package com.ciudadania360.subsistemaciudadano.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "solicitud_agrupada", schema = "ciudadano")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudAgrupada {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "solicitud_padre_id")
    private Solicitud solicitudPadre; // solicitud principal

    @ManyToOne
    @JoinColumn(name = "solicitud_hija_id")
    private Solicitud solicitudHija;  // sub-solicitud

    @Column(columnDefinition = "jsonb")
    private String metadata; // información adicional

    @Version
    private Long version;
}
