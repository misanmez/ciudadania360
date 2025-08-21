package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.ReglaClasificacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.ReglaClasificacionResponse;
import com.ciudadania360.subsistemaciudadano.application.service.ReglaClasificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reglaclasificacions")
public class ReglaClasificacionController {

    private final ReglaClasificacionService service;

    public ReglaClasificacionController(ReglaClasificacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReglaClasificacionResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public ReglaClasificacionResponse get(@PathVariable("id") UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<ReglaClasificacionResponse> create(@RequestBody ReglaClasificacionRequest request) {
        ReglaClasificacionResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ReglaClasificacionResponse update(@PathVariable("id") UUID id, @RequestBody ReglaClasificacionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
