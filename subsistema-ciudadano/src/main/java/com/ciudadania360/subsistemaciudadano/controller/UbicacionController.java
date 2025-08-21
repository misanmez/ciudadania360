package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.ubicacion.UbicacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.ubicacion.UbicacionResponse;
import com.ciudadania360.subsistemaciudadano.application.service.UbicacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ubicacions")
public class UbicacionController {

    private final UbicacionService service;

    public UbicacionController(UbicacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<UbicacionResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public UbicacionResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<UbicacionResponse> create(@RequestBody UbicacionRequest request) {
        UbicacionResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public UbicacionResponse update(@PathVariable UUID id, @RequestBody UbicacionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
