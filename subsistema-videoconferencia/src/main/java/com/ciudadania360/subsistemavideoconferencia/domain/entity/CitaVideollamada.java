package com.ciudadania360.subsistemavideoconferencia.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;
import java.util.UUID;
import lombok.*;

import lombok.*;

@Entity
@Table(name = "cita_videollamada", schema = "videoconferencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaVideollamada {
    @Id
    private UUID id;
    @Column(name = "sesion_id")
    private UUID sesionId;
    @Column(name = "ciudadano_id")
    private UUID ciudadanoId;
    private String empleadoId;
    private Instant fechaProgramadaInicio;
    private Instant fechaProgramadaFin;
    private String estado;
    private String canalNotificacion;
    @Column(columnDefinition = "text")
    private String notas;

    @Version
    private Long version;
}
