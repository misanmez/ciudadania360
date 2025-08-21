package com.ciudadania360.subsistemaciudadano.application.mapper;

import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.ciudadania360.subsistemaciudadano.application.dto.ClasificacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.ClasificacionResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClasificacionMapper {

    Clasificacion toEntity(ClasificacionRequest request);

    ClasificacionResponse toResponse(Clasificacion entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Clasificacion existing, ClasificacionRequest request);
}
