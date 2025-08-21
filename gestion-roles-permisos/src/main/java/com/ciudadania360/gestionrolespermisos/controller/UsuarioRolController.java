package com.ciudadania360.gestionrolespermisos.controller;

import com.ciudadania360.gestionrolespermisos.application.dto.usuariorol.UsuarioRolRequest;
import com.ciudadania360.gestionrolespermisos.application.dto.usuariorol.UsuarioRolResponse;
import com.ciudadania360.gestionrolespermisos.application.service.UsuarioRolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarioroles")
@RequiredArgsConstructor
public class UsuarioRolController {

    private final UsuarioRolService service;

    @GetMapping
    public List<UsuarioRolResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public UsuarioRolResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<UsuarioRolResponse> create(@RequestBody UsuarioRolRequest request) {
        UsuarioRolResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public UsuarioRolResponse update(@PathVariable UUID id, @RequestBody UsuarioRolRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
