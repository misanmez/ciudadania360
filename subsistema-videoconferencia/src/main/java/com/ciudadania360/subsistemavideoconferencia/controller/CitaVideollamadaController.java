package com.ciudadania360.subsistemavideoconferencia.controller;

import com.ciudadania360.subsistemavideoconferencia.application.dto.citavideollamada.CitaVideollamadaRequest;
import com.ciudadania360.subsistemavideoconferencia.application.dto.citavideollamada.CitaVideollamadaResponse;
import com.ciudadania360.subsistemavideoconferencia.application.service.CitaVideollamadaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/citas-videollamada")
public class CitaVideollamadaController {

    private final CitaVideollamadaService service;

    public CitaVideollamadaController(CitaVideollamadaService service) {
        this.service = service;
    }

    @GetMapping
    public List<CitaVideollamadaResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public CitaVideollamadaResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<CitaVideollamadaResponse> create(@RequestBody CitaVideollamadaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public CitaVideollamadaResponse update(@PathVariable UUID id, @RequestBody CitaVideollamadaRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
