package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.dto.flujo.FlujoRequest;
import com.ciudadania360.subsistematramitacion.application.dto.flujo.FlujoResponse;
import com.ciudadania360.subsistematramitacion.application.service.FlujoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/flujos")
public class FlujoController {

    private final FlujoService service;

    public FlujoController(FlujoService service) {
        this.service = service;
    }

    @GetMapping
    public List<FlujoResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public FlujoResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<FlujoResponse> create(@RequestBody FlujoRequest request) {
        FlujoResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public FlujoResponse update(@PathVariable UUID id, @RequestBody FlujoRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
