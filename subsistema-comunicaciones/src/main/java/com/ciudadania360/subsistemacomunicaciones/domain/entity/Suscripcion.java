package com.ciudadania360.subsistemacomunicaciones.domain.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "suscripcion", schema = "comunicaciones")
public class Suscripcion {
  @Id
  private UUID id;
  @Column(name = "ciudadano_id")
  private UUID ciudadanoId;
  private String tema;
  private Boolean activo;

  @Version
  private Long version;
}
