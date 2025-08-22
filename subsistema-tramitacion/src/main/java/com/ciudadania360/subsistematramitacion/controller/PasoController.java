package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.dto.paso.PasoRequest;
import com.ciudadania360.subsistematramitacion.application.dto.paso.PasoResponse;
import com.ciudadania360.subsistematramitacion.application.service.PasoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pasos")
public class PasoController {

    private final PasoService service;

    public PasoController(PasoService service) {
        this.service = service;
    }

    @GetMapping
    public List<PasoResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public PasoResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<PasoResponse> create(@RequestBody PasoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public PasoResponse update(@PathVariable UUID id, @RequestBody PasoRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
