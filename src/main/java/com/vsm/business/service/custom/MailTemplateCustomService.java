package com.vsm.business.service.custom;

import com.vsm.business.domain.MailTemplate;
import com.vsm.business.repository.MailTemplateRepository;
import com.vsm.business.repository.search.MailTemplateSearchRepository;
import com.vsm.business.service.dto.MailTemplateDTO;
import com.vsm.business.service.dto.ProcessInfoDTO;
import com.vsm.business.service.mapper.MailTemplateMapper;
import com.vsm.business.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;

@Service
public class MailTemplateCustomService {
    private final Logger log = LoggerFactory.getLogger(MailTemplateCustomService.class);

    private MailTemplateRepository mailTemplateRepository;
    private MailTemplateSearchRepository mailTemplateSearchRepository;
    private MailTemplateMapper mailTemplateMapper;

    public MailTemplateCustomService(MailTemplateRepository mailTemplateRepository, MailTemplateSearchRepository mailTemplateSearchRepository, MailTemplateMapper mailTemplateMapper) {
        this.mailTemplateRepository = mailTemplateRepository;
        this.mailTemplateSearchRepository = mailTemplateSearchRepository;
        this.mailTemplateMapper = mailTemplateMapper;
    }

    public List<MailTemplateDTO> getAll() {
//        log.debug("MailTemplateCustomService: getAll()");
//        List<MailTemplateDTO> result = this.mailTemplateRepository.findAll().stream().map(mailTemplateMapper::toDto).collect(Collectors.toList());
//        return result;
        List<MailTemplate> mailTemplateList = this.mailTemplateRepository.findAll();
        return convertToDTO(mailTemplateList);
    }

    public List<MailTemplateDTO> deleteAll(List<MailTemplateDTO> mailTemplateDTOS){
        log.debug("MailTemplateCustomService: deleteAll({})", mailTemplateDTOS);
        List<Long> ids = mailTemplateDTOS.stream().map(MailTemplateDTO::getId).collect(Collectors.toList());
        this.mailTemplateRepository.deleteAllById(ids);
        return this.getAll();
    }

    public List<MailTemplateDTO> saveAll(List<MailTemplateDTO> mailTemplateDTOS){
        List<MailTemplateDTO> result = mailTemplateRepository.saveAll(mailTemplateDTOS.stream().map(mailTemplateMapper::toEntity).collect(Collectors.toList())).stream().map(mailTemplateMapper::toDto).collect(Collectors.toList());
        log.debug("MailTemplateCustomService: saveAll({}) {}", mailTemplateDTOS, result);
        return  result;
    }


        // utils \\
    @Autowired
    private ObjectUtils objectUtils;
    private MailTemplateDTO convertToDTO(MailTemplate mailTemplate) throws IllegalAccessException {
        if(mailTemplate == null) return null;
        MailTemplateDTO result = new MailTemplateDTO();
        result = objectUtils.copyproperties(mailTemplate, result, MailTemplateDTO.class);

            // lấy thông tin quy trình áp dụng \\
        Set<ProcessInfoDTO> processInfoDTOList = new HashSet<>();
        mailTemplate.getStepInProcesses().forEach(ele -> {
            try {
                processInfoDTOList.add(objectUtils.copyproperties(ele.getProcessInfo(), new ProcessInfoDTO(), ProcessInfoDTO.class));
            } catch (IllegalAccessException e) {throw new RuntimeException(e);}
        });
        result.setProcessInfos(processInfoDTOList);

        return result;
    }

    private List<MailTemplateDTO> convertToDTO(List<MailTemplate> mailTemplateList){
        if(mailTemplateList == null) return null;
        List<MailTemplateDTO> result = new ArrayList<>();
        mailTemplateList.forEach(ele -> {
            try {
                result.add(this.convertToDTO(ele));
            } catch (IllegalAccessException e) {throw new RuntimeException(e);}
        });
        return result;
    }
}
