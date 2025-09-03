package com.ciudadania360.shared.exception;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Estructura uniforme de respuesta de error que se devuelve a los clientes REST.
 * Usamos record para inmutabilidad y concisión.
 */
public record ErrorResponse(
        String transactionId,
        String code,
        String message,
        OffsetDateTime timestamp,
        Map<String, Object> details
) {}
