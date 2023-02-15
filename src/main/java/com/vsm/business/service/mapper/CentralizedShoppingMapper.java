package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.CentralizedShoppingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CentralizedShopping} and its DTO {@link CentralizedShoppingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CentralizedShoppingMapper extends EntityMapper<CentralizedShoppingDTO, CentralizedShopping> {}
