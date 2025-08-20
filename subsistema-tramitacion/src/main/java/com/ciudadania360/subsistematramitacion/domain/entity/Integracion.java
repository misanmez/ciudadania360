package com.ciudadania360.subsistematramitacion.domain.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "integracion", schema = "tramitacion")
public class Integracion {
  @Id
  private UUID id;
  private String sistema;
  private String tipo;
  private String endpoint;

}
