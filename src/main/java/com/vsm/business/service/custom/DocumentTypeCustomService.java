package com.vsm.business.service.custom;

import com.vsm.business.domain.DocumentType;
import com.vsm.business.domain.Step;
import com.vsm.business.repository.DocumentTypeRepository;
import com.vsm.business.repository.search.DocumentTypeSearchRepository;
import com.vsm.business.service.dto.DocumentTypeDTO;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.mapper.DocumentTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentTypeCustomService {
    private final Logger log = LoggerFactory.getLogger(DocumentTypeCustomService.class);

    private DocumentTypeRepository documentTypeRepository;

    private DocumentTypeSearchRepository documentTypeSearchRepository;

    private DocumentTypeMapper documentTypeMapper;

    public DocumentTypeCustomService(DocumentTypeRepository documentTypeRepository, DocumentTypeSearchRepository documentTypeSearchRepository, DocumentTypeMapper documentTypeMapper) {
        this.documentTypeRepository = documentTypeRepository;
        this.documentTypeSearchRepository = documentTypeSearchRepository;
        this.documentTypeMapper = documentTypeMapper;
    }

    public List<DocumentTypeDTO> getAll() {
        log.debug("DocumentTypeCustomService: getAll()");
        List<DocumentTypeDTO> result = new ArrayList<>();
        try {
            List<DocumentType> documentTypes = this.documentTypeRepository.findAll();
            for (DocumentType documentType :
                documentTypes) {
                DocumentTypeDTO documentTypeDTO = documentTypeMapper.toDto(documentType);
                result.add(documentTypeDTO);
            }
        }catch (Exception e){
            log.error("DocumentTypeCustomService: getAll() {}", e);
        }
        log.debug("DocumentTypeCustomService: getAll() {}", result);
        return result;
    }

    public List<DocumentTypeDTO> deleteAll(List<DocumentTypeDTO> documentTypeDTOS) {
        log.debug("DocumentTypeCustomService: deleteAll({})", documentTypeDTOS);
        List<Long> ids = documentTypeDTOS.stream().map(DocumentTypeDTO::getId).collect(Collectors.toList());
        this.documentTypeRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            documentTypeRepository.deleteById(id);
            documentTypeSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("DocumentTypeCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }
}
