package com.ciudadania360.subsistemainterno.application.mapper;

import com.ciudadania360.subsistemainterno.domain.entity.Adjunto;
import com.ciudadania360.subsistemainterno.application.dto.adjunto.*;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AdjuntoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parteTrabajo", ignore = true)
    @Mapping(target = "version", ignore = true)
    Adjunto toEntity(AdjuntoRequest request);

    @Mapping(target = "parteTrabajoId", ignore = true)
    AdjuntoResponse toResponse(Adjunto entity);

    List<AdjuntoResponse> toResponseList(List<Adjunto> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parteTrabajo", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(@MappingTarget Adjunto entity, AdjuntoRequest request);

}
