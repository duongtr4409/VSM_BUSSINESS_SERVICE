package com.vsm.business.service.custom;

import com.vsm.business.domain.FileType;
import com.vsm.business.domain.Step;
import com.vsm.business.repository.FileTypeRepository;
import com.vsm.business.repository.StepRepository;
import com.vsm.business.repository.search.FileTypeSearchRepository;
import com.vsm.business.repository.search.StepSearchRepository;
import com.vsm.business.service.dto.FileTypeDTO;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.mapper.FileTypeMapper;
import com.vsm.business.service.mapper.StepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldTypeCustomService {
    private final Logger log = LoggerFactory.getLogger(FieldTypeCustomService.class);

    private FileTypeRepository fileTypeRepository;

    private FileTypeSearchRepository fileTypeSearchRepository;

    private FileTypeMapper fileTypeMapper;

    public FieldTypeCustomService(FileTypeRepository fileTypeRepository, FileTypeSearchRepository fileTypeSearchRepository, FileTypeMapper fileTypeMapper) {
        this.fileTypeRepository = fileTypeRepository;
        this.fileTypeSearchRepository = fileTypeSearchRepository;
        this.fileTypeMapper = fileTypeMapper;
    }

    public List<FileTypeDTO> getAll() {
        log.debug("FieldTypeCustomService: getAll()");
        List<FileTypeDTO> result = new ArrayList<>();
        try {
            List<FileType> fileTypes = this.fileTypeRepository.findAll();
            for (FileType fileType :
                fileTypes) {
                FileTypeDTO fileTypeDTO = fileTypeMapper.toDto(fileType);
                result.add(fileTypeDTO);
            }
        }catch (Exception e){
            log.error("FieldTypeCustomService: getAll() {}", e);
        }
        log.debug("FieldTypeCustomService: getAll() {}", result);
        return result;
    }

    public List<FileTypeDTO> deleteAll(List<FileTypeDTO> fileTypeDTOS) {
        log.debug("FieldTypeCustomService: deleteAll({})", fileTypeDTOS);
        List<Long> ids = fileTypeDTOS.stream().map(FileTypeDTO::getId).collect(Collectors.toList());
        this.fileTypeRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            fileTypeRepository.deleteById(id);
            fileTypeSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("FieldTypeCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }
}
