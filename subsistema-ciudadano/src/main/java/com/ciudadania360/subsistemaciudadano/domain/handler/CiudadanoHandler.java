package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.repository.CiudadanoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class CiudadanoHandler {

    private final CiudadanoRepository repo;

    public CiudadanoHandler(CiudadanoRepository repo) {
        this.repo = repo;
    }

    // Listado completo de ciudadanos
    public List<Ciudadano> list() {
        return repo.findAll();
    }

    // Obtener un ciudadano por ID
    public Ciudadano get(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ciudadano no encontrado con ID: " + id));
    }

    // Crear un ciudadano
    public Ciudadano create(Ciudadano e) {
        if (e.getId() == null) {
            e.setId(UUID.randomUUID());
        } else if (repo.existsById(e.getId())) {
            throw new IllegalArgumentException("El ID proporcionado ya existe: " + e.getId());
        }
        return repo.save(e);
    }

    // Actualizar un ciudadano existente
    public Ciudadano update(UUID id, Ciudadano e) {
        Ciudadano existing = get(id);

        // Mantener el mismo ID y version
        e.setId(existing.getId());
        e.setVersion(existing.getVersion());

        // Podrías aquí combinar campos no nulos del request para evitar sobrescribir valores
        return repo.save(e);
    }

    // Eliminar un ciudadano
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("No se puede eliminar. Ciudadano no encontrado con ID: " + id);
        }
        repo.deleteById(id);
    }

    // Método opcional: comprobar existencia
    public boolean exists(UUID id) {
        return repo.existsById(id);
    }
}

