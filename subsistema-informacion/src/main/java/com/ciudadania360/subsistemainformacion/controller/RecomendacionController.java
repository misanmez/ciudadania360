package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.dto.recomendacion.RecomendacionRequest;
import com.ciudadania360.subsistemainformacion.application.dto.recomendacion.RecomendacionResponse;
import com.ciudadania360.subsistemainformacion.application.service.RecomendacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recomendaciones")
public class RecomendacionController {

    private final RecomendacionService service;

    public RecomendacionController(RecomendacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<RecomendacionResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public RecomendacionResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<RecomendacionResponse> create(@RequestBody RecomendacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public RecomendacionResponse update(@PathVariable UUID id, @RequestBody RecomendacionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
