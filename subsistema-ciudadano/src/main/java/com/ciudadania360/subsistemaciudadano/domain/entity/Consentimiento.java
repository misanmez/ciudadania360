package com.ciudadania360.subsistemaciudadano.domain.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "consentimiento", schema = "ciudadano")
public class Consentimiento {
  @Id
  private UUID id;
  @Column(name = "ciudadano_id")
  private UUID ciudadanoId;
  private String tipo;
  private Boolean otorgado;

}
