package com.vsm.business.service.custom;

import com.vsm.business.domain.Examine;
import com.vsm.business.domain.ExamineReply;
import com.vsm.business.domain.MailTemplate;
import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.ExamineReplyRepository;
import com.vsm.business.repository.ExamineRepository;
import com.vsm.business.repository.MailTemplateRepository;
import com.vsm.business.repository.search.ExamineReplySearchRepository;
import com.vsm.business.service.custom.mail.MailSchedule;
import com.vsm.business.service.custom.processRequest.ProcessRequestCustomService;
import com.vsm.business.service.dto.ExamineReplyDTO;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.mapper.ExamineMapper;
import com.vsm.business.service.mapper.ExamineReplyMapper;
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
public class ExamineReplyCustomService {
    private final Logger log = LoggerFactory.getLogger(ExamineReplyCustomService.class);

    @Value("${system.mailtemplate.tra-loi-xoat-xet}")
    private String MAIL_TRA_LOI_XOAT_XET_CODE;

    private ExamineReplyRepository examineReplyRepository;

    private ExamineReplySearchRepository examineReplySearchRepository;

    private ExamineReplyMapper examineReplyMapper;

    private MailTemplateRepository mailTemplateRepository;

    private ProcessRequestCustomService processRequestCustomService;

    private ExamineRepository examineRepository;

    private MailSchedule mailSchedule;

    public ExamineReplyCustomService(ExamineReplyRepository examineReplyRepository, ExamineReplySearchRepository examineReplySearchRepository, ExamineReplyMapper examineReplyMapper, MailTemplateRepository mailTemplateRepositor, ProcessRequestCustomService processRequestCustomService, MailSchedule mailSchedule, ExamineRepository examineRepository) {
        this.examineReplyRepository = examineReplyRepository;
        this.examineReplySearchRepository = examineReplySearchRepository;
        this.examineReplyMapper = examineReplyMapper;
        this.mailTemplateRepository = mailTemplateRepositor;
        this.processRequestCustomService = processRequestCustomService;
        this.mailSchedule = mailSchedule;
        this.examineRepository = examineRepository;
    }

    public List<ExamineReplyDTO> getAll() {
        log.debug("ExamineReplyCustomService: getAll()");
        List<ExamineReplyDTO> result = new ArrayList<>();
        try {
            List<ExamineReply> examineReplies = this.examineReplyRepository.findAll();
            for (ExamineReply examineReply :
                examineReplies) {
                ExamineReplyDTO examineReplyDTO = examineReplyMapper.toDto(examineReply);
                result.add(examineReplyDTO);
            }
        }catch (Exception e){
            log.error("ExamineReplyCustomService: getAll() {}", e);
        }
        log.debug("ExamineReplyCustomService: getAll() {}", result);
        return result;
    }

    public List<ExamineReplyDTO> deleteAll(List<ExamineReplyDTO> examineReplyDTOS) {
        log.debug("ExamineReplyCustomService: deleteAll({})", examineReplyDTOS);
        List<Long> ids = examineReplyDTOS.stream().map(ExamineReplyDTO::getId).collect(Collectors.toList());
        this.examineReplyRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            examineReplyRepository.deleteById(id);
            examineReplySearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("ExamineReplyCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }


    public ExamineReplyDTO customSave(ExamineReplyDTO examineReplyDTO) {
        log.debug("Request to save ExamineReply : {}", examineReplyDTO);
        ExamineReply examineReply = examineReplyMapper.toEntity(examineReplyDTO);
        examineReply = examineReplyRepository.save(examineReply);
        ExamineReplyDTO result = examineReplyMapper.toDto(examineReply);
        try{
//            examineReplySearchRepository.save(examineReply);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }

        try {
            // thêm phần gửi mail khi tạo thành công
            List<MailTemplate> mailTemplateList = mailTemplateRepository.findAllByMailTemplateCode(MAIL_TRA_LOI_XOAT_XET_CODE);
            MailTemplate mailTemplate;
            if(mailTemplateList != null && mailTemplateList.size() > 0){
                mailTemplate = mailTemplateList.get(0);
            }else{
                String mailName = "EOFFICE: MAIL THÔNG BÁO CÓ PHẢN HỒI YÊU CẦU XOÁT XÉT.";
                String mailSubject = "VCR: THÔNG BÁO CÓ PHẢN HỒI YÊU CẦU XOÁT XÉT";
                String mailContent = "<p><strong>Dear anh/chị</strong></p><p>Phiếu yêu cầu : [rfa_code] đã có trả lời yêu cầu xoát xét gửi đến anh/chị.</p><p><em><strong>Thông tin yêu cầu / Request information</strong></em></p><p>&nbsp; - Loại tờ trình / Request type: [request_<em>type_</em>name]</p><p>&nbsp; - Số tờ trình/ Code: [rfa_code]</p><p>&nbsp; - Tên tờ trình/Title: [rfa_name]</p><p>- Người trình/ Requestor: [requester_name] (Fullname)</p><p>- Bộ phận/Department: [requester_department] (Tên bộ phận)</p><p><em><strong>ps</strong>: mail gửi tự động vui lòng không phản hồi</em></p><p><em>thank and best request</em></p>";
                mailTemplate = mailSchedule.initMailTemplateCanhBaoQuaHan(MAIL_TRA_LOI_XOAT_XET_CODE, mailName, mailContent, mailSubject);
            }

            // chuẩn bị thông tin gửi mail \\
            Examine examine = this.examineRepository.findById(examineReply.getExamine().getId()).get();
            UserInfo userSendExamine = examine.getSender();
            Long requestDataId = examine.getStepData().getRequestData().getId();

            processRequestCustomService.autoSendMail(requestDataId, mailTemplate, Arrays.asList(userSendExamine), null, new ArrayList<>(),new ArrayList<>(), new HashMap<>(), examineReply.getSender());
        }catch (Exception e){
            log.error("ExamineCustomService customSave(): Lỗi khi gửi mail xoát xét: {}", e);
        }

        return result;
    }

}
