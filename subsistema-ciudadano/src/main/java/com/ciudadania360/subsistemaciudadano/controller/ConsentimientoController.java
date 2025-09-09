package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.shared.application.dto.consentimiento.ConsentimientoRequest;
import com.ciudadania360.shared.application.dto.consentimiento.ConsentimientoResponse;
import com.ciudadania360.subsistemaciudadano.application.service.ConsentimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/consentimientos")
public class ConsentimientoController {

    private final ConsentimientoService service;

    public ConsentimientoController(ConsentimientoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ConsentimientoResponse>> list() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsentimientoResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping
    public ResponseEntity<ConsentimientoResponse> create(@RequestBody ConsentimientoRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsentimientoResponse> update(
            @PathVariable UUID id,
            @RequestBody ConsentimientoRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
