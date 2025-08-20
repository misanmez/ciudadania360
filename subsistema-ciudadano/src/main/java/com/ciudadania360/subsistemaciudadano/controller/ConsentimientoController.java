package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.ConsentimientoServicio;
import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.ciudadania360.subsistemaciudadano.domain.entity.Consentimiento;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/consentimientos")
public class ConsentimientoController {
    private final ConsentimientoServicio service;
    public ConsentimientoController(ConsentimientoServicio service) { this.service = service; }

    @GetMapping
    public List<Consentimiento> list() { return service.list(); }

    @GetMapping("/{id}")
    public Consentimiento get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    public ResponseEntity<Consentimiento> crear(@RequestBody Consentimiento e) {
        Consentimiento created = service.create(e);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @PutMapping("/{id}")
    public Consentimiento update(@PathVariable("id") UUID id, @RequestBody Consentimiento e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
