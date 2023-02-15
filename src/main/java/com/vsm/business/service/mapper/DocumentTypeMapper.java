package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.DocumentTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentType} and its DTO {@link DocumentTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class })
public interface DocumentTypeMapper extends EntityMapper<DocumentTypeDTO, DocumentType> {
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    DocumentTypeDTO toDto(DocumentType s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DocumentTypeDTO toDtoId(DocumentType documentType);
}
