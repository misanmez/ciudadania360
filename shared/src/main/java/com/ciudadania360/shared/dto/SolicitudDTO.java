package com.ciudadania360.shared.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;

public class SolicitudDTO {
    private UUID id;
    @NotBlank
    private String titulo;
    private String descripcion;
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
