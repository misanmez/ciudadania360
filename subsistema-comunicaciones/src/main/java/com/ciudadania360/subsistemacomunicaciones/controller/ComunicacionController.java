package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.service.ComunicacionServicio;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Comunicacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/comunicacions")
public class ComunicacionController {
    private final ComunicacionServicio service;
    public ComunicacionController(ComunicacionServicio service) { this.service = service; }

    @GetMapping
    public List<Comunicacion> list() { return service.list(); }

    @GetMapping("/{id}")
    public Comunicacion get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comunicacion create(@RequestBody Comunicacion e) { return service.create(e); }

    @PutMapping("/{id}")
    public Comunicacion update(@PathVariable("id") UUID id, @RequestBody Comunicacion e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
