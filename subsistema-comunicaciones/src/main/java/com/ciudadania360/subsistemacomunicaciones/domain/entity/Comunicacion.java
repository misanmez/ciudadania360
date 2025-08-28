package com.ciudadania360.subsistemacomunicaciones.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.*;


@Entity
@Table(name = "comunicacion", schema = "comunicaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comunicacion {
    @Id
    private UUID id;
    private String asunto;
    @Column(columnDefinition = "text")
    private String cuerpo;
    private String canal;
    private String estado;
    private String destinatario;
    @Column(name = "ciudadano_id")
    private UUID ciudadanoId;
    @Column(name = "programada_para")
    private Instant programadaPara;

    @Version
    private Long version;
}
