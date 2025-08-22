package com.ciudadania360.subsistemainformacion.application.mapper;

import com.ciudadania360.subsistemainformacion.domain.entity.Instruccion;
import com.ciudadania360.subsistemainformacion.application.dto.instruccion.InstruccionRequest;
import com.ciudadania360.subsistemainformacion.application.dto.instruccion.InstruccionResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InstruccionMapper {

    Instruccion toEntity(InstruccionRequest request);

    InstruccionResponse toResponse(Instruccion entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Instruccion existing, InstruccionRequest request);
}
