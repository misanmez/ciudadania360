package com.ciudadania360.subsistemainformacion.domain.handler;

import com.ciudadania360.subsistemainformacion.domain.entity.Recomendacion;
import com.ciudadania360.subsistemainformacion.domain.repository.RecomendacionRepository;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class RecomendacionHandler {
    private final RecomendacionRepository repo;
    public RecomendacionHandler(RecomendacionRepository repo) { this.repo = repo; }

    public List<Recomendacion> list() { return repo.findAll(); }
    public Recomendacion get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Recomendacion no encontrado")); }
    public Recomendacion create(Recomendacion e) { return repo.save(e); }
    public Recomendacion update(UUID id, Recomendacion e) { Recomendacion cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
