package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.shared.exception.BusinessException;
import com.ciudadania360.shared.exception.SystemException;
import com.ciudadania360.subsistemaciudadano.application.dto.ciudadano.CiudadanoRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.ciudadano.CiudadanoResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.CiudadanoMapper;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.handler.CiudadanoHandler;
import com.ciudadania360.shared.exception.BadRequestException;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CiudadanoService {

    private final CiudadanoHandler handler;
    private final CiudadanoMapper mapper;

    public CiudadanoService(CiudadanoHandler handler, CiudadanoMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public List<CiudadanoResponse> list() {
        return handler.list().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public CiudadanoResponse get(UUID id) {
        return mapper.toResponse(handler.get(id));
    }

    public CiudadanoResponse create(CiudadanoRequest request) {
        validateRequest(request);

        Ciudadano entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        entity.setVersion(0L);

        Ciudadano created = handler.create(entity);
        return mapper.toResponse(created);
    }

    public CiudadanoResponse update(UUID id, CiudadanoRequest request) {
        validateRequest(request);

        Ciudadano existing = handler.get(id);
        mapper.updateEntity(existing, request);

        Ciudadano updated = handler.update(id, existing);
        return mapper.toResponse(updated);
    }

    public void delete(UUID id) {
        handler.delete(id);
    }

    private void validateRequest(CiudadanoRequest request) {

        // --- BadRequestException: campos obligatorios ---
        if (request.getNifNie() == null || request.getNifNie().isBlank()) {
            throw new BadRequestException("NIF/NIE obligatorio");
        }

        if (request.getEmail() != null &&
                !request.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new BadRequestException("Email inválido");
        }

        // --- ValidationException: reglas de validación más complejas ---
        if (request.getNombre() != null && request.getNombre().length() > 50) {
            throw new ValidationException("El nombre no puede superar 50 caracteres");
        }

        if (request.getTelefono() != null &&
                !request.getTelefono().matches("^\\+?[0-9]{9,15}$")) {
            throw new ValidationException("Teléfono inválido");
        }

        // --- BusinessException: reglas de negocio ---
        if (isCiudadanoDuplicado(request.getNifNie())) {
            throw new BusinessException("Ya existe un ciudadano con ese NIF/NIE");
        }

        // --- SecurityException: comprobación de permisos ---
        if (!usuarioActualTienePermiso("CREAR_CIUDADANO")) {
            throw new SecurityException("Usuario no autorizado para crear ciudadanos");
        }

        // --- SystemException: error de sistema simulado ---
        try {
            generarIdTemporal(request);
        } catch (Exception ex) {
            throw new SystemException("Error al generar ID temporal", ex);
        }
    }

    // Métodos auxiliares de ejemplo
    private boolean isCiudadanoDuplicado(String nifNie) {
        // Simula una comprobación de duplicados en la base de datos
        return false;
    }

    private boolean usuarioActualTienePermiso(String permiso) {
        // Simula verificación de permisos
        return true;
    }

    private void generarIdTemporal(CiudadanoRequest request) {
        // Simula un fallo de sistema (solo de ejemplo)
        // throw new RuntimeException("Fallo al generar ID");
    }

}
