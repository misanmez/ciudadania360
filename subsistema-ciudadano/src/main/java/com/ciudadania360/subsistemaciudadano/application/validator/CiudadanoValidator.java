package com.ciudadania360.subsistemaciudadano.application.validator;

import com.ciudadania360.shared.exception.BadRequestException;
import com.ciudadania360.shared.exception.BusinessException;
import com.ciudadania360.shared.application.dto.ciudadano.CiudadanoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CiudadanoValidator {

    private static final List<String> CANALES_VALIDOS = Arrays.asList("EMAIL", "TELEFONO", "PRESENCIAL");
    private static final List<String> ESTADOS_VALIDOS = Arrays.asList("ACTIVO", "INACTIVO", "SUSPENDIDO");

    private final ObjectMapper objectMapper;

    public CiudadanoValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void validateForCreate(CiudadanoRequest request) {
        checkRequiredFields(request);
        checkFormats(request);
        checkBusinessRules(request, true);
    }

    public void validateForUpdate(CiudadanoRequest request) {
        checkFormats(request);
        checkBusinessRules(request, false);
    }

    private void checkRequiredFields(CiudadanoRequest r) {
        if (r.getNifNie() == null || r.getNifNie().isBlank())
            throw new BadRequestException("NIF/NIE obligatorio");

        if (r.getNombre() == null || r.getNombre().isBlank())
            throw new BadRequestException("Nombre obligatorio");

        if (r.getApellidos() == null || r.getApellidos().isBlank())
            throw new BadRequestException("Apellidos obligatorios");

        if (r.getConsentimientoLOPD() == null)
            throw new BadRequestException("Consentimiento LOPD obligatorio");
    }

    private void checkFormats(CiudadanoRequest r) {

        if (r.getEmail() != null &&
                !r.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new BadRequestException("Email inválido");
        }

        if (r.getTelefono() != null &&
                !r.getTelefono().matches("^\\+?[0-9]{9,15}$")) {
            throw new BadRequestException("Teléfono inválido");
        }

        if (r.getNombre() != null && r.getNombre().length() > 100) {
            throw new BadRequestException("El nombre no puede superar 100 caracteres");
        }

        if (r.getApellidos() != null && r.getApellidos().length() > 150) {
            throw new BadRequestException("Los apellidos no pueden superar 150 caracteres");
        }

        if (r.getCanalPreferido() != null && !CANALES_VALIDOS.contains(r.getCanalPreferido().toUpperCase())) {
            throw new BadRequestException("Canal preferido inválido. Valores válidos: " + CANALES_VALIDOS);
        }

        if (r.getEstado() != null && !ESTADOS_VALIDOS.contains(r.getEstado().toUpperCase())) {
            throw new BadRequestException("Estado inválido. Valores válidos: " + ESTADOS_VALIDOS);
        }

        if (r.getMetadata() != null) {
            try {
                objectMapper.readTree(r.getMetadata());
            } catch (Exception ex) {
                throw new BadRequestException("Metadata debe ser un JSON válido");
            }
        }
    }

    private void checkBusinessRules(CiudadanoRequest r, boolean isCreate) {
        if (isCreate && isCiudadanoDuplicado(r.getNifNie())) {
            throw new BusinessException("Ya existe un ciudadano con ese NIF/NIE");
        }
    }

    private boolean isCiudadanoDuplicado(String nifNie) {
        // TODO: implementar comprobación real contra base de datos
        return false;
    }
}
