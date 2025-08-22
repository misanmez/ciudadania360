package com.ciudadania360.subsistemainformacion.application.mapper;

import com.ciudadania360.subsistemainformacion.domain.entity.Duda;
import com.ciudadania360.subsistemainformacion.application.dto.duda.DudaRequest;
import com.ciudadania360.subsistemainformacion.application.dto.duda.DudaResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DudaMapper {

    Duda toEntity(DudaRequest request);

    DudaResponse toResponse(Duda entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Duda existing, DudaRequest request);
}
