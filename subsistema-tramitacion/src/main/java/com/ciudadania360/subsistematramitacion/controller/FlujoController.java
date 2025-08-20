package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.service.FlujoServicio;
import com.ciudadania360.subsistematramitacion.domain.entity.Flujo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/flujos")
public class FlujoController {
    private final FlujoServicio service;
    public FlujoController(FlujoServicio service) { this.service = service; }

    @GetMapping
    public List<Flujo> list() { return service.list(); }

    @GetMapping("/{id}")
    public Flujo get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Flujo create(@RequestBody Flujo e) { return service.create(e); }

    @PutMapping("/{id}")
    public Flujo update(@PathVariable("id") UUID id, @RequestBody Flujo e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
