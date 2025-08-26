package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.repository.CiudadanoRepository;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class CiudadanoHandler {
    private final CiudadanoRepository repo;
    public CiudadanoHandler(CiudadanoRepository repo) { this.repo = repo; }

    public List<Ciudadano> list() { return repo.findAll(); }
    public Ciudadano get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Ciudadano no encontrado")); }
    public Ciudadano create(Ciudadano e) {
        return repo.save(e);
    }
    public Ciudadano update(UUID id, Ciudadano e) { Ciudadano cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
