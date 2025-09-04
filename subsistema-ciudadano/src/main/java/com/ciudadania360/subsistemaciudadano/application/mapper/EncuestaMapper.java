package com.ciudadania360.subsistemaciudadano.application.mapper;

import com.ciudadania360.subsistemaciudadano.domain.entity.Encuesta;
import com.ciudadania360.subsistemaciudadano.application.dto.encuesta.EncuestaRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.encuesta.EncuestaResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EncuestaMapper {

    EncuestaResponse toResponse(Encuesta entity);

    Encuesta toEntity(EncuestaRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Encuesta entity, EncuestaRequest request);
}
