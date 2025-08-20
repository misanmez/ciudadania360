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
  @Column(name = "ciudadano_id")
  private UUID ciudadanoId;
  private String via;
  private String numero;
  private String cp;
  private String municipio;
  private String provincia;
  private Double lat;
  private Double lon;
  private Boolean principal;

}
