package com.ciudadania360.subsistemainformacion.domain.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "recomendacion", schema = "informacion")
public class Recomendacion {
  @Id
  private UUID id;
  private String titulo;
  private String detalle;

}
