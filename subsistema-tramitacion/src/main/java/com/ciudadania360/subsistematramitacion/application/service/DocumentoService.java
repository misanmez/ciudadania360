package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Documento;
import com.ciudadania360.subsistematramitacion.domain.handler.DocumentoHandler;
import com.ciudadania360.subsistematramitacion.application.dto.documento.DocumentoRequest;
import com.ciudadania360.subsistematramitacion.application.dto.documento.DocumentoResponse;
import com.ciudadania360.subsistematramitacion.application.mapper.DocumentoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DocumentoService {

    private final DocumentoHandler handler;
    private final DocumentoMapper mapper;

    public DocumentoService(DocumentoHandler handler, DocumentoMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<DocumentoResponse> list() {
        return handler.list().stream().map(mapper::toResponse).toList();
    }

    public DocumentoResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public DocumentoResponse create(DocumentoRequest request) {
        Documento entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);
        return mapper.toResponse(handler.create(entity));
    }

    public DocumentoResponse update(UUID id, DocumentoRequest request) {
        Documento existing = handler.get(id);
        mapper.updateEntity(existing, request);
        return mapper.toResponse(handler.update(id, existing));
    }

    public void delete(UUID id) {
        handler.delete(id);
    }
}
