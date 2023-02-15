package com.vsm.business.service.custom;

import com.vsm.business.domain.Examine;
import com.vsm.business.domain.MailTemplate;
import com.vsm.business.domain.Step;
import com.vsm.business.domain.StepData;
import com.vsm.business.repository.ExamineRepository;
import com.vsm.business.repository.MailTemplateRepository;
import com.vsm.business.repository.StepDataRepository;
import com.vsm.business.repository.StepRepository;
import com.vsm.business.repository.search.ExamineSearchRepository;
import com.vsm.business.repository.search.StepSearchRepository;
import com.vsm.business.service.custom.mail.MailSchedule;
import com.vsm.business.service.custom.processRequest.ProcessRequestCustomService;
import com.vsm.business.service.custom.processRequest.ProcessRequestService;
import com.vsm.business.service.dto.ExamineDTO;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.mapper.ExamineMapper;
import com.vsm.business.service.mapper.StepMapper;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamineCustomService {
    private final Logger log = LoggerFactory.getLogger(ExamineCustomService.class);

    private ExamineRepository examineRepository;

    private ExamineSearchRepository examineSearchRepository;

    private ExamineMapper examineMapper;

    private StepDataRepository stepDataRepository;

    private ProcessRequestCustomService processRequestCustomService;

    private MailTemplateRepository mailTemplateRepository;

    private MailSchedule mailSchedule;

    @Value("${system.mailtemplate.xoat-xet}")
    private String MAIL_XOAT_XET_CODE;

    public ExamineCustomService(ExamineRepository examineRepository, ExamineSearchRepository examineSearchRepository, ExamineMapper examineMapper, StepDataRepository stepDataRepository, ProcessRequestCustomService processRequestCustomService, MailTemplateRepository mailTemplateRepository, MailSchedule mailSchedule) {
        this.examineRepository = examineRepository;
        this.examineSearchRepository = examineSearchRepository;
        this.examineMapper = examineMapper;
        this.stepDataRepository = stepDataRepository;
        this.processRequestCustomService = processRequestCustomService;
        this.mailTemplateRepository = mailTemplateRepository;
        this.mailSchedule = mailSchedule;
    }

    public List<ExamineDTO> getAll() {
        log.debug("ExamineCustomService: getAll()");
        List<ExamineDTO> result = new ArrayList<>();
        try {
            List<Examine> examines = this.examineRepository.findAll();
            for (Examine examine :
                examines) {
                ExamineDTO examineDTO = examineMapper.toDto(examine);
                result.add(examineDTO);
            }
        }catch (Exception e){
            log.error("ExamineCustomService: getAll() {}", e);
        }
        log.debug("ExamineCustomService: getAll() {}", result);
        return result;
    }

    public List<ExamineDTO> deleteAll(List<ExamineDTO> examineDTOS) {
        log.debug("StepCustomService: deleteAll({})", examineDTOS);
        List<Long> ids = examineDTOS.stream().map(ExamineDTO::getId).collect(Collectors.toList());
        this.examineRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            examineRepository.deleteById(id);
            examineSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("ExamineCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<ExamineDTO> getAllByUserAndRequestData(Long userId, Long requestDataId){
        List<StepData> stepDataList = this.stepDataRepository.findAllByRequestDataId(requestDataId);
        if(stepDataList == null || stepDataList.isEmpty()) return new ArrayList<>();
        List<ExamineDTO> result = new ArrayList<>();
        stepDataList.forEach(ele -> {
            result.addAll(this.examineRepository.findAllByReceiverIdAndStepDataId(userId, ele.getId()).stream().map(this.examineMapper::toDto).collect(Collectors.toList()));
        });
        log.debug("ExamineCustomService: getAllByUserAndRequestData(userId: {}, requestDataId: {}): {}", userId, requestDataId, result);
        return result;
    }


    public ExamineDTO customSave(ExamineDTO examineDTO) {
        log.debug("Request to save Examine : {}", examineDTO);
        Examine examine = examineMapper.toEntity(examineDTO);
        examine = examineRepository.save(examine);
        ExamineDTO result = examineMapper.toDto(examine);
        try{
//            examineSearchRepository.save(examine);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }

        try {
            // thêm phần gửi mail khi tạo thành công
            List<MailTemplate> mailTemplateList = mailTemplateRepository.findAllByMailTemplateCode(MAIL_XOAT_XET_CODE);
            MailTemplate mailTemplate;
            if(mailTemplateList != null && mailTemplateList.size() > 0){
                mailTemplate = mailTemplateList.get(0);
            }else{
                String mailName = "EOFFICE: MAIL THÔNG BÁO CÓ YÊU CẦU XOÁT XÉT.";
                String mailSubject = "VCR: THÔNG BÁO CÓ YÊU CẦU XOÁT XÉT";
                String mailContent = "<p><strong>Dear anh/chị</strong></p><p>Phiếu yêu cầu : [rfa_code] có yêu cầu xoát xét gửi đến anh/chị.</p><p><em><strong>Thông tin yêu cầu / Request information</strong></em></p><p>&nbsp; - Loại tờ trình / Request type: [request_<em>type_</em>name]</p><p>&nbsp; - Số tờ trình/ Code: [rfa_code]</p><p>&nbsp; - Tên tờ trình/Title: [rfa_name]</p><p>- Người trình/ Requestor: [requester_name] (Fullname)</p><p>- Bộ phận/Department: [requester_department] (Tên bộ phận)</p><p><em><strong>ps</strong>: mail gửi tự động vui lòng không phản hồi</em></p><p><em>thank and best request</em></p>";
                mailTemplate = mailSchedule.initMailTemplateCanhBaoQuaHan(MAIL_XOAT_XET_CODE, mailName, mailContent, mailSubject);
            }
            processRequestCustomService.autoSendMail(examine.getStepData().getRequestData().getId(), mailTemplate, Arrays.asList(examine.getReceiver()), null, new ArrayList<>(),new ArrayList<>(), new HashMap<>(), examine.getSender());
        }catch (Exception e){
            log.error("ExamineCustomService customSave(): Lỗi khi gửi mail xoát xét: {}", e);
        }

        return result;
    }
}
