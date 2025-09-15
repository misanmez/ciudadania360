package com.ciudadania360.aywebfwk.ws.tramitacion;

import com.ciudadania360.subsistematramitacion.application.service.FlujoService;
import com.ciudadania360.shared.application.dto.flujo.FlujoResponse;
import com.ciudadania360.shared.application.dto.flujo.FlujoRequest;
import com.ciudadania360.aywebfwk.ws.tramitacion.mapper.FlujoMapper;
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
 * Servicio web SOAP para el subsistema de tramitación
 * Implementa la interfaz AyWebFwk para integración con sistemas externos
 */
@WebService(
    name = "TramitacionWebService",
    targetNamespace = "http://ciudadania360.com/aywebfwk/ws/tramitacion",
    serviceName = "TramitacionWebService",
    portName = "TramitacionWebServicePort"
)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
@Service
@RequiredArgsConstructor
@Slf4j
public class TramitacionWebService {

    private final FlujoService flujoService;
    private final FlujoMapper flujoMapper;

    /**
     * Obtiene la lista de todos los flujos de tramitación
     * @return Lista de flujos
     */
    @WebMethod(operationName = "listarFlujos")
    @WebResult(name = "flujo")
    public List<FlujoResponse> listarFlujos() {
        log.info("SOAP: Listando todos los flujos de tramitación");
        return flujoService.list().stream()
                .map(flujoMapper::toSharedResponse)
                .toList();
    }

    /**
     * Obtiene un flujo por su ID
     * @param id ID del flujo
     * @return Datos del flujo
     */
    @WebMethod(operationName = "obtenerFlujo")
    @WebResult(name = "flujo")
    public FlujoResponse obtenerFlujo(
            @WebParam(name = "id") UUID id) {
        log.info("SOAP: Obteniendo flujo con ID: {}", id);
        return flujoMapper.toSharedResponse(flujoService.get(id));
    }

    /**
     * Crea un nuevo flujo
     * @param request Datos del flujo a crear
     * @return Flujo creado
     */
    @WebMethod(operationName = "crearFlujo")
    @WebResult(name = "flujo")
    public FlujoResponse crearFlujo(
            @WebParam(name = "flujoRequest") FlujoRequest request) {
        log.info("SOAP: Creando flujo: {}", request);
        return flujoMapper.toSharedResponse(flujoService.create(flujoMapper.toSubsistemaRequest(request)));
    }

    /**
     * Actualiza un flujo existente
     * @param id ID del flujo
     * @param request Datos actualizados del flujo
     * @return Flujo actualizado
     */
    @WebMethod(operationName = "actualizarFlujo")
    @WebResult(name = "flujo")
    public FlujoResponse actualizarFlujo(
            @WebParam(name = "id") UUID id,
            @WebParam(name = "flujoRequest") FlujoRequest request) {
        log.info("SOAP: Actualizando flujo con ID: {}", id);
        return flujoMapper.toSharedResponse(flujoService.update(id, flujoMapper.toSubsistemaRequest(request)));
    }

    /**
     * Elimina un flujo
     * @param id ID del flujo a eliminar
     */
    @WebMethod(operationName = "eliminarFlujo")
    public void eliminarFlujo(@WebParam(name = "id") UUID id) {
        log.info("SOAP: Eliminando flujo con ID: {}", id);
        flujoService.delete(id);
    }
}
