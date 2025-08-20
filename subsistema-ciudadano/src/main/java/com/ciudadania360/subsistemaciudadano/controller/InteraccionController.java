package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.InteraccionServicio;
import com.ciudadania360.subsistemaciudadano.domain.entity.Interaccion;
import com.ciudadania360.subsistemaciudadano.domain.entity.ReglaClasificacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/interaccions")
public class InteraccionController {
    private final InteraccionServicio servicio;

    public InteraccionController(InteraccionServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<Interaccion>> listar() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Interaccion> obtener(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Interaccion> crear(@RequestBody Interaccion e) {
        Interaccion created = servicio.crear(e);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Interaccion> actualizar(@PathVariable("id") UUID id, @RequestBody Interaccion e) {
        return ResponseEntity.ok(servicio.actualizar(id, e));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") UUID id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
