package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.ReglaClasificacionServicio;
import com.ciudadania360.subsistemaciudadano.domain.entity.ReglaClasificacion;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ubicacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reglaclasificacions")
public class ReglaClasificacionController {
    private final ReglaClasificacionServicio servicio;

    public ReglaClasificacionController(ReglaClasificacionServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<ReglaClasificacion>> listar() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReglaClasificacion> obtener(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ReglaClasificacion> crear(@RequestBody ReglaClasificacion e) {
        ReglaClasificacion created = servicio.crear(e);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReglaClasificacion> actualizar(@PathVariable("id") UUID id, @RequestBody ReglaClasificacion e) {
        return ResponseEntity.ok(servicio.actualizar(id, e));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") UUID id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
