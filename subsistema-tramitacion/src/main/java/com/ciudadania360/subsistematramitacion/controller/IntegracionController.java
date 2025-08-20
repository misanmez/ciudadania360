package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.service.IntegracionServicio;
import com.ciudadania360.subsistematramitacion.domain.entity.Integracion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/integracions")
public class IntegracionController {
    private final IntegracionServicio service;
    public IntegracionController(IntegracionServicio service) { this.service = service; }

    @GetMapping
    public List<Integracion> list() { return service.list(); }

    @GetMapping("/{id}")
    public Integracion get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integracion create(@RequestBody Integracion e) { return service.create(e); }

    @PutMapping("/{id}")
    public Integracion update(@PathVariable("id") UUID id, @RequestBody Integracion e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
