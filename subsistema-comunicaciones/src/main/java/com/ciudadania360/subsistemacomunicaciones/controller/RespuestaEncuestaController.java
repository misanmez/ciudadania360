package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.service.RespuestaEncuestaServicio;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.RespuestaEncuesta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/respuestaencuestas")
public class RespuestaEncuestaController {
    private final RespuestaEncuestaServicio service;
    public RespuestaEncuestaController(RespuestaEncuestaServicio service) { this.service = service; }

    @GetMapping
    public List<RespuestaEncuesta> list() { return service.list(); }

    @GetMapping("/{id}")
    public RespuestaEncuesta get(@PathVariable("id") UUID id) { return service.get(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RespuestaEncuesta create(@RequestBody RespuestaEncuesta e) { return service.create(e); }

    @PutMapping("/{id}")
    public RespuestaEncuesta update(@PathVariable("id") UUID id, @RequestBody RespuestaEncuesta e) { return service.update(id, e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
