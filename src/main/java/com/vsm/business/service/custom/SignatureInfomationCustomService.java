package com.vsm.business.service.custom;

import com.vsm.business.domain.SignatureInfomation;
import com.vsm.business.repository.AttachmentPermisitionRepository;
import com.vsm.business.repository.SignatureInfomationRepository;
import com.vsm.business.repository.search.AttachmentPermisitionSearchRepository;
import com.vsm.business.repository.search.SignatureInfomationSearchRepository;
import com.vsm.business.service.custom.StepCustomService;
import com.vsm.business.service.dto.AttachmentPermisitionDTO;
import com.vsm.business.service.dto.SignatureInfomationDTO;
import com.vsm.business.service.mapper.AttachmentPermisitionMapper;
import com.vsm.business.service.mapper.SignatureInfomationMapper;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SignatureInfomationCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);

    private SignatureInfomationRepository signatureInfomationRepository;
    private SignatureInfomationSearchRepository signatureInfomationSearchRepository;
    private SignatureInfomationMapper signatureInfomationMapper;

    public SignatureInfomationCustomService(SignatureInfomationRepository signatureInfomationRepository, SignatureInfomationSearchRepository signatureInfomationSearchRepository, SignatureInfomationMapper signatureInfomationMapper) {
        this.signatureInfomationRepository = signatureInfomationRepository;
        this.signatureInfomationSearchRepository = signatureInfomationSearchRepository;
        this.signatureInfomationMapper = signatureInfomationMapper;
    }

    public List<SignatureInfomationDTO> getAll() {
        log.debug("SignatureInfomationCustomService: getAll()");
        List<SignatureInfomationDTO> result = this.signatureInfomationRepository.findAll().stream().map(signatureInfomationMapper::toDto).collect(Collectors.toList());
        return result;
    }

    public List<SignatureInfomationDTO> deleteAll(List<SignatureInfomationDTO> signatureInfomationDTOS){
        log.debug("SignatureInfomationCustomService: deleteAll({})", signatureInfomationDTOS);
        List<Long> ids = signatureInfomationDTOS.stream().map(SignatureInfomationDTO::getId).collect(Collectors.toList());
        this.signatureInfomationRepository.deleteAllById(ids);
        return this.getAll();
    }

    public List<SignatureInfomationDTO> getAllByUser(Long userId){
        log.debug("SignatureInfomationCustomService: getAllByUser({})", userId);
        List<SignatureInfomationDTO> result = this.signatureInfomationRepository.findAllByUserInfoId(userId).stream().map(signatureInfomationMapper::toDto).collect(Collectors.toList());
        return result;
    }

    public SignatureInfomationDTO customSave(SignatureInfomationDTO signatureInfomationDTO){
        log.debug("SignatureInfomationCustomService : customSave({}): {}", signatureInfomationDTO);
        // bỏ idefaul ở các signatureInfomation cùng 1 user
        if(signatureInfomationDTO.getIsDefault()){
            try{
                List<SignatureInfomation> signatureInfomationListOfUser = this.signatureInfomationRepository.findAllByUserInfoId(signatureInfomationDTO.getUserInfo().getId());
                if(signatureInfomationListOfUser != null && !signatureInfomationListOfUser.isEmpty()){
                    signatureInfomationListOfUser.forEach(ele -> {
                        ele.setIsDefault(false);
                        this.signatureInfomationRepository.save(ele);
                    });
                }
            }catch (Exception e){log.info("NO USER -> CAN NOT FIND signatureInfomationListOfUser: {}", e);}
        }


        SignatureInfomation signatureInfomation = signatureInfomationMapper.toEntity(signatureInfomationDTO);
        signatureInfomation = signatureInfomationRepository.save(signatureInfomation);
        SignatureInfomationDTO result = signatureInfomationMapper.toDto(signatureInfomation);
        try{
//            signatureInfomationSearchRepository.save(signatureInfomation);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
            return result;
    }

}
