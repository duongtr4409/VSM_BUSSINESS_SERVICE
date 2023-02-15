package com.vsm.business.service.custom;

import com.vsm.business.domain.Field;
import com.vsm.business.domain.Step;
import com.vsm.business.repository.FieldRepository;
import com.vsm.business.repository.StepRepository;
import com.vsm.business.repository.search.FieldSearchRepository;
import com.vsm.business.repository.search.StepSearchRepository;
import com.vsm.business.service.dto.FieldDTO;
import com.vsm.business.service.dto.FieldDataDTO;
import com.vsm.business.service.mapper.FieldMapper;
import com.vsm.business.service.mapper.StepMapper;
import com.vsm.business.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldCustomService {
    private final Logger log = LoggerFactory.getLogger(FieldCustomService.class);

    private FieldRepository fieldRepository;

    private FieldSearchRepository fieldSearchRepository;

    private FieldMapper fieldMapper;

    private ObjectUtils objectUtils;

    public FieldCustomService(FieldRepository fieldRepository, FieldSearchRepository fieldSearchRepository, FieldMapper fieldMapper, ObjectUtils objectUtils) {
        this.fieldRepository = fieldRepository;
        this.fieldSearchRepository = fieldSearchRepository;
        this.fieldMapper = fieldMapper;
        this.objectUtils = objectUtils;
    }

    public List<FieldDTO> getAll() {
        log.debug("FieldCustomService: getAll()");
        List<FieldDTO> result = new ArrayList<>();
        try {
            List<Field> fields = this.fieldRepository.findAll();
            for (Field field :
                fields) {
                FieldDTO fieldDTO = fieldMapper.toDto(field);
                result.add(fieldDTO);
            }
        }catch (Exception e){
            log.error("FieldCustomService: getAll() {}", e);
        }
        log.debug("StepCustomService: getAll() {}", result);
        return result;
    }

    public List<FieldDTO> deleteAll(List<FieldDTO> fieldDTOS) {
        log.debug("FiledCustomService: deleteAll({})", fieldDTOS);
        List<Long> ids = fieldDTOS.stream().map(FieldDTO::getId).collect(Collectors.toList());
        this.fieldRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            fieldRepository.deleteById(id);
            fieldSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("FieldCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<FieldDTO> getAllIgnoreField() {
        log.debug("FieldCustomService: getAllIgnoreField()");
        List<FieldDTO> result = new ArrayList<>();
        try {
            List<Field> fields = this.fieldRepository.findAll();
            for (Field field :
                fields) {
                //FieldDTO fieldDTO = fieldMapper.toDto(field);
                if(field != null){
                    FieldDTO fieldDTO = new FieldDTO();
                    fieldDTO = this.objectUtils.coppySimpleType(field, fieldDTO, FieldDTO.class);
                    result.add(fieldDTO);
                }
            }
        }catch (Exception e){
            log.error("FieldCustomService: getAll() {}", e);
        }
        log.debug("StepCustomService: getAll() {}", result);
        return result;
    }
}
