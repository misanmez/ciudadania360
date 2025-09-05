package com.ciudadania360.subsistemainterno.application.validator;

import com.ciudadania360.shared.exception.BadRequestException;
import com.ciudadania360.shared.exception.BusinessException;
import com.ciudadania360.subsistemainterno.application.dto.empleado.EmpleadoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Arrays;

@Component
public class EmpleadoValidator {

    private final ObjectMapper objectMapper;

    private static final List<String> ROLES_VALIDOS = Arrays.asList("ADMIN", "USER", "GESTOR");
    private static final List<String> ESTADOS_VALIDOS = Arrays.asList("ACTIVO", "INACTIVO", "SUSPENDIDO");

    public EmpleadoValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void validateForCreate(EmpleadoRequest request) {
        checkRequiredFields(request);
        checkFormats(request);
        checkBusinessRules(request, true);
    }

    public void validateForUpdate(EmpleadoRequest request) {
        checkFormats(request);
        checkBusinessRules(request, false);
    }

    private void checkRequiredFields(EmpleadoRequest r) {
        if (r.getNombre() == null || r.getNombre().isBlank())
            throw new BadRequestException("Nombre obligatorio");
        if (r.getApellidos() == null || r.getApellidos().isBlank())
            throw new BadRequestException("Apellidos obligatorios");
        if (r.getDepartamentoId() == null)
            throw new BadRequestException("Departamento obligatorio");
    }

    private void checkFormats(EmpleadoRequest r) {
        if (r.getEmail() != null &&
                !r.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
            throw new BadRequestException("Email inválido");

        if (r.getTelefono() != null &&
                !r.getTelefono().matches("^\\+?[0-9]{9,15}$"))
            throw new BadRequestException("Teléfono inválido");

        if (r.getRol() != null && !ROLES_VALIDOS.contains(r.getRol().toUpperCase()))
            throw new BadRequestException("Rol inválido. Valores válidos: " + ROLES_VALIDOS);

        if (r.getMetadata() != null) {
            try {
                objectMapper.readTree(r.getMetadata());
            } catch (Exception ex) {
                throw new BadRequestException("Metadata debe ser un JSON válido");
            }
        }
    }

    private void checkBusinessRules(EmpleadoRequest r, boolean isCreate) {
        if (isCreate && isEmpleadoDuplicado(r.getEmail()))
            throw new BusinessException("Ya existe un empleado con ese email");
    }

    private boolean isEmpleadoDuplicado(String email) {
        // TODO: implementar verificación real en base de datos
        return false;
    }
}
