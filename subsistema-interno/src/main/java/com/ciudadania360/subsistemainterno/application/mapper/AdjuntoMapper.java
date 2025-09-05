package com.ciudadania360.subsistemainterno.application.mapper;

import com.ciudadania360.subsistemainterno.domain.entity.Adjunto;
import com.ciudadania360.subsistemainterno.application.dto.adjunto.*;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AdjuntoMapper {

    Adjunto toEntity(AdjuntoRequest request);

    AdjuntoResponse toResponse(Adjunto entity);

    List<AdjuntoResponse> toResponseList(List<Adjunto> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Adjunto entity, AdjuntoRequest request);

}
