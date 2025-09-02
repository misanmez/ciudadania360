package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.Contrata;
import com.ciudadania360.subsistematramitacion.domain.repository.ContrataRepository;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class ContrataHandler {
    private final ContrataRepository repo;
    public ContrataHandler(ContrataRepository repo) { this.repo = repo; }

    public List<Contrata> list() { return repo.findAll(); }
    public Contrata get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Contrata no encontrado")); }
    public Contrata create(Contrata e) { return repo.save(e); }
    public Contrata update(UUID id, Contrata e) { Contrata cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
