package com.ciudadania360.subsistemavideoconferencia.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.*;


@Entity
@Table(name = "sesion", schema = "videoconferencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sesion {
    @Id
    private UUID id;
    @Column(name = "solicitud_id")
    private UUID solicitudId;
    private Instant fechaInicio;
    private Instant fechaFin;
    private String estado;
    private String plataforma;
    private String enlace;
    private String codigoAcceso;
    private String grabacionUri;
    private String motivo;
    private String operadorId;

    @Version
    private Long version;
}
