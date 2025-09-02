package com.ciudadania360.subsistemacomunicaciones.domain.handler;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.RespuestaEncuesta;
import com.ciudadania360.subsistemacomunicaciones.domain.repository.RespuestaEncuestaRepository;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class RespuestaEncuestaHandler {
    private final RespuestaEncuestaRepository repo;
    public RespuestaEncuestaHandler(RespuestaEncuestaRepository repo) { this.repo = repo; }

    public List<RespuestaEncuesta> list() { return repo.findAll(); }
    public RespuestaEncuesta get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("RespuestaEncuesta no encontrado")); }
    public RespuestaEncuesta create(RespuestaEncuesta e) { return repo.save(e); }
    public RespuestaEncuesta update(UUID id, RespuestaEncuesta e) { RespuestaEncuesta cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
