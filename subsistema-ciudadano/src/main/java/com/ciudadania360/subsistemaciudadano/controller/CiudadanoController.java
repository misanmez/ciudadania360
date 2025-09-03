package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.ciudadano.CiudadanoRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.ciudadano.CiudadanoResponse;
import com.ciudadania360.subsistemaciudadano.application.service.CiudadanoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ciudadanos")
public class CiudadanoController {

    private final CiudadanoService service;

    public CiudadanoController(CiudadanoService service) {
        this.service = service;
    }

    @GetMapping
    public List<CiudadanoResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public CiudadanoResponse get(@PathVariable("id") UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<CiudadanoResponse> create(@Valid @RequestBody CiudadanoRequest request) {
        CiudadanoResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public CiudadanoResponse update(@PathVariable("id") UUID id, @Valid @RequestBody CiudadanoRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
