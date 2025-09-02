package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.Carpeta;
import com.ciudadania360.subsistematramitacion.domain.repository.CarpetaRepository;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class CarpetaHandler {
    private final CarpetaRepository repo;
    public CarpetaHandler(CarpetaRepository repo) { this.repo = repo; }

    public List<Carpeta> list() { return repo.findAll(); }
    public Carpeta get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Carpeta no encontrado")); }
    public Carpeta create(Carpeta e) { return repo.save(e); }
    public Carpeta update(UUID id, Carpeta e) { Carpeta cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
