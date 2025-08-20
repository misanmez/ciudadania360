package com.ciudadania360.subsistemacomunicaciones.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;
import java.util.UUID;
import lombok.*;

import lombok.*;

@Entity
@Table(name = "notificacion", schema = "comunicaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {
    @Id
    private UUID id;
    @Column(name = "ciudadano_id")
    private UUID ciudadanoId;
    private String titulo;
    @Column(length = 500)
    private String mensaje;
    private String canal;
    private String prioridad;
    private String estado;
    private String referencia;
    @Column(name = "fecha_entrega")
    private Instant fechaEntrega;

    @Version
    private Long version;
}
