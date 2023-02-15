package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.FileType;
import com.vsm.business.repository.FileTypeRepository;
import com.vsm.business.repository.search.FileTypeSearchRepository;
import com.vsm.business.service.FileTypeService;
import com.vsm.business.service.dto.FileTypeDTO;
import com.vsm.business.service.mapper.FileTypeMapper;
import java.util.Optional;

import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FileType}.
 */
@Service
@Transactional
public class FileTypeServiceImpl implements FileTypeService {

    private final Logger log = LoggerFactory.getLogger(FileTypeServiceImpl.class);

    private final FileTypeRepository fileTypeRepository;

    private final FileTypeMapper fileTypeMapper;

    private final FileTypeSearchRepository fileTypeSearchRepository;

    public FileTypeServiceImpl(
        FileTypeRepository fileTypeRepository,
        FileTypeMapper fileTypeMapper,
        FileTypeSearchRepository fileTypeSearchRepository
    ) {
        this.fileTypeRepository = fileTypeRepository;
        this.fileTypeMapper = fileTypeMapper;
        this.fileTypeSearchRepository = fileTypeSearchRepository;
    }

    @Override
    public FileTypeDTO save(FileTypeDTO fileTypeDTO) {
        log.debug("Request to save FileType : {}", fileTypeDTO);
        FileType fileType = fileTypeMapper.toEntity(fileTypeDTO);
        fileType = fileTypeRepository.save(fileType);
        FileTypeDTO result = fileTypeMapper.toDto(fileType);
        try{
//            fileTypeSearchRepository.save(fileType);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<FileTypeDTO> partialUpdate(FileTypeDTO fileTypeDTO) {
        log.debug("Request to partially update FileType : {}", fileTypeDTO);

        return fileTypeRepository
            .findById(fileTypeDTO.getId())
            .map(existingFileType -> {
                fileTypeMapper.partialUpdate(existingFileType, fileTypeDTO);

                return existingFileType;
            })
            .map(fileTypeRepository::save)
            .map(savedFileType -> {
                fileTypeSearchRepository.save(savedFileType);

                return savedFileType;
            })
            .map(fileTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FileTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FileTypes");
        return fileTypeRepository.findAll(pageable).map(fileTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FileTypeDTO> findOne(Long id) {
        log.debug("Request to get FileType : {}", id);
        return fileTypeRepository.findById(id).map(fileTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileType : {}", id);
        fileTypeRepository.deleteById(id);
        fileTypeSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FileTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FileTypes for query {}", query);
        return fileTypeSearchRepository.search(query, pageable).map(fileTypeMapper::toDto);
    }
}
