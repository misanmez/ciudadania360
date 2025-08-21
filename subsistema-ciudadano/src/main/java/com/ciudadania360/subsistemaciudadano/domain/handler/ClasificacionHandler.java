package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.ciudadania360.subsistemaciudadano.domain.repository.ClasificacionRepositorio;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class ClasificacionHandler {

    private final ClasificacionRepositorio repo;

    public ClasificacionHandler(ClasificacionRepositorio repo) {
        this.repo = repo;
    }

    public List<Clasificacion> list() {
        return repo.findAll();
    }

    public Clasificacion get(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Clasificación no encontrada"));
    }

    public Clasificacion create(Clasificacion e) {
        if (e.getId() == null) {
            e.setId(UUID.randomUUID());
        }
        return repo.save(e);
    }

    public Clasificacion update(UUID id, Clasificacion e) {
        Clasificacion actual = get(id); // asegura existencia
        e.setId(actual.getId());
        return repo.save(e);
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
