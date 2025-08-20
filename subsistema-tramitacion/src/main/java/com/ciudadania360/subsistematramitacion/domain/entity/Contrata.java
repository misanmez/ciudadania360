package com.ciudadania360.subsistematramitacion.domain.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "contrata", schema = "tramitacion")
public class Contrata {
  @Id
  private UUID id;
  private String nombre;
  private String cif;

}
