package com.ciudadania360.aywebfwk.ws.comunicaciones;

import com.ciudadania360.subsistemacomunicaciones.application.service.ComunicacionService;
import com.ciudadania360.shared.application.dto.comunicacion.ComunicacionResponse;
import com.ciudadania360.shared.application.dto.comunicacion.ComunicacionRequest;
import com.ciudadania360.aywebfwk.ws.comunicaciones.mapper.ComunicacionMapper;
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
 * Servicio web SOAP para el subsistema de comunicaciones
 * Implementa la interfaz AyWebFwk para integración con sistemas externos
 */
@WebService(
    name = "ComunicacionesWebService",
    targetNamespace = "http://ciudadania360.com/aywebfwk/ws/comunicaciones",
    serviceName = "ComunicacionesWebService",
    portName = "ComunicacionesWebServicePort"
)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
@Service
@RequiredArgsConstructor
@Slf4j
public class ComunicacionesWebService {

    private final ComunicacionService comunicacionService;
    private final ComunicacionMapper comunicacionMapper;

    /**
     * Obtiene la lista de todas las comunicaciones
     * @return Lista de comunicaciones
     */
    @WebMethod(operationName = "listarComunicaciones")
    @WebResult(name = "comunicacion")
    public List<ComunicacionResponse> listarComunicaciones() {
        log.info("SOAP: Listando todas las comunicaciones");
        return comunicacionService.list().stream()
                .map(comunicacionMapper::toSharedResponse)
                .toList();
    }

    /**
     * Obtiene una comunicación por su ID
     * @param id ID de la comunicación
     * @return Datos de la comunicación
     */
    @WebMethod(operationName = "obtenerComunicacion")
    @WebResult(name = "comunicacion")
    public ComunicacionResponse obtenerComunicacion(
            @WebParam(name = "id") UUID id) {
        log.info("SOAP: Obteniendo comunicación con ID: {}", id);
        return comunicacionMapper.toSharedResponse(comunicacionService.get(id));
    }

    /**
     * Crea una nueva comunicación
     * @param request Datos de la comunicación a crear
     * @return Comunicación creada
     */
    @WebMethod(operationName = "crearComunicacion")
    @WebResult(name = "comunicacion")
    public ComunicacionResponse crearComunicacion(
            @WebParam(name = "comunicacionRequest") ComunicacionRequest request) {
        log.info("SOAP: Creando comunicación: {}", request);
        return comunicacionMapper.toSharedResponse(comunicacionService.create(comunicacionMapper.toSubsistemaRequest(request)));
    }

    /**
     * Actualiza una comunicación existente
     * @param id ID de la comunicación
     * @param request Datos actualizados de la comunicación
     * @return Comunicación actualizada
     */
    @WebMethod(operationName = "actualizarComunicacion")
    @WebResult(name = "comunicacion")
    public ComunicacionResponse actualizarComunicacion(
            @WebParam(name = "id") UUID id,
            @WebParam(name = "comunicacionRequest") ComunicacionRequest request) {
        log.info("SOAP: Actualizando comunicación con ID: {}", id);
        return comunicacionMapper.toSharedResponse(comunicacionService.update(id, comunicacionMapper.toSubsistemaRequest(request)));
    }

    /**
     * Elimina una comunicación
     * @param id ID de la comunicación a eliminar
     */
    @WebMethod(operationName = "eliminarComunicacion")
    public void eliminarComunicacion(@WebParam(name = "id") UUID id) {
        log.info("SOAP: Eliminando comunicación con ID: {}", id);
        comunicacionService.delete(id);
    }

}
