package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.FileTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FileType} and its DTO {@link FileTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = { TennantMapper.class, UserInfoMapper.class })
public interface FileTypeMapper extends EntityMapper<FileTypeDTO, FileType> {
    @Mapping(target = "tennant", source = "tennant", qualifiedByName = "id")
    @Mapping(target = "created", source = "created", qualifiedByName = "id")
    @Mapping(target = "modified", source = "modified", qualifiedByName = "id")
    FileTypeDTO toDto(FileType s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FileTypeDTO toDtoId(FileType fileType);
}
