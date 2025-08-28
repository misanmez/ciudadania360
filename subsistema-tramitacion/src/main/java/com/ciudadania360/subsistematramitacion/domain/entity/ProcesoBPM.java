package com.ciudadania360.subsistematramitacion.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.*;


@Entity
@Table(name = "proceso_bpm", schema = "tramitacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcesoBPM {
    @Id
    private UUID id;
    private String engineInstanceId;
    private String definitionKey;
    @Column(name = "business_key")
    private UUID businessKey;
    private String estado;
    private Instant inicio;
    private Instant fin;
    @Column(columnDefinition = "jsonb")
    private String variables;
    private String iniciador;

    @Version
    private Long version;
}
