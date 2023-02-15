package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.RankDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rank} and its DTO {@link RankDTO}.
 */
@Mapper(componentModel = "spring", uses = { TennantMapper.class, UserInfoMapper.class })
public interface RankMapper extends EntityMapper<RankDTO, Rank> {
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created", qualifiedByName = "id")
    @Mapping(target = "modified", source = "modified", qualifiedByName = "id")
    RankDTO toDto(Rank s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RankDTO toDtoId(Rank rank);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<RankDTO> toDtoIdSet(Set<Rank> rank);
}
