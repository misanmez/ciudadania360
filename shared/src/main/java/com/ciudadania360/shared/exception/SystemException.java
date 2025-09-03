package com.ciudadania360.shared.exception;

import lombok.Getter;

// Para fallos técnicos no esperados (HTTP 500)
// Se lanza para fallos técnicos: caída de BBDD, timeout de servicios externos, errores no controlados.
// Se mantiene, aunque en algunos casos RuntimeException podría bastar, pero tenerlo explícito ayuda al logging y TDD.
@Getter
public class SystemException extends RuntimeException {
    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(String message) {
        super(message);
    }
}
