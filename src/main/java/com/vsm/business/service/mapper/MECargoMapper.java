package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.MECargoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MECargo} and its DTO {@link MECargoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MECargoMapper extends EntityMapper<MECargoDTO, MECargo> {}
