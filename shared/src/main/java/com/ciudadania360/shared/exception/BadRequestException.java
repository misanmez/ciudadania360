package com.ciudadania360.shared.exception;

import lombok.Getter;

// Para errores de validación o entradas inválidas (HTTP 400)
// Se usa en controladores REST cuando los parámetros no cumplen la validación.
// Ejemplo típico: un DTO recibido con campos obligatorios vacíos o formato inválido.
// Se mantiene, se debe usar en todos los endpoints con validaciones de entrada.

@Getter
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
