package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.Documento;
import com.ciudadania360.subsistematramitacion.domain.repository.DocumentoRepository;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class DocumentoHandler {
    private final DocumentoRepository repo;
    public DocumentoHandler(DocumentoRepository repo) { this.repo = repo; }

    public List<Documento> list() { return repo.findAll(); }
    public Documento get(UUID id) { return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Documento no encontrado")); }
    public Documento create(Documento e) { return repo.save(e); }
    public Documento update(UUID id, Documento e) { Documento cur = get(id); e.setId(cur.getId()); return repo.save(e); }
    public void delete(UUID id) { repo.deleteById(id); }
}
