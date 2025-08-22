package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.dto.comunicacion.ComunicacionRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.comunicacion.ComunicacionResponse;
import com.ciudadania360.subsistemacomunicaciones.application.service.ComunicacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comunicaciones")
public class ComunicacionController {

    private final ComunicacionService service;

    public ComunicacionController(ComunicacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<ComunicacionResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public ComunicacionResponse get(@PathVariable("id") UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<ComunicacionResponse> create(@RequestBody ComunicacionRequest request) {
        ComunicacionResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ComunicacionResponse update(@PathVariable("id") UUID id, @RequestBody ComunicacionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
