package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.DireccionServicio;
import com.ciudadania360.subsistemaciudadano.domain.entity.Direccion;
import com.ciudadania360.subsistemaciudadano.domain.entity.Interaccion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/direccions")
public class DireccionController {
    private final DireccionServicio service;
    public DireccionController(DireccionServicio service) { this.service = service; }

    @GetMapping
    public List<Direccion> list() { return service.list(); }

    @GetMapping("/{id}")
    public Direccion get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    public ResponseEntity<Direccion> crear(@RequestBody Direccion e) {
        Direccion created = service.create(e);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public Direccion update(@PathVariable("id") UUID id, @RequestBody Direccion e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
