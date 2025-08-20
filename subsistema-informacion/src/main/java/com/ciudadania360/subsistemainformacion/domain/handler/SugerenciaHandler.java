package com.ciudadania360.subsistemainformacion.domain.handler;

import com.ciudadania360.subsistemainformacion.domain.entity.Sugerencia;
import com.ciudadania360.subsistemainformacion.domain.repository.SugerenciaRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class SugerenciaHandler {
    private final SugerenciaRepositorio repo;
    public SugerenciaHandler(SugerenciaRepositorio repo) { this.repo = repo; }

    public List<Sugerencia> list() { return repo.findAll(); }
    public Sugerencia get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Sugerencia no encontrado")); }
    public Sugerencia create(Sugerencia e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public Sugerencia update(UUID id, Sugerencia e) { Sugerencia cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
