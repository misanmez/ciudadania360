package com.ciudadania360.subsistematramitacion.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;
import java.util.UUID;
import lombok.*;

import lombok.*;

@Entity
@Table(name = "tarea_bpm", schema = "tramitacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TareaBPM {
    @Id
    private UUID id;
    @Column(name = "proceso_bpm_id")
    private UUID procesoBpmId;
    private String engineTaskId;
    private String nombre;
    private String estado;
    private String assignee;
    private String candidateGroup;
    private Instant dueDate;
    private Integer priority;
    @Column(columnDefinition = "jsonb")
    private String variables;
    private Instant created;
    private Instant completed;

    @Version
    private Long version;
}
