package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.BusinessPartnerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BusinessPartner} and its DTO {@link BusinessPartnerDTO}.
 */
@Mapper(componentModel = "spring", uses = { BusinessPartnerTypeMapper.class, UserInfoMapper.class })
public interface BusinessPartnerMapper extends EntityMapper<BusinessPartnerDTO, BusinessPartner> {
    @Mapping(target = "businessPartnerType", source = "businessPartnerType", qualifiedByName = "id")
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    BusinessPartnerDTO toDto(BusinessPartner s);
}
