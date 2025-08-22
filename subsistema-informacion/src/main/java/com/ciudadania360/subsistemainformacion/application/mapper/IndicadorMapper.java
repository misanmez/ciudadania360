package com.ciudadania360.subsistemainformacion.application.mapper;

import com.ciudadania360.subsistemainformacion.domain.entity.Indicador;
import com.ciudadania360.subsistemainformacion.application.dto.indicador.IndicadorRequest;
import com.ciudadania360.subsistemainformacion.application.dto.indicador.IndicadorResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IndicadorMapper {

    Indicador toEntity(IndicadorRequest request);

    IndicadorResponse toResponse(Indicador entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Indicador existing, IndicadorRequest request);
}
