package com.ciudadania360.subsistemaciudadano.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "direccion", schema = "ciudadano")
public class Direccion {

  @Id
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "ciudadano_id")
  private Ciudadano ciudadano; // relación con ciudadano

  private String via;        // nombre de la calle
  private String numero;     // número de la calle
  private String cp;         // código postal
  private String municipio;  // municipio
  private String provincia;  // provincia
  private String pais;       // nuevo campo opcional para futuro internacionalización

  private Double lat;        // latitud geográfica
  private Double lon;        // longitud geográfica

  private Boolean principal; // si es la dirección principal del ciudadano

  @Column(columnDefinition = "jsonb")
  private String metadata;   // información adicional de la dirección

  @Version
  private Long version;
}
