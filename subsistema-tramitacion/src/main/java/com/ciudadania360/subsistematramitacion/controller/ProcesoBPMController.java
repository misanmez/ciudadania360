package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.dto.procesobpm.ProcesoBPMRequest;
import com.ciudadania360.subsistematramitacion.application.dto.procesobpm.ProcesoBPMResponse;
import com.ciudadania360.subsistematramitacion.application.service.ProcesoBPMService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/procesos-bpm")
public class ProcesoBPMController {

    private final ProcesoBPMService service;

    public ProcesoBPMController(ProcesoBPMService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProcesoBPMResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public ProcesoBPMResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<ProcesoBPMResponse> create(@RequestBody ProcesoBPMRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ProcesoBPMResponse update(@PathVariable UUID id, @RequestBody ProcesoBPMRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
