package com.ciudadania360.subsistemaciudadano;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ciudadania360.subsistemaciudadano")
public class SubsistemaCiudadanoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SubsistemaCiudadanoApplication.class, args);
    }
}
