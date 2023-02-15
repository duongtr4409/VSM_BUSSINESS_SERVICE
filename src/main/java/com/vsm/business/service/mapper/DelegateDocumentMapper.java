package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.DelegateDocumentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DelegateDocument} and its DTO {@link DelegateDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DelegateDocumentMapper extends EntityMapper<DelegateDocumentDTO, DelegateDocument> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DelegateDocumentDTO toDtoId(DelegateDocument delegateDocument);
}
