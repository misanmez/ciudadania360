package com.ciudadania360.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.ciudadania360")
@EnableJpaRepositories(basePackages = {
        "com.ciudadania360.shared.domain.repository",
        "com.ciudadania360.subsistemaciudadano.domain.repository",
        "com.ciudadania360.subsistemacomunicaciones.domain.repository",
        "com.ciudadania360.subsistemaia.domain.repository",
        "com.ciudadania360.subsistemainformacion.domain.repository",
        "com.ciudadania360.subsistematramitacion.domain.repository",
        "com.ciudadania360.subsistemavideoconferencia.domain.repository",
        "com.ciudadania360.gestionrolespermisos.domain.repository"
})
@EntityScan(basePackages = {
        "com.ciudadania360.shared.domain.entity",
        "com.ciudadania360.subsistemaciudadano.domain.entity",
        "com.ciudadania360.subsistemacomunicaciones.domain.entity",
        "com.ciudadania360.subsistemaia.domain.entity",
        "com.ciudadania360.subsistemainformacion.domain.entity",
        "com.ciudadania360.subsistematramitacion.domain.entity",
        "com.ciudadania360.subsistemavideoconferencia.domain.entity",
        "com.ciudadania360.gestionrolespermisos.domain.entity"
})
public class Ciudadania360Application {
    public static void main(String[] args) {
        SpringApplication.run(Ciudadania360Application.class, args);
    }
}
