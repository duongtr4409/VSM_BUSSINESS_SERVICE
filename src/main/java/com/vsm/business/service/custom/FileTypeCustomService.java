package com.vsm.business.service.custom;

import com.vsm.business.domain.FileType;
import com.vsm.business.repository.AttachmentPermisitionRepository;
import com.vsm.business.repository.FileTypeRepository;
import com.vsm.business.repository.search.AttachmentPermisitionSearchRepository;
import com.vsm.business.repository.search.FileTypeSearchRepository;
import com.vsm.business.service.dto.AttachmentPermisitionDTO;
import com.vsm.business.service.dto.FileTypeDTO;
import com.vsm.business.service.mapper.AttachmentPermisitionMapper;
import com.vsm.business.service.mapper.FileTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileTypeCustomService {
    private final Logger log = LoggerFactory.getLogger(FileTypeCustomService.class);

    private FileTypeRepository fileTypeRepository;
    private FileTypeSearchRepository fileTypeSearchRepository;
    private FileTypeMapper fileTypeMapper;

    public FileTypeCustomService(FileTypeRepository fileTypeRepository, FileTypeSearchRepository fileTypeSearchRepository, FileTypeMapper fileTypeMapper) {
        this.fileTypeRepository = fileTypeRepository;
        this.fileTypeSearchRepository = fileTypeSearchRepository;
        this.fileTypeMapper = fileTypeMapper;
    }

    public List<FileTypeDTO> getAll() {
        log.debug("FileTypeCustomService: getAll()");
        List<FileTypeDTO> result = this.fileTypeRepository.findAll().stream().map(fileTypeMapper::toDto).collect(Collectors.toList());
        return result;
    }

    public List<FileTypeDTO> deleteAll(List<AttachmentPermisitionDTO> attachmentPermisitionDTOS){
        log.debug("FileTypeCustomService: deleteAll({})", attachmentPermisitionDTOS);
        List<Long> ids = attachmentPermisitionDTOS.stream().map(AttachmentPermisitionDTO::getId).collect(Collectors.toList());
        this.fileTypeRepository.deleteAllById(ids);
        return this.getAll();
    }

}
