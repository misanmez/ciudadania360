package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.Documento;
import com.ciudadania360.subsistematramitacion.domain.repository.DocumentoRepositorio;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class DocumentoHandler {
    private final DocumentoRepositorio repo;
    public DocumentoHandler(DocumentoRepositorio repo) { this.repo = repo; }

    public List<Documento> list() { return repo.findAll(); }
    public Documento get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Documento no encontrado")); }
    public Documento create(Documento e) { if(e.getId()==null) e.setId(UUID.randomUUID()); return repo.save(e); }
    public Documento update(UUID id, Documento e) { Documento cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
