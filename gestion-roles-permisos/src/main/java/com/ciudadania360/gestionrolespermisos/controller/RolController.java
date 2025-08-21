package com.ciudadania360.gestionrolespermisos.controller;

import com.ciudadania360.gestionrolespermisos.application.dto.rol.RolRequest;
import com.ciudadania360.gestionrolespermisos.application.dto.rol.RolResponse;
import com.ciudadania360.gestionrolespermisos.application.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @GetMapping
    public List<RolResponse> list() {
        return rolService.list();
    }

    @PostMapping
    public ResponseEntity<RolResponse> create(@RequestBody RolRequest request) {
        RolResponse created = rolService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @PutMapping("/{id}")
    public RolResponse update(@PathVariable UUID id, @RequestBody RolRequest request) {
        return rolService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        rolService.delete(id);
    }
}
