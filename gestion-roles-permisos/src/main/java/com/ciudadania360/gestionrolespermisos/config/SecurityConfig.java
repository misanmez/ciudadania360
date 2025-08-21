package com.ciudadania360.gestionrolespermisos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desactivar CSRF para simplificar tests y APIs REST
                .csrf(csrf -> csrf.disable())

                // Configuración de autorización
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos (docs y health)
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/actuator/health").permitAll()
                        // Resto requiere autenticación
                        .anyRequest().authenticated()
                )

                // Configuración OAuth2 Resource Server JWT
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }
}
