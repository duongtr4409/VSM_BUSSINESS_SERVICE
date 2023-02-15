package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.CurrencyUnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CurrencyUnit} and its DTO {@link CurrencyUnitDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class })
public interface CurrencyUnitMapper extends EntityMapper<CurrencyUnitDTO, CurrencyUnit> {
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    CurrencyUnitDTO toDto(CurrencyUnit s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CurrencyUnitDTO toDtoId(CurrencyUnit currencyUnit);
}
