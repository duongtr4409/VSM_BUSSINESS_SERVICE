package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.ThemeConfigDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ThemeConfig} and its DTO {@link ThemeConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ThemeConfigMapper extends EntityMapper<ThemeConfigDTO, ThemeConfig> {}
