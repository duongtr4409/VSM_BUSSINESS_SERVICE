package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.DelegateInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DelegateInfo} and its DTO {@link DelegateInfoDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = { UserInfoMapper.class, DelegateTypeMapper.class, RequestMapper.class, DelegateDocumentMapper.class }
)
public interface DelegateInfoMapper extends EntityMapper<DelegateInfoDTO, DelegateInfo> {
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    @Mapping(target = "delegater", source = "delegater", qualifiedByName = "id")
    @Mapping(target = "delegated", source = "delegated", qualifiedByName = "id")
    @Mapping(target = "delegateType", source = "delegateType", qualifiedByName = "id")
    @Mapping(target = "docDelegate", source = "docDelegate", qualifiedByName = "id")
    @Mapping(target = "delegateDocument", source = "delegateDocument", qualifiedByName = "id")
    DelegateInfoDTO toDto(DelegateInfo s);
}
