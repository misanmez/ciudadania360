package com.ciudadania360.shared.exception;

import lombok.Getter;

// Para errores de autenticación/autorization (HTTP 401/403)
// Se usa para denegar acceso si el usuario no tiene permisos o rol.
// Ejemplo: intento de acceso a recurso protegido sin rol adecuado.
// Se mantiene, puede centralizar HTTP 401/403 y logging.
@Getter
public class SecurityException extends RuntimeException {
    public SecurityException(String message) {
        super(message);
    }
}
