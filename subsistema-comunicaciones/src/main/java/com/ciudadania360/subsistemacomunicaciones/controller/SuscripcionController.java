package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.dto.suscripcion.SuscripcionRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.suscripcion.SuscripcionResponse;
import com.ciudadania360.subsistemacomunicaciones.application.service.SuscripcionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/suscripciones")
public class SuscripcionController {

    private final SuscripcionService service;

    public SuscripcionController(SuscripcionService service) {
        this.service = service;
    }

    @GetMapping
    public List<SuscripcionResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public SuscripcionResponse get(@PathVariable("id") UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<SuscripcionResponse> create(@RequestBody SuscripcionRequest request) {
        SuscripcionResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public SuscripcionResponse update(@PathVariable("id") UUID id, @RequestBody SuscripcionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
