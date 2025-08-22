package com.ciudadania360.subsistemainformacion.application.mapper;

import com.ciudadania360.subsistemainformacion.domain.entity.Dataset;
import com.ciudadania360.subsistemainformacion.application.dto.dataset.DatasetRequest;
import com.ciudadania360.subsistemainformacion.application.dto.dataset.DatasetResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DatasetMapper {

    Dataset toEntity(DatasetRequest request);

    DatasetResponse toResponse(Dataset entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Dataset existing, DatasetRequest request);
}
