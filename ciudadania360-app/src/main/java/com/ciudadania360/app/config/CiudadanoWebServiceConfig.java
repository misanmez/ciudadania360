package com.ciudadania360.app.config;

import com.ciudadania360.aywebfwk.ws.ciudadano.CiudadanoWebService;
import com.ciudadania360.aywebfwk.ws.tramitacion.TramitacionWebService;
import com.ciudadania360.aywebfwk.ws.comunicaciones.ComunicacionesWebService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.xml.ws.Endpoint;

/**
 * Configuración de los servicios web SOAP en ciudadania360-app
 * Expone los servicios SOAP junto con las APIs REST en el puerto 8080
 */
@Configuration
public class CiudadanoWebServiceConfig {

    @Autowired
    private Bus bus;

    @Autowired
    private CiudadanoWebService ciudadanoWebService;

    @Autowired
    private TramitacionWebService tramitacionWebService;

    @Autowired
    private ComunicacionesWebService comunicacionesWebService;

    /**
     * Expone el servicio web SOAP de ciudadanos
     * @return Endpoint del servicio web
     */
    @Bean
    public Endpoint ciudadanoWebServiceEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, ciudadanoWebService);
        endpoint.publish("/ciudadano"); // 🔧 corregido (sin /services)
        return endpoint;
    }

    /**
     * Expone el servicio web SOAP de tramitación
     * @return Endpoint del servicio web
     */
    @Bean
    public Endpoint tramitacionWebServiceEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, tramitacionWebService);
        endpoint.publish("/tramitacion"); // 🔧 corregido (sin /services)
        return endpoint;
    }

    /**
     * Expone el servicio web SOAP de comunicaciones
     * @return Endpoint del servicio web
     */
    @Bean
    public Endpoint comunicacionesWebServiceEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, comunicacionesWebService);
        endpoint.publish("/comunicaciones"); // 🔧 corregido (sin /services)
        return endpoint;
    }
}
