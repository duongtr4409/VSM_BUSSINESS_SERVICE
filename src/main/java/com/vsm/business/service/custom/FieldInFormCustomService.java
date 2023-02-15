package com.vsm.business.service.custom;

import com.vsm.business.repository.FieldInFormRepository;
import com.vsm.business.repository.search.FieldInFormSearchRepository;
import com.vsm.business.service.dto.FieldInFormDTO;
import com.vsm.business.service.mapper.FieldInFormMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FieldInFormCustomService {
    private final Logger log = LoggerFactory.getLogger(FieldInFormCustomService.class);

    private final FieldInFormRepository fieldInFormRepository;

    private final FieldInFormMapper fieldInFormMapper;

    private final FieldInFormSearchRepository fieldInFormSearchRepository;

    public FieldInFormCustomService(
        FieldInFormRepository fieldInFormRepository,
        FieldInFormMapper fieldInFormMapper,
        FieldInFormSearchRepository fieldInFormSearchRepository
    ) {
        this.fieldInFormRepository = fieldInFormRepository;
        this.fieldInFormMapper = fieldInFormMapper;
        this.fieldInFormSearchRepository = fieldInFormSearchRepository;
    }

    public List<FieldInFormDTO> findAllByEFormId(Long formId, Boolean ignoreField){
        log.debug("UserInStepCustomService: findAllByStepInProcessId({})", formId);
        if(ignoreField){
            List<FieldInFormDTO> result = this.fieldInFormRepository.findAllByFormId(formId).stream().map(ele -> {
                FieldInFormDTO fieldInFormDTO = new FieldInFormDTO();
                BeanUtils.copyProperties(ele, fieldInFormDTO, "form");
                return fieldInFormDTO;
            }).collect(Collectors.toList());
            return result;
        }
        return fieldInFormRepository.findAllByFormId(formId).stream().map(fieldInFormMapper::toDto).collect(Collectors.toList());
    }
}
