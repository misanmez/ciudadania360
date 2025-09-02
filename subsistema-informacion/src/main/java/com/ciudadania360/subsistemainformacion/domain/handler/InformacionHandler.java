package com.ciudadania360.subsistemainformacion.domain.handler;

import com.ciudadania360.subsistemainformacion.domain.entity.Informacion;
import com.ciudadania360.subsistemainformacion.domain.repository.InformacionRepository;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class InformacionHandler {
    private final InformacionRepository repo;
    public InformacionHandler(InformacionRepository repo) { this.repo = repo; }

    public List<Informacion> list() { return repo.findAll(); }
    public Informacion get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Informacion no encontrado")); }
    public Informacion create(Informacion e) { return repo.save(e); }
    public Informacion update(UUID id, Informacion e) { Informacion cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
