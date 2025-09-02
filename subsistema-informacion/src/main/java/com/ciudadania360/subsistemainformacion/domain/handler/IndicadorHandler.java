package com.ciudadania360.subsistemainformacion.domain.handler;

import com.ciudadania360.subsistemainformacion.domain.entity.Indicador;
import com.ciudadania360.subsistemainformacion.domain.repository.IndicadorRepository;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class IndicadorHandler {
    private final IndicadorRepository repo;
    public IndicadorHandler(IndicadorRepository repo) { this.repo = repo; }

    public List<Indicador> list() { return repo.findAll(); }
    public Indicador get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Indicador no encontrado")); }
    public Indicador create(Indicador e) { return repo.save(e); }
    public Indicador update(UUID id, Indicador e) { Indicador cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
