package com.ciudadania360.subsistemacomunicaciones.application.mapper;

import com.ciudadania360.subsistemacomunicaciones.application.dto.notificacion.NotificacionRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.notificacion.NotificacionResponse;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Notificacion;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificacionMapper {

    // Request -> Entity
    Notificacion toEntity(NotificacionRequest request);

    // Entity -> Response
    NotificacionResponse toResponse(Notificacion entity);

    // Update parcial (ignora nulls en el request)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Notificacion existing, NotificacionRequest request);
}
