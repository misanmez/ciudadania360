package com.ciudadania360.aywebfwk.ws.ciudadano;

import com.ciudadania360.subsistemaciudadano.application.service.CiudadanoService;
import com.ciudadania360.shared.application.dto.ciudadano.CiudadanoResponse;
import com.ciudadania360.shared.application.dto.ciudadano.CiudadanoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import java.util.List;
import java.util.UUID;

/**
 * Servicio web SOAP para el subsistema ciudadano
 * Implementa la interfaz AyWebFwk para integración con sistemas externos
 */
@WebService(
    name = "CiudadanoWebService",
    targetNamespace = "http://ciudadania360.com/aywebfwk/ws/ciudadano",
    serviceName = "CiudadanoWebService",
    portName = "CiudadanoWebServicePort"
)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
@Service
@RequiredArgsConstructor
@Slf4j
public class CiudadanoWebService {

    private final CiudadanoService ciudadanoService;

    /**
     * Obtiene la lista de todos los ciudadanos
     * @return Lista de ciudadanos
     */
    @WebMethod(operationName = "listarCiudadanos")
    @WebResult(name = "ciudadano")
    public List<CiudadanoResponse> listarCiudadanos() {
        log.info("SOAP: Listando todos los ciudadanos");
        return ciudadanoService.list();
    }

    /**
     * Obtiene un ciudadano por su ID
     * @param id ID del ciudadano
     * @return Datos del ciudadano
     */
    @WebMethod(operationName = "obtenerCiudadano")
    @WebResult(name = "ciudadano")
    public CiudadanoResponse obtenerCiudadano(
            @WebParam(name = "id") UUID id) {
        log.info("SOAP: Obteniendo ciudadano con ID: {}", id);
        return ciudadanoService.get(id);
    }

    /**
     * Crea un nuevo ciudadano
     * @param request Datos del ciudadano a crear
     * @return Ciudadano creado
     */
    @WebMethod(operationName = "crearCiudadano")
    @WebResult(name = "ciudadano")
    public CiudadanoResponse crearCiudadano(
            @WebParam(name = "ciudadanoRequest") CiudadanoRequest request) {
        log.info("SOAP: Creando ciudadano: {}", request);
        return ciudadanoService.create(request);
    }

    /**
     * Actualiza un ciudadano existente
     * @param id ID del ciudadano
     * @param request Datos actualizados del ciudadano
     * @return Ciudadano actualizado
     */
    @WebMethod(operationName = "actualizarCiudadano")
    @WebResult(name = "ciudadano")
    public CiudadanoResponse actualizarCiudadano(
            @WebParam(name = "id") UUID id,
            @WebParam(name = "ciudadanoRequest") CiudadanoRequest request) {
        log.info("SOAP: Actualizando ciudadano con ID: {}", id);
        return ciudadanoService.update(id, request);
    }

    /**
     * Elimina un ciudadano
     * @param id ID del ciudadano a eliminar
     */
    @WebMethod(operationName = "eliminarCiudadano")
    public void eliminarCiudadano(@WebParam(name = "id") UUID id) {
        log.info("SOAP: Eliminando ciudadano con ID: {}", id);
        ciudadanoService.delete(id);
    }
}
