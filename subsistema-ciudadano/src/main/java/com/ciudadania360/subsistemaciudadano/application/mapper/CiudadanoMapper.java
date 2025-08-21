package com.ciudadania360.subsistemaciudadano.application.mapper;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.application.dto.ciudadano.CiudadanoRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.ciudadano.CiudadanoResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CiudadanoMapper {

    // De entidad a DTO de respuesta
    CiudadanoResponse toResponse(Ciudadano entity);

    // De DTO de creación a entidad
    Ciudadano toEntity(CiudadanoRequest request);

    // Actualización parcial de la entidad existente con datos del request
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Ciudadano entity, CiudadanoRequest request);
}
