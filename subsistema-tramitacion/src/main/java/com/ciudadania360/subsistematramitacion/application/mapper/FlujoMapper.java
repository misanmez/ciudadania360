// FlujoMapper.java
package com.ciudadania360.subsistematramitacion.application.mapper;

import com.ciudadania360.subsistematramitacion.domain.entity.Flujo;
import com.ciudadania360.subsistematramitacion.application.dto.flujo.FlujoRequest;
import com.ciudadania360.subsistematramitacion.application.dto.flujo.FlujoResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FlujoMapper {

    // Request -> Entity
    Flujo toEntity(FlujoRequest request);

    // Entity -> Response
    FlujoResponse toResponse(Flujo entity);

    // Update parcial (ignora nulls en el request)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Flujo existing, FlujoRequest request);
}
