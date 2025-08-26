package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Direccion;
import com.ciudadania360.subsistemaciudadano.domain.repository.DireccionRepository;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class DireccionHandler {
    private final DireccionRepository repo;
    public DireccionHandler(DireccionRepository repo) { this.repo = repo; }

    public List<Direccion> list() { return repo.findAll(); }
    public Direccion get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Direccion no encontrado")); }
    public Direccion create(Direccion e) { return repo.save(e); }
    public Direccion update(UUID id, Direccion e) { Direccion cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
