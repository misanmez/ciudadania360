package com.ciudadania360.subsistemainformacion.domain.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;
@Entity
@Table(name = "duda", schema = "informacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Duda {
  @Id
  private UUID id;
  private String pregunta;
  private String respuesta;

}
