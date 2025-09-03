package com.ciudadania360.shared.exception;

import lombok.Getter;

// Para errores de negocio o reglas de tramitación (HTTP 422)
// Se lanza cuando la operación no puede completarse por reglas de negocio.
// Ejemplo: intentar cerrar una solicitud que ya está finalizada, duplicidad de incidencia, flujo inexistente.
// Se mantiene, sirve para todos los errores de negocio.
@Getter
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
