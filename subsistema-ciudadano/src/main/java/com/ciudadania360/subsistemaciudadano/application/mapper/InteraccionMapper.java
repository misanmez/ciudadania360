package com.ciudadania360.subsistemaciudadano.application.mapper;

import com.ciudadania360.shared.application.dto.ciudadano.CiudadanoResponse;
import com.ciudadania360.shared.application.dto.interaccion.InteraccionRequest;
import com.ciudadania360.shared.application.dto.interaccion.InteraccionResponse;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.entity.Interaccion;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.shared.domain.entity.Empleado;
import com.ciudadania360.shared.application.dto.solicitud.SolicitudResponse;
import com.ciudadania360.shared.application.dto.empleado.EmpleadoResponse;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InteraccionMapper {

    // Request -> Entity
    @Mapping(target = "solicitud", source = "solicitudId", qualifiedByName = "mapSolicitud")
    @Mapping(target = "ciudadano", source = "ciudadanoId", qualifiedByName = "mapCiudadano")
    @Mapping(target = "empleadoResponsable", source = "empleadoResponsableId", qualifiedByName = "mapEmpleado")
    Interaccion toEntity(InteraccionRequest request);

    // Entity -> Response
    @Mapping(target = "solicitud", source = "solicitud", qualifiedByName = "mapSolicitudResponse")
    @Mapping(target = "ciudadano", source = "ciudadano", qualifiedByName = "mapCiudadanoResponse")
    @Mapping(target = "empleadoResponsable", source = "empleadoResponsable", qualifiedByName = "mapEmpleadoResponse")
    InteraccionResponse toResponse(Interaccion entity);

    // Update parcial (ignora nulls)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "solicitud", source = "solicitudId", qualifiedByName = "mapSolicitud")
    @Mapping(target = "ciudadano", source = "ciudadanoId", qualifiedByName = "mapCiudadano")
    @Mapping(target = "empleadoResponsable", source = "empleadoResponsableId", qualifiedByName = "mapEmpleado")
    void updateEntity(@MappingTarget Interaccion existing, InteraccionRequest request);

    /* ==== Helpers UUID -> Entidades ==== */
    @Named("mapSolicitud")
    default Solicitud mapSolicitud(UUID id) {
        if (id == null) return null;
        Solicitud s = new Solicitud();
        s.setId(id);
        return s;
    }

    @Named("mapCiudadano")
    default Ciudadano mapCiudadano(UUID id) {
        if (id == null) return null;
        Ciudadano c = new Ciudadano();
        c.setId(id);
        return c;
    }

    @Named("mapEmpleado")
    default Empleado mapEmpleado(UUID id) {
        if (id == null) return null;
        Empleado e = new Empleado();
        e.setId(id);
        return e;
    }

    /* ==== Helpers Entidad -> DTO ==== */
    @Named("mapSolicitudResponse")
    default SolicitudResponse toSolicitudResponse(Solicitud s) {
        if (s == null) return null;
        return SolicitudResponse.builder()
                .id(s.getId())
                .titulo(s.getTitulo())
                .descripcion(s.getDescripcion())
                .tipo(s.getTipo())
                .estado(s.getEstado())
                .prioridad(s.getPrioridad())
                .build();
    }

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
}
