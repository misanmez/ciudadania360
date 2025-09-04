package com.ciudadania360.subsistemaciudadano.application.mapper;

import com.ciudadania360.subsistemaciudadano.domain.entity.Notificacion;
import com.ciudadania360.subsistemaciudadano.application.dto.notificacion.NotificacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.notificacion.NotificacionResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificacionMapper {

    NotificacionResponse toResponse(Notificacion entity);

    Notificacion toEntity(NotificacionRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Notificacion entity, NotificacionRequest request);
}
