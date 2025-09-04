package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.ReglaClasificacion;
import com.ciudadania360.subsistemaciudadano.domain.repository.ReglaClasificacionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ReglaClasificacionHandler {

    private final ReglaClasificacionRepository repositorio;

    public ReglaClasificacionHandler(ReglaClasificacionRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<ReglaClasificacion> list() {
        return repositorio.findAll();
    }

    public ReglaClasificacion get(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Regla de clasificación no encontrada con id: " + id));
    }

    public ReglaClasificacion create(ReglaClasificacion e) {
        return repositorio.save(e);
    }

    public ReglaClasificacion update(UUID id, ReglaClasificacion cambios) {
        ReglaClasificacion existente = get(id);

        // Aplicar cambios solo a los campos modificables
        existente.setNombre(cambios.getNombre());
        existente.setExpresion(cambios.getExpresion());
        existente.setPrioridad(cambios.getPrioridad());
        existente.setActiva(cambios.getActiva());
        existente.getClasificacion().setId(cambios.getClasificacion().getId());
        existente.setCondiciones(cambios.getCondiciones());
        existente.setFuente(cambios.getFuente());
        existente.setVigenciaDesde(cambios.getVigenciaDesde());
        existente.setVigenciaHasta(cambios.getVigenciaHasta());

        return repositorio.save(existente);
    }

    public void delete(UUID id) {
        get(id); // lanza excepción si no existe
        repositorio.deleteById(id);
    }

    /**
     * Comprueba si ya existe una regla con el nombre dado.
     */
    public boolean existsByNombre(String nombre) {
        return repositorio.findByNombreIgnoreCase(nombre).isPresent();
    }

    /**
     * Comprueba si existe otra regla con el mismo nombre, excluyendo el id dado.
     */
    public boolean existsByNombreExcludingId(String nombre, UUID id) {
        return repositorio.findByNombreIgnoreCase(nombre)
                .map(r -> !r.getId().equals(id))
                .orElse(false);
    }
}
