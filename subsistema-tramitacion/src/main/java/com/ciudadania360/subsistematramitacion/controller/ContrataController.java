package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.dto.contrata.ContrataRequest;
import com.ciudadania360.subsistematramitacion.application.dto.contrata.ContrataResponse;
import com.ciudadania360.subsistematramitacion.application.service.ContrataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contratas")
public class ContrataController {

    private final ContrataService service;

    public ContrataController(ContrataService service) {
        this.service = service;
    }

    @GetMapping
    public List<ContrataResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public ContrataResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<ContrataResponse> create(@RequestBody ContrataRequest request) {
        ContrataResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ContrataResponse update(@PathVariable UUID id, @RequestBody ContrataRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
