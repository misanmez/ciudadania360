package com.ciudadania360.subsistemainformacion.domain.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "instruccion", schema = "informacion")
public class Instruccion {
  @Id
  private UUID id;
  private String titulo;
  private String pasos;

}
