package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.service.RecomendacionServicio;
import com.ciudadania360.subsistemainformacion.domain.entity.Recomendacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/recomendacions")
public class RecomendacionController {
    private final RecomendacionServicio service;
    public RecomendacionController(RecomendacionServicio service) { this.service = service; }

    @GetMapping
    public List<Recomendacion> list() { return service.list(); }

    @GetMapping("/{id}")
    public Recomendacion get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Recomendacion create(@RequestBody Recomendacion e) { return service.create(e); }

    @PutMapping("/{id}")
    public Recomendacion update(@PathVariable("id") UUID id, @RequestBody Recomendacion e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
