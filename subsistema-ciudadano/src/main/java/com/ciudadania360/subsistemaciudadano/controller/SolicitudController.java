package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.SolicitudServicio;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/solicituds")
public class SolicitudController {
    private final SolicitudServicio servicio;

    public SolicitudController(SolicitudServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<Solicitud>> listar() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> obtener(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Solicitud> crear(@RequestBody Solicitud e) {
        Solicitud created = servicio.crear(e);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> actualizar(@PathVariable("id") UUID id, @RequestBody Solicitud e) {
        return ResponseEntity.ok(servicio.actualizar(id, e));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") UUID id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
