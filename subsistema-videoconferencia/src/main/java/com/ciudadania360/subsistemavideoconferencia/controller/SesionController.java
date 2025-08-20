package com.ciudadania360.subsistemavideoconferencia.controller;

import com.ciudadania360.subsistemavideoconferencia.application.service.SesionServicio;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.Sesion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/sesions")
public class SesionController {
    private final SesionServicio service;
    public SesionController(SesionServicio service) { this.service = service; }

    @GetMapping
    public List<Sesion> list() { return service.list(); }

    @GetMapping("/{id}")
    public Sesion get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sesion create(@RequestBody Sesion e) { return service.create(e); }

    @PutMapping("/{id}")
    public Sesion update(@PathVariable("id") UUID id, @RequestBody Sesion e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
