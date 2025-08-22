// DocumentoMapper.java
package com.ciudadania360.subsistematramitacion.application.mapper;

import com.ciudadania360.subsistematramitacion.domain.entity.Documento;
import com.ciudadania360.subsistematramitacion.application.dto.documento.DocumentoRequest;
import com.ciudadania360.subsistematramitacion.application.dto.documento.DocumentoResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocumentoMapper {

    // Request -> Entity
    Documento toEntity(DocumentoRequest request);

    // Entity -> Response
    DocumentoResponse toResponse(Documento entity);

    // Update parcial (ignora nulls en el request)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Documento existing, DocumentoRequest request);
}
