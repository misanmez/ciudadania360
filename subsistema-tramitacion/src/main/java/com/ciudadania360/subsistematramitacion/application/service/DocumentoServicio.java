package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Documento;
import com.ciudadania360.subsistematramitacion.domain.handler.DocumentoHandler;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DocumentoServicio {
    private final DocumentoHandler handler;
    public DocumentoServicio(DocumentoHandler handler) { this.handler = handler; }
    public List<Documento> list() { return handler.list(); }
    public Documento get(UUID id) { return handler.get(id); }
    public Documento create(Documento e) { return handler.create(e); }
    public Documento update(UUID id, Documento e) { return handler.update(id, e); }
    public void delete(UUID id) { handler.delete(id); }
}
