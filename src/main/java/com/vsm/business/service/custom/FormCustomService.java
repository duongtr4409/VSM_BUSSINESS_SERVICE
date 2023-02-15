package com.vsm.business.service.custom;

import com.vsm.business.domain.Form;
import com.vsm.business.domain.Request;
import com.vsm.business.domain.Step;
import com.vsm.business.repository.FormRepository;
import com.vsm.business.repository.RequestRepository;
import com.vsm.business.repository.search.FormSearchRepository;
import com.vsm.business.service.dto.FormDTO;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.mapper.FormMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormCustomService {
    private final Logger log = LoggerFactory.getLogger(FormCustomService.class);

    private FormRepository formRepository;

    private FormSearchRepository formSearchRepository;

    private FormMapper formMapper;

    private RequestRepository requestRepository;

    public FormCustomService(FormRepository formRepository, FormSearchRepository formSearchRepository, FormMapper formMapper, RequestRepository requestRepository) {
        this.formRepository = formRepository;
        this.formSearchRepository = formSearchRepository;
        this.formMapper = formMapper;
        this.requestRepository = requestRepository;
    }

    public List<FormDTO> getAll() {
        log.debug("FormCustomService: getAll()");
        List<FormDTO> result = new ArrayList<>();
        try {
            List<Form> forms = this.formRepository.findAll();
            for (Form form :
                forms) {
                FormDTO stepDTO = formMapper.toDto(form);
                result.add(stepDTO);
            }
        }catch (Exception e){
            log.error("FormCustomService: getAll() {}", e);
        }
        log.debug("FormCustomService: getAll() {}", result);
        return result;
    }

    public List<FormDTO> deleteAll(List<FormDTO> formDTOS) {
        log.debug("FormCustomService: deleteAll({})", formDTOS);
        List<Long> ids = formDTOS.stream().map(FormDTO::getId).collect(Collectors.toList());
        this.formRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            formRepository.deleteById(id);
            formSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("FormCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<FormDTO> saveAll(List<FormDTO> formDTOList){
        List<FormDTO> result = formRepository.saveAll(formDTOList.stream().map(formMapper::toEntity).collect(Collectors.toList())).stream().map(formMapper::toDto).collect(Collectors.toList());
        log.debug("FormCustomService: saveAll({}) {}", formDTOList, result);
        return result;
    }

    public List<FormDTO> getAllByRequest(Long requestId){
        List<FormDTO> result = new ArrayList<>();
        Request request = this.requestRepository.findById(requestId).get();
//        result = this.formRepository.findAllByRequestsIn(Arrays.asList(request).stream().collect(Collectors.toSet())).stream().map(ele -> {
//            FormDTO formDTO = new FormDTO();
//            //BeanUtils.copyProperties(ele, formDTO);
//            formDTO.setId(ele.getId());
//            return formDTO;
//        }).collect(Collectors.toList());
        if(request.getForm() != null){
            FormDTO formDTO = new FormDTO();
            formDTO.setId(request.getForm().getId());
            result.add(formDTO);
        }
        return result;
    }
}
