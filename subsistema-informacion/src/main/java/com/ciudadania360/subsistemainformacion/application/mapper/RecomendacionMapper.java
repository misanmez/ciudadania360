package com.ciudadania360.subsistemainformacion.application.mapper;

import com.ciudadania360.subsistemainformacion.domain.entity.Recomendacion;
import com.ciudadania360.subsistemainformacion.application.dto.recomendacion.RecomendacionRequest;
import com.ciudadania360.subsistemainformacion.application.dto.recomendacion.RecomendacionResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecomendacionMapper {

    Recomendacion toEntity(RecomendacionRequest request);

    RecomendacionResponse toResponse(Recomendacion entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Recomendacion existing, RecomendacionRequest request);
}
