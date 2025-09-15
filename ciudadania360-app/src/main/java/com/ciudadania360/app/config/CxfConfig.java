package com.ciudadania360.app.config;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración CXF para servicios web SOAP en ciudadania360-app
 * Permite que los servicios web AyWebFwk funcionen junto con las APIs REST
 */
@Configuration
public class CxfConfig {

    /**
     * Configura el bus de CXF para servicios web SOAP
     * @return Bus de CXF configurado
     */
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    /**
     * Registra el servlet de CXF para manejar las peticiones SOAP
     * @return ServletRegistrationBean para CXF
     */
    @Bean
    public ServletRegistrationBean<CXFServlet> cxfServlet() {
        ServletRegistrationBean<CXFServlet> registration = new ServletRegistrationBean<>(new CXFServlet(), "/services/*");
        registration.setName("cxfServlet");
        registration.setLoadOnStartup(1);
        return registration;
    }
}
