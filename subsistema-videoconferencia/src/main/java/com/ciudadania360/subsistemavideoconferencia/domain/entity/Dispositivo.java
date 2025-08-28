package com.ciudadania360.subsistemavideoconferencia.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "dispositivo", schema = "videoconferencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dispositivo {
    @Id
    private UUID id;
    @Column(name = "ciudadano_id")
    private UUID ciudadanoId;
    private String tipo;
    private String sistemaOperativo;
    private String navegador;
    private String capacidadVideo;
    private Boolean microfono;
    private Boolean camara;
    private String red;
    private Boolean pruebaRealizada;
    private Instant ultimoCheck;

    @Version
    private Long version;
}
