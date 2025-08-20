package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.service.IncidenciaContrataServicio;
import com.ciudadania360.subsistematramitacion.domain.entity.IncidenciaContrata;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/incidenciacontratas")
public class IncidenciaContrataController {
    private final IncidenciaContrataServicio service;
    public IncidenciaContrataController(IncidenciaContrataServicio service) { this.service = service; }

    @GetMapping
    public List<IncidenciaContrata> list() { return service.list(); }

    @GetMapping("/{id}")
    public IncidenciaContrata get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IncidenciaContrata create(@RequestBody IncidenciaContrata e) { return service.create(e); }

    @PutMapping("/{id}")
    public IncidenciaContrata update(@PathVariable("id") UUID id, @RequestBody IncidenciaContrata e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
