package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.service.ContrataServicio;
import com.ciudadania360.subsistematramitacion.domain.entity.Contrata;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/contratas")
public class ContrataController {
    private final ContrataServicio service;
    public ContrataController(ContrataServicio service) { this.service = service; }

    @GetMapping
    public List<Contrata> list() { return service.list(); }

    @GetMapping("/{id}")
    public Contrata get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contrata create(@RequestBody Contrata e) { return service.create(e); }

    @PutMapping("/{id}")
    public Contrata update(@PathVariable("id") UUID id, @RequestBody Contrata e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
