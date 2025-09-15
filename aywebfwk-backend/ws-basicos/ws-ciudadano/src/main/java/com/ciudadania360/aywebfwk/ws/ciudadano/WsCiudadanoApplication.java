package com.ciudadania360.aywebfwk.ws.ciudadano;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Aplicación principal para el servicio web SOAP de ciudadanos
 * Implementa la estructura AyWebFwk del Ayuntamiento de Valencia
 */
@SpringBootApplication
@ComponentScan(basePackages = {
    "com.ciudadania360.aywebfwk.ws.ciudadano",
    "com.ciudadania360.subsistemaciudadano"
})
public class WsCiudadanoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsCiudadanoApplication.class, args);
    }
}

