package com.ciudadania360.subsistemaciudadano.application.mapper;

import com.ciudadania360.subsistemaciudadano.application.dto.direccion.DireccionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.direccion.DireccionResponse;
import com.ciudadania360.subsistemaciudadano.domain.entity.Direccion;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DireccionMapper {

    Direccion toEntity(DireccionRequest request);

    DireccionResponse toResponse(Direccion entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Direccion existing, DireccionRequest request);
}
