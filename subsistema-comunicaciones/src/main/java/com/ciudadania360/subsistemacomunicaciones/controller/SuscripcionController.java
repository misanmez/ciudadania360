package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.service.SuscripcionServicio;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Suscripcion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/suscripcions")
public class SuscripcionController {
    private final SuscripcionServicio service;
    public SuscripcionController(SuscripcionServicio service) { this.service = service; }

    @GetMapping
    public List<Suscripcion> list() { return service.list(); }

    @GetMapping("/{id}")
    public Suscripcion get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Suscripcion create(@RequestBody Suscripcion e) { return service.create(e); }

    @PutMapping("/{id}")
    public Suscripcion update(@PathVariable("id") UUID id, @RequestBody Suscripcion e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
