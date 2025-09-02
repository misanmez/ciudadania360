package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.Integracion;
import com.ciudadania360.subsistematramitacion.domain.repository.IntegracionRepository;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class IntegracionHandler {
    private final IntegracionRepository repo;
    public IntegracionHandler(IntegracionRepository repo) { this.repo = repo; }

    public List<Integracion> list() { return repo.findAll(); }
    public Integracion get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Integracion no encontrado")); }
    public Integracion create(Integracion e) { return repo.save(e); }
    public Integracion update(UUID id, Integracion e) { Integracion cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
