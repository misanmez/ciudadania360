package com.ciudadania360.subsistemaciudadano.application.mapper;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ubicacion;
import com.ciudadania360.subsistemaciudadano.application.dto.UbicacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.UbicacionResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UbicacionMapper {

    Ubicacion toEntity(UbicacionRequest request);

    UbicacionResponse toResponse(Ubicacion entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Ubicacion existing, UbicacionRequest request);
}