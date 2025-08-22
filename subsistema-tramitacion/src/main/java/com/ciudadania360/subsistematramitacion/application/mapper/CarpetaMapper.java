// CarpetaMapper.java
package com.ciudadania360.subsistematramitacion.application.mapper;

import com.ciudadania360.subsistematramitacion.domain.entity.Carpeta;
import com.ciudadania360.subsistematramitacion.application.dto.carpeta.CarpetaRequest;
import com.ciudadania360.subsistematramitacion.application.dto.carpeta.CarpetaResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarpetaMapper {

    // Request -> Entity
    Carpeta toEntity(CarpetaRequest request);

    // Entity -> Response
    CarpetaResponse toResponse(Carpeta entity);

    // Update parcial (ignora nulls en el request)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Carpeta existing, CarpetaRequest request);
}
