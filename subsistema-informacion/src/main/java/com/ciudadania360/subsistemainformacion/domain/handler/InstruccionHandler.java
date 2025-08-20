package com.ciudadania360.subsistemainformacion.domain.handler;

import com.ciudadania360.subsistemainformacion.domain.entity.Instruccion;
import com.ciudadania360.subsistemainformacion.domain.repository.InstruccionRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class InstruccionHandler {
    private final InstruccionRepositorio repo;
    public InstruccionHandler(InstruccionRepositorio repo) { this.repo = repo; }

    public List<Instruccion> list() { return repo.findAll(); }
    public Instruccion get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Instruccion no encontrado")); }
    public Instruccion create(Instruccion e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public Instruccion update(UUID id, Instruccion e) { Instruccion cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
