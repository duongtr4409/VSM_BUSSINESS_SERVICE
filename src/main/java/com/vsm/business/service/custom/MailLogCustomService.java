package com.vsm.business.service.custom;

import com.vsm.business.domain.MailLog;
import com.vsm.business.domain.Step;
import com.vsm.business.repository.MailLogRepository;
import com.vsm.business.repository.StepRepository;
import com.vsm.business.repository.search.MailLogSearchRepository;
import com.vsm.business.repository.search.StepSearchRepository;
import com.vsm.business.service.dto.MailLogDTO;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.mapper.MailLogMapper;
import com.vsm.business.service.mapper.StepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailLogCustomService {
    private final Logger log = LoggerFactory.getLogger(MailLogCustomService.class);

    private MailLogRepository mailLogRepository;

    private MailLogSearchRepository mailLogSearchRepository;

    private MailLogMapper mailLogMapper;

    public MailLogCustomService(MailLogRepository mailLogRepository, MailLogSearchRepository mailLogSearchRepository, MailLogMapper mailLogMapper) {
        this.mailLogRepository = mailLogRepository;
        this.mailLogSearchRepository = mailLogSearchRepository;
        this.mailLogMapper = mailLogMapper;
    }

    public List<MailLogDTO> getAll() {
        log.debug("MailLogCustomService: getAll()");
        List<MailLogDTO> mailLogDTOS = new ArrayList<>();
        try {
            List<MailLog> mailLogs = this.mailLogRepository.findAll();
            for (MailLog mailLog :
                mailLogs) {
                MailLogDTO mailLogDTO = mailLogMapper.toDto(mailLog);
                mailLogDTOS.add(mailLogDTO);
            }
        }catch (Exception e){
            log.error("StepCustomService: getAll() {}", e);
        }
        log.debug("MailLogCustomService: getAll() {}", mailLogDTOS);
        return mailLogDTOS;
    }

    public List<MailLogDTO> deleteAll(List<MailLogDTO> mailLogDTOS) {
        log.debug("MailLogCustomService: deleteAll({})", mailLogDTOS);
        List<Long> ids = mailLogDTOS.stream().map(MailLogDTO::getId).collect(Collectors.toList());
        this.mailLogRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            mailLogRepository.deleteById(id);
            mailLogSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("MailLogCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }
}
