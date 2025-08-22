package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.dto.instruccion.InstruccionRequest;
import com.ciudadania360.subsistemainformacion.application.dto.instruccion.InstruccionResponse;
import com.ciudadania360.subsistemainformacion.application.service.InstruccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/instrucciones")
public class InstruccionController {

    private final InstruccionService service;

    public InstruccionController(InstruccionService service) {
        this.service = service;
    }

    @GetMapping
    public List<InstruccionResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public InstruccionResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<InstruccionResponse> create(@RequestBody InstruccionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public InstruccionResponse update(@PathVariable UUID id, @RequestBody InstruccionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
