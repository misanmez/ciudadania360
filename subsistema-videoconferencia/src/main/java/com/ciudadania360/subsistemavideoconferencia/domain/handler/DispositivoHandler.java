package com.ciudadania360.subsistemavideoconferencia.domain.handler;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.Dispositivo;
import com.ciudadania360.subsistemavideoconferencia.domain.repository.DispositivoRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class DispositivoHandler {
    private final DispositivoRepositorio repo;
    public DispositivoHandler(DispositivoRepositorio repo) { this.repo = repo; }

    public List<Dispositivo> list() { return repo.findAll(); }
    public Dispositivo get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Dispositivo no encontrado")); }
    public Dispositivo create(Dispositivo e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public Dispositivo update(UUID id, Dispositivo e) { Dispositivo cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
