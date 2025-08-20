package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.service.DudaServicio;
import com.ciudadania360.subsistemainformacion.domain.entity.Duda;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/dudas")
public class DudaController {
    private final DudaServicio service;
    public DudaController(DudaServicio service) { this.service = service; }

    @GetMapping
    public List<Duda> list() { return service.list(); }

    @GetMapping("/{id}")
    public Duda get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Duda create(@RequestBody Duda e) { return service.create(e); }

    @PutMapping("/{id}")
    public Duda update(@PathVariable("id") UUID id, @RequestBody Duda e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
