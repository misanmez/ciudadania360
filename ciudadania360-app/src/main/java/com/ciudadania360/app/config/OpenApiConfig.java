package com.ciudadania360.app.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi ciudadanoApi() {
        return GroupedOpenApi.builder()
                .group("01-Ciudadano")
                .packagesToScan("com.ciudadania360.subsistemaciudadano")
                .build();
    }

    @Bean
    public GroupedOpenApi tramitacionApi() {
        return GroupedOpenApi.builder()
                .group("02-Tramitacion")
                .packagesToScan("com.ciudadania360.subsistematramitacion")
                .build();
    }

    @Bean
    public GroupedOpenApi comunicacionesApi() {
        return GroupedOpenApi.builder()
                .group("03-Comunicaciones")
                .packagesToScan("com.ciudadania360.subsistemacomunicaciones")
                .build();
    }

    @Bean
    public GroupedOpenApi videoconferenciaApi() {
        return GroupedOpenApi.builder()
                .group("04-Videoconferencia")
                .packagesToScan("com.ciudadania360.subsistemavideoconferencia")
                .build();
    }

    @Bean
    public GroupedOpenApi informacionApi() {
        return GroupedOpenApi.builder()
                .group("05-Informacion")
                .packagesToScan("com.ciudadania360.subsistemainformacion")
                .build();
    }

    @Bean
    public GroupedOpenApi rolesPermisosApi() {
        return GroupedOpenApi.builder()
                .group("06-RolesPermisos")
                .packagesToScan("com.ciudadania360.gestionrolespermisos")
                .build();
    }

    @Bean
    public GroupedOpenApi iaApi() {
        return GroupedOpenApi.builder()
                .group("07-IA")
                .packagesToScan("com.ciudadania360.subsistemaia")
                .build();
    }

}
