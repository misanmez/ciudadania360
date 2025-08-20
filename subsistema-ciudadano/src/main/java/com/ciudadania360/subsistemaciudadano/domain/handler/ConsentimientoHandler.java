package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Consentimiento;
import com.ciudadania360.subsistemaciudadano.domain.repository.ConsentimientoRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class ConsentimientoHandler {
    private final ConsentimientoRepositorio repo;
    public ConsentimientoHandler(ConsentimientoRepositorio repo) { this.repo = repo; }

    public List<Consentimiento> list() { return repo.findAll(); }
    public Consentimiento get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Consentimiento no encontrado")); }
    public Consentimiento create(Consentimiento e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public Consentimiento update(UUID id, Consentimiento e) { Consentimiento cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
