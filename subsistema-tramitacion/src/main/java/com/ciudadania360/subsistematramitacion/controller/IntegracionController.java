package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.dto.integracion.IntegracionRequest;
import com.ciudadania360.subsistematramitacion.application.dto.integracion.IntegracionResponse;
import com.ciudadania360.subsistematramitacion.application.service.IntegracionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/integraciones")
public class IntegracionController {

    private final IntegracionService service;

    public IntegracionController(IntegracionService service) {
        this.service = service;
    }

    @GetMapping
    public List<IntegracionResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public IntegracionResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<IntegracionResponse> create(@RequestBody IntegracionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public IntegracionResponse update(@PathVariable UUID id, @RequestBody IntegracionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
