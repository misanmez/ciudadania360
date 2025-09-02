package com.ciudadania360.subsistemavideoconferencia.domain.handler;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.Sesion;
import com.ciudadania360.subsistemavideoconferencia.domain.repository.SesionRepository;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class SesionHandler {
    private final SesionRepository repo;
    public SesionHandler(SesionRepository repo) { this.repo = repo; }

    public List<Sesion> list() { return repo.findAll(); }
    public Sesion get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Sesion no encontrado")); }
    public Sesion create(Sesion e) { return repo.save(e); }
    public Sesion update(UUID id, Sesion e) { Sesion cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
