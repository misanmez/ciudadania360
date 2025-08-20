package com.ciudadania360.subsistemavideoconferencia.domain.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "planificacion", schema = "videoconferencia")
public class Planificacion {
  @Id
  private UUID id;
  private String nombre;
  private String descripcion;

}
