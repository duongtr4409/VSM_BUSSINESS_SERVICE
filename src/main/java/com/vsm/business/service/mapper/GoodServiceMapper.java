package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.GoodServiceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GoodService} and its DTO {@link GoodServiceDTO}.
 */
@Mapper(componentModel = "spring", uses = { CurrencyUnitMapper.class, GoodServiceTypeMapper.class, UserInfoMapper.class })
public interface GoodServiceMapper extends EntityMapper<GoodServiceDTO, GoodService> {
    @Mapping(target = "currencyUnit", source = "currencyUnit", qualifiedByName = "id")
    @Mapping(target = "goodServiceType", source = "goodServiceType", qualifiedByName = "id")
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    GoodServiceDTO toDto(GoodService s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GoodServiceDTO toDtoId(GoodService goodService);
}
