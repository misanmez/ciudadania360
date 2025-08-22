package com.ciudadania360.subsistemavideoconferencia.application.mapper;

import com.ciudadania360.subsistemavideoconferencia.application.dto.dispositivo.DispositivoRequest;
import com.ciudadania360.subsistemavideoconferencia.application.dto.dispositivo.DispositivoResponse;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.Dispositivo;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DispositivoMapper {

    // Request -> Entity
    Dispositivo toEntity(DispositivoRequest request);

    // Entity -> Response
    DispositivoResponse toResponse(Dispositivo entity);

    // Update parcial (ignora nulls en el request)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Dispositivo existing, DispositivoRequest request);
}
