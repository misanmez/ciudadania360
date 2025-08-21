package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.InteraccionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.InteraccionResponse;
import com.ciudadania360.subsistemaciudadano.application.service.InteraccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/interaccions")
public class InteraccionController {

    private final InteraccionService service;

    public InteraccionController(InteraccionService service) {
        this.service = service;
    }

    @GetMapping
    public List<InteraccionResponse> list() { return service.list();
    }

    @GetMapping("/{id}")
    public InteraccionResponse get(@PathVariable("id") UUID id)  {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<InteraccionResponse> create(@RequestBody InteraccionRequest request) {
        InteraccionResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public InteraccionResponse update(@PathVariable("id") UUID id, @RequestBody InteraccionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
