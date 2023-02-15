package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.MallAndStallDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MallAndStall} and its DTO {@link MallAndStallDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MallAndStallMapper extends EntityMapper<MallAndStallDTO, MallAndStall> {}
