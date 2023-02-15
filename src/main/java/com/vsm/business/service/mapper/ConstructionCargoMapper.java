package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.ConstructionCargoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConstructionCargo} and its DTO {@link ConstructionCargoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConstructionCargoMapper extends EntityMapper<ConstructionCargoDTO, ConstructionCargo> {}
