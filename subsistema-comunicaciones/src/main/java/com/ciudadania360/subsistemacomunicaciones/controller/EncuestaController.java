package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.service.EncuestaServicio;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Encuesta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/encuestas")
public class EncuestaController {
    private final EncuestaServicio service;
    public EncuestaController(EncuestaServicio service) { this.service = service; }

    @GetMapping
    public List<Encuesta> list() { return service.list(); }

    @GetMapping("/{id}")
    public Encuesta get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Encuesta create(@RequestBody Encuesta e) { return service.create(e); }

    @PutMapping("/{id}")
    public Encuesta update(@PathVariable("id") UUID id, @RequestBody Encuesta e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
