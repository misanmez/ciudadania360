package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.dto.carpeta.CarpetaRequest;
import com.ciudadania360.subsistematramitacion.application.dto.carpeta.CarpetaResponse;
import com.ciudadania360.subsistematramitacion.application.service.CarpetaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/carpetas")
public class CarpetaController {

    private final CarpetaService service;

    public CarpetaController(CarpetaService service) {
        this.service = service;
    }

    @GetMapping
    public List<CarpetaResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public CarpetaResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<CarpetaResponse> create(@RequestBody CarpetaRequest request) {
        CarpetaResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public CarpetaResponse update(@PathVariable UUID id, @RequestBody CarpetaRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
