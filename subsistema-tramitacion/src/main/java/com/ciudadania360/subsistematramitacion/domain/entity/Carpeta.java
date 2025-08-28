package com.ciudadania360.subsistematramitacion.domain.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;


@Entity
@Table(name = "carpeta", schema = "tramitacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carpeta {
    @Id
    private UUID id;
    @Column(name = "solicitud_id")
    private UUID solicitudId;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String ruta;
    @Column(columnDefinition = "jsonb")
    private String permisos;
    @Column(name = "numero_expediente")
    private String numeroExpediente;
    private String estado;

    @Version
    private Long version;
}
