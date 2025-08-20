package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.ProcesoBPM;
import com.ciudadania360.subsistematramitacion.domain.repository.ProcesoBPMRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class ProcesoBPMHandler {
    private final ProcesoBPMRepositorio repo;
    public ProcesoBPMHandler(ProcesoBPMRepositorio repo) { this.repo = repo; }

    public List<ProcesoBPM> list() { return repo.findAll(); }
    public ProcesoBPM get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("ProcesoBPM no encontrado")); }
    public ProcesoBPM create(ProcesoBPM e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public ProcesoBPM update(UUID id, ProcesoBPM e) { ProcesoBPM cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
