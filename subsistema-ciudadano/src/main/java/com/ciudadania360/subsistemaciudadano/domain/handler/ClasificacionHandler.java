package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.ciudadania360.subsistemaciudadano.domain.repository.ClasificacionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class ClasificacionHandler {

    private final ClasificacionRepository repo;

    public ClasificacionHandler(ClasificacionRepository repo) {
        this.repo = repo;
    }

    public List<Clasificacion> list() {
        return repo.findAll();
    }

    public Clasificacion get(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Clasificación no encontrada con ID: " + id));
    }

    public Clasificacion create(Clasificacion e) {
        e.setId(UUID.randomUUID());
        return repo.save(e);
    }

    public Clasificacion update(UUID id, Clasificacion e) {
        Clasificacion existing = get(id);
        e.setId(existing.getId());
        return repo.save(e);
    }

    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("No se puede eliminar. Clasificación no encontrada con ID: " + id);
        }
        repo.deleteById(id);
    }

    public Clasificacion getDefaultClasificacion() {
        return repo.findByCodigo("GENERICA")
                .orElseThrow(() -> new IllegalStateException("No existe la clasificación GENÉRICA en la BD"));
    }
}
