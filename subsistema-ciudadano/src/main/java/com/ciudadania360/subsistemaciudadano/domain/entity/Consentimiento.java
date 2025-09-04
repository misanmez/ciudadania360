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

  @ManyToOne
  @JoinColumn(name = "ciudadano_id")
  private Ciudadano ciudadano; // relación con ciudadano

  private String tipo;      // tipo de consentimiento (LOPD, marketing, encuestas, etc.)
  private Boolean otorgado;  // si el consentimiento fue otorgado o revocado

  @Column(name = "fecha_otorgamiento")
  private Date fechaOtorgamiento; // fecha en que se dio el consentimiento

  @Column(name = "fecha_revocacion")
  private Date fechaRevocacion;   // fecha en que se revocó el consentimiento (si aplica)

  @Column(columnDefinition = "jsonb")
  private String metadata;        // información adicional o notas

  @Version
  private Long version;
}
