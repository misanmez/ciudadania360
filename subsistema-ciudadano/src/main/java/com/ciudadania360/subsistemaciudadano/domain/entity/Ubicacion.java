package com.ciudadania360.subsistemaciudadano.domain.entity;

import jakarta.persistence.*;
import java.util.*;
import java.util.UUID;
import lombok.*;

import lombok.*;

@Entity
@Table(name = "ubicacion", schema = "ciudadano")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ubicacion {
    @Id
    private UUID id;
    private String direccion;
    private String municipio;
    private String provincia;
    private String cp;
    private Double lat;
    private Double lon;
    private Integer precision;
    private String fuente;

    @Version
    private Long version;
}
