package com.ciudadania360.subsistemaciudadano.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "ubicacion", schema = "ciudadano")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ubicacion {

    @Id
    private UUID id;

    private String direccion;   // calle, número
    private String municipio;   // municipio
    private String provincia;   // provincia
    private String cp;          // código postal
    private Double lat;         // latitud geográfica
    private Double lon;         // longitud geográfica
    private Integer precision;  // precisión geográfica del dato
    private String fuente;      // origen de la información (BDC, Padrón, ciudadano, etc.)

    @Column(columnDefinition = "jsonb")
    private String metadata;    // datos adicionales: notas, referencias internas

    @Version
    private Long version;
}
