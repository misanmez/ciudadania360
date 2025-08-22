package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.dto.tareabpm.TareaBPMRequest;
import com.ciudadania360.subsistematramitacion.application.dto.tareabpm.TareaBPMResponse;
import com.ciudadania360.subsistematramitacion.application.service.TareaBPMService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tareas-bpm")
public class TareaBPMController {

    private final TareaBPMService service;

    public TareaBPMController(TareaBPMService service) {
        this.service = service;
    }

    @GetMapping
    public List<TareaBPMResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public TareaBPMResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<TareaBPMResponse> create(@RequestBody TareaBPMRequest request) {
        TareaBPMResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public TareaBPMResponse update(@PathVariable UUID id, @RequestBody TareaBPMRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
