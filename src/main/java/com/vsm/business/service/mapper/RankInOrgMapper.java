package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.RankInOrgDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RankInOrg} and its DTO {@link RankInOrgDTO}.
 */
@Mapper(componentModel = "spring", uses = { RankMapper.class, OrganizationMapper.class })
public interface RankInOrgMapper extends EntityMapper<RankInOrgDTO, RankInOrg> {
    @Mapping(target = "rank", source = "rank")
    @Mapping(target = "organization", source = "organization")
    RankInOrgDTO toDto(RankInOrg s);
}
