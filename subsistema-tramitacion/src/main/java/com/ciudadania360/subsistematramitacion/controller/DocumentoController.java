package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.service.DocumentoServicio;
import com.ciudadania360.subsistematramitacion.domain.entity.Documento;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoController {
    private final DocumentoServicio service;
    public DocumentoController(DocumentoServicio service) { this.service = service; }

    @GetMapping
    public List<Documento> list() { return service.list(); }

    @GetMapping("/{id}")
    public Documento get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Documento create(@RequestBody Documento e) { return service.create(e); }

    @PutMapping("/{id}")
    public Documento update(@PathVariable("id") UUID id, @RequestBody Documento e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
