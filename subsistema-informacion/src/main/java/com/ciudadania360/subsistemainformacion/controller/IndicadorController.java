package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.dto.indicador.IndicadorRequest;
import com.ciudadania360.subsistemainformacion.application.dto.indicador.IndicadorResponse;
import com.ciudadania360.subsistemainformacion.application.service.IndicadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/indicadores")
public class IndicadorController {

    private final IndicadorService service;

    public IndicadorController(IndicadorService service) {
        this.service = service;
    }

    @GetMapping
    public List<IndicadorResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public IndicadorResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<IndicadorResponse> create(@RequestBody IndicadorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public IndicadorResponse update(@PathVariable UUID id, @RequestBody IndicadorRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
