package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.SecurityLevelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SecurityLevel} and its DTO {@link SecurityLevelDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class })
public interface SecurityLevelMapper extends EntityMapper<SecurityLevelDTO, SecurityLevel> {
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    SecurityLevelDTO toDto(SecurityLevel s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SecurityLevelDTO toDtoId(SecurityLevel securityLevel);
}
