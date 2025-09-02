package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.TareaBPM;
import com.ciudadania360.subsistematramitacion.domain.repository.TareaBPMRepository;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class TareaBPMHandler {
    private final TareaBPMRepository repo;
    public TareaBPMHandler(TareaBPMRepository repo) { this.repo = repo; }

    public List<TareaBPM> list() { return repo.findAll(); }
    public TareaBPM get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("TareaBPM no encontrado")); }
    public TareaBPM create(TareaBPM e) { return repo.save(e); }
    public TareaBPM update(UUID id, TareaBPM e) { TareaBPM cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
