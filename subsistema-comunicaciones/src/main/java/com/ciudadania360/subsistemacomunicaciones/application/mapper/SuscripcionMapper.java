package com.ciudadania360.subsistemacomunicaciones.application.mapper;

import com.ciudadania360.subsistemacomunicaciones.application.dto.suscripcion.SuscripcionRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.suscripcion.SuscripcionResponse;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Suscripcion;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SuscripcionMapper {

    // Request -> Entity
    Suscripcion toEntity(SuscripcionRequest request);

    // Entity -> Response
    SuscripcionResponse toResponse(Suscripcion entity);

    // Update parcial (ignora nulls en el request)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Suscripcion existing, SuscripcionRequest request);
}
