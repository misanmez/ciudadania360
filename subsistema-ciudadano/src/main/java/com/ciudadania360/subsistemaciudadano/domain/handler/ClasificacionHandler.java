package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.ciudadania360.subsistemaciudadano.domain.repository.ClasificacionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class ClasificacionHandler {

    private final ClasificacionRepository repository;

    public ClasificacionHandler(ClasificacionRepository repo) {
        this.repository = repo;
    }

    public List<Clasificacion> list() {
        return repository.findAll();
    }

    public Clasificacion get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Clasificación no encontrada"));
    }

    public Clasificacion create(Clasificacion e) {
        if (e.getId() == null) {
            e.setId(UUID.randomUUID());
        }
        return repository.save(e);
    }

    public Clasificacion update(UUID id, Clasificacion e) {
        Clasificacion actual = get(id); // asegura existencia
        e.setId(actual.getId());
        return repository.save(e);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Clasificacion getDefaultClasificacion() {
        return repository.findByCodigo("GENERICA")
                .orElseThrow(() -> new IllegalStateException("No existe la clasificación GENÉRICA en la BD"));
    }
}
