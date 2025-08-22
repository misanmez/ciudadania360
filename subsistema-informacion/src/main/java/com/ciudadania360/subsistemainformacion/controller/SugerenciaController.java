package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.dto.sugerencia.SugerenciaRequest;
import com.ciudadania360.subsistemainformacion.application.dto.sugerencia.SugerenciaResponse;
import com.ciudadania360.subsistemainformacion.application.service.SugerenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sugerencias")
public class SugerenciaController {

    private final SugerenciaService service;

    public SugerenciaController(SugerenciaService service) {
        this.service = service;
    }

    @GetMapping
    public List<SugerenciaResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public SugerenciaResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<SugerenciaResponse> create(@RequestBody SugerenciaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public SugerenciaResponse update(@PathVariable UUID id, @RequestBody SugerenciaRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
