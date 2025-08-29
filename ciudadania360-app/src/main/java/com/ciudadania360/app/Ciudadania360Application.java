package com.ciudadania360.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        // raíz común
        "com.ciudadania360"
        // si algún módulo usa otro paquete raíz, añádelo aquí
})
public class Ciudadania360Application {
    public static void main(String[] args) {
        SpringApplication.run(Ciudadania360Application.class, args);
    }
}
