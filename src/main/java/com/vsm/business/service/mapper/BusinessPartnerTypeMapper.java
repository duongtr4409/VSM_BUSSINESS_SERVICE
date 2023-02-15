package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.BusinessPartnerTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BusinessPartnerType} and its DTO {@link BusinessPartnerTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class })
public interface BusinessPartnerTypeMapper extends EntityMapper<BusinessPartnerTypeDTO, BusinessPartnerType> {
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    BusinessPartnerTypeDTO toDto(BusinessPartnerType s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BusinessPartnerTypeDTO toDtoId(BusinessPartnerType businessPartnerType);
}
