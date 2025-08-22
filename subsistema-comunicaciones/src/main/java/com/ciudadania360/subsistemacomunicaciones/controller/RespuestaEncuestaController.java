package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.dto.respuestaencuesta.RespuestaEncuestaRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.respuestaencuesta.RespuestaEncuestaResponse;
import com.ciudadania360.subsistemacomunicaciones.application.service.RespuestaEncuestaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/respuestas-encuestas")
public class RespuestaEncuestaController {

    private final RespuestaEncuestaService service;

    public RespuestaEncuestaController(RespuestaEncuestaService service) {
        this.service = service;
    }

    @GetMapping
    public List<RespuestaEncuestaResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public RespuestaEncuestaResponse get(@PathVariable("id") UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<RespuestaEncuestaResponse> create(@RequestBody RespuestaEncuestaRequest request) {
        RespuestaEncuestaResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public RespuestaEncuestaResponse update(@PathVariable("id") UUID id, @RequestBody RespuestaEncuestaRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
