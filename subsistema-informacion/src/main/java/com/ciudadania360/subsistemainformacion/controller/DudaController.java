package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.dto.duda.DudaRequest;
import com.ciudadania360.subsistemainformacion.application.dto.duda.DudaResponse;
import com.ciudadania360.subsistemainformacion.application.service.DudaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dudas")
public class DudaController {

    private final DudaService service;

    public DudaController(DudaService service) {
        this.service = service;
    }

    @GetMapping
    public List<DudaResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public DudaResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<DudaResponse> create(@RequestBody DudaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public DudaResponse update(@PathVariable UUID id, @RequestBody DudaRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
