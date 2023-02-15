package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.PriceInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PriceInfo} and its DTO {@link PriceInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = { VendorMapper.class, GoodServiceMapper.class, CurrencyUnitMapper.class, UserInfoMapper.class })
public interface PriceInfoMapper extends EntityMapper<PriceInfoDTO, PriceInfo> {
    @Mapping(target = "vendor", source = "vendor", qualifiedByName = "id")
    @Mapping(target = "goodService", source = "goodService", qualifiedByName = "id")
    @Mapping(target = "currencyUnit", source = "currencyUnit", qualifiedByName = "id")
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    PriceInfoDTO toDto(PriceInfo s);
}
