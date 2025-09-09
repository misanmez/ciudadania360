package com.ciudadania360.subsistemaciudadano.application.mapper;

import com.ciudadania360.shared.application.dto.ciudadano.CiudadanoResponse;
import com.ciudadania360.shared.application.dto.clasificacion.ClasificacionResponse;
import com.ciudadania360.shared.application.dto.solicitud.SolicitudRequest;
import com.ciudadania360.shared.application.dto.solicitud.SolicitudResponse;
import com.ciudadania360.shared.application.dto.interaccion.InteraccionResponse;
import com.ciudadania360.shared.application.dto.empleado.EmpleadoResponse;
import com.ciudadania360.subsistemaciudadano.domain.entity.*;
import com.ciudadania360.shared.domain.entity.Empleado;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SolicitudMapper {

    // Request -> Entity
    @Mapping(target = "ciudadano", source = "ciudadanoId", qualifiedByName = "mapCiudadano")
    @Mapping(target = "clasificacion", source = "clasificacionId", qualifiedByName = "mapClasificacion")
    @Mapping(target = "ubicacion", source = "ubicacionId", qualifiedByName = "mapUbicacion")
    @Mapping(target = "agenteAsignado", source = "agenteAsignadoId", qualifiedByName = "mapEmpleado")
    Solicitud toEntity(SolicitudRequest request);

    // Entity -> Response
    @Mapping(target = "ciudadano", source = "ciudadano", qualifiedByName = "mapCiudadanoResponse")
    @Mapping(target = "clasificacion", source = "clasificacion", qualifiedByName = "mapClasificacionResponse")
    @Mapping(target = "agenteAsignado", source = "agenteAsignado", qualifiedByName = "mapEmpleadoResponse")
    @Mapping(target = "interacciones", source = "interacciones", qualifiedByName = "mapInteracciones")
    SolicitudResponse toResponse(Solicitud entity);

    // Update parcial (ignora nulls en el request)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "ciudadano", source = "ciudadanoId", qualifiedByName = "mapCiudadano")
    @Mapping(target = "clasificacion", source = "clasificacionId", qualifiedByName = "mapClasificacion")
    @Mapping(target = "ubicacion", source = "ubicacionId", qualifiedByName = "mapUbicacion")
    @Mapping(target = "agenteAsignado", source = "agenteAsignadoId", qualifiedByName = "mapEmpleado")
    void updateEntity(@MappingTarget Solicitud existing, SolicitudRequest request);

    /* ===== Helpers UUID -> Entidad ===== */
    @Named("mapCiudadano")
    default Ciudadano toCiudadano(UUID id) {
        if (id == null) return null;
        Ciudadano c = new Ciudadano();
        c.setId(id);
        return c;
    }

    @Named("mapClasificacion")
    default Clasificacion toClasificacion(UUID id) {
        if (id == null) return null;
        Clasificacion cl = new Clasificacion();
        cl.setId(id);
        return cl;
    }

    @Named("mapUbicacion")
    default Ubicacion toUbicacion(UUID id) {
        if (id == null) return null;
        Ubicacion u = new Ubicacion();
        u.setId(id);
        return u;
    }

    @Named("mapEmpleado")
    default Empleado toEmpleado(UUID id) {
        if (id == null) return null;
        Empleado e = new Empleado();
        e.setId(id);
        return e;
    }

    /* ===== Helpers Entidad -> DTO ===== */
    @Named("mapCiudadanoResponse")
    default CiudadanoResponse toCiudadanoResponse(Ciudadano c) {
        if (c == null) return null;
        return CiudadanoResponse.builder()
                .id(c.getId())
                .nombre(c.getNombre())
                .apellidos(c.getApellidos())
                .email(c.getEmail())
                .telefono(c.getTelefono())
                .build();
    }

    @Named("mapClasificacionResponse")
    default ClasificacionResponse toClasificacionResponse(Clasificacion cl) {
        if (cl == null) return null;
        return ClasificacionResponse.builder()
                .id(cl.getId())
                .nombre(cl.getNombre())
                .descripcion(cl.getDescripcion())
                .build();
    }

    @Named("mapEmpleadoResponse")
    default EmpleadoResponse toEmpleadoResponse(Empleado e) {
        if (e == null) return null;
        return EmpleadoResponse.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .apellidos(e.getApellidos())
                .email(e.getEmail())
                .telefono(e.getTelefono())
                .rol(e.getRol())
                .build();
    }

    @Named("mapInteracciones")
    default List<InteraccionResponse> toInteraccionResponseList(List<Interaccion> interacciones) {
        if (interacciones == null) return null;

        return interacciones.stream()
                .map(i -> InteraccionResponse.builder()
                        .id(i.getId())
                        .solicitud(i.getSolicitud() != null ? toResponse(i.getSolicitud()) : null)
                        .ciudadano(i.getCiudadano() != null ? toCiudadanoResponse(i.getCiudadano()) : null)
                        .empleadoResponsable(i.getEmpleadoResponsable() != null ? toEmpleadoResponse(i.getEmpleadoResponsable()) : null)
                        .canal(i.getCanal())
                        .fecha(i.getFecha())
                        .agente(i.getAgente())
                        .mensaje(i.getMensaje())
                        .adjuntoUri(i.getAdjuntoUri())
                        .visibilidad(i.getVisibilidad())
                        .version(i.getVersion())
                        .build())
                .collect(Collectors.toList());
    }
}
