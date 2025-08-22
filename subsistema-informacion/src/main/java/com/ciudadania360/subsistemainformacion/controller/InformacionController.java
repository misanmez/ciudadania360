package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.dto.informacion.InformacionRequest;
import com.ciudadania360.subsistemainformacion.application.dto.informacion.InformacionResponse;
import com.ciudadania360.subsistemainformacion.application.service.InformacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/informaciones")
public class InformacionController {

    private final InformacionService service;

    public InformacionController(InformacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<InformacionResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public InformacionResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<InformacionResponse> create(@RequestBody InformacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public InformacionResponse update(@PathVariable UUID id, @RequestBody InformacionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
