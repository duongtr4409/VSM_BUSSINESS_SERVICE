package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.PatternDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pattern} and its DTO {@link PatternDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PatternMapper extends EntityMapper<PatternDTO, Pattern> {}
