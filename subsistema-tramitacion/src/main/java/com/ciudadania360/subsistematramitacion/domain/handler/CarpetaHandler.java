package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.Carpeta;
import com.ciudadania360.subsistematramitacion.domain.repository.CarpetaRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class CarpetaHandler {
    private final CarpetaRepositorio repo;
    public CarpetaHandler(CarpetaRepositorio repo) { this.repo = repo; }

    public List<Carpeta> list() { return repo.findAll(); }
    public Carpeta get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Carpeta no encontrado")); }
    public Carpeta create(Carpeta e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public Carpeta update(UUID id, Carpeta e) { Carpeta cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
