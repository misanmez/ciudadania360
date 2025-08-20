package com.ciudadania360.subsistematramitacion.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;
import java.util.UUID;
import lombok.*;

import lombok.*;

@Entity
@Table(name = "documento", schema = "tramitacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Documento {
    @Id
    private UUID id;
    @Column(name = "carpeta_id")
    private UUID carpetaId;
    private String nombre;
    @Column(name = "tipo_mime")
    private String tipoMime;
    @Column(name = "uri_almacenamiento")
    private String uriAlmacenamiento;
    private String hash;
    @Column(name = "version_number")
    private Integer versionNumber;
    @Column(name = "fecha_subida")
    private Instant fechaSubida;
    private String origen;
    private Boolean firmado;
    @Column(columnDefinition = "jsonb")
    private String metadatos;

    @Version
    private Long version;
}
