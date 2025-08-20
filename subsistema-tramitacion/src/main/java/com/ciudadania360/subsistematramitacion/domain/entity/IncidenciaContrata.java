package com.ciudadania360.subsistematramitacion.domain.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "incidencia_contrata", schema = "tramitacion")
public class IncidenciaContrata {
  @Id
  private UUID id;
  private java.util.UUID contrataId;
  private String descripcion;
  private String estado;

}
