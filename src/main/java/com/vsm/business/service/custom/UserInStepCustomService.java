package com.vsm.business.service.custom;

import com.vsm.business.domain.UserInStep;
import com.vsm.business.repository.UserInStepRepository;
import com.vsm.business.repository.search.UserInStepSearchRepository;
import com.vsm.business.service.dto.UserInStepDTO;
import com.vsm.business.service.mapper.UserInStepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserInStepCustomService {
    private final Logger log = LoggerFactory.getLogger(UserInStepCustomService.class);

    private final UserInStepRepository userInStepRepository;

    private final UserInStepMapper userInStepMapper;

    private final UserInStepSearchRepository userInStepSearchRepository;

    public UserInStepCustomService(
        UserInStepRepository userInStepRepository,
        UserInStepMapper userInStepMapper,
        UserInStepSearchRepository userInStepSearchRepository
    ) {
        this.userInStepRepository = userInStepRepository;
        this.userInStepMapper = userInStepMapper;
        this.userInStepSearchRepository = userInStepSearchRepository;
    }

    public List<UserInStepDTO> findAllByStepInProcessId(Long stepInProcessId){
        log.debug("UserInStepCustomService: findAllByStepInProcessId({})", stepInProcessId);
        return userInStepRepository.findAllByStepInProcessId(stepInProcessId).stream().map(userInStepMapper::toDto).collect(Collectors.toList());
    }

    public Boolean deleteAllByStepInProcessId(Long stepInProcessId) {
        try {
            List<UserInStep> allByStepInProcessId = userInStepRepository.findAllByStepInProcessId(stepInProcessId);
            if(allByStepInProcessId != null && !allByStepInProcessId.isEmpty()){
                userInStepRepository.deleteAll(allByStepInProcessId);
                userInStepSearchRepository.deleteAll(allByStepInProcessId);
            }
            return true;
        }catch (Exception e){
            log.error("UserInStepCustomService: deleteAllByStepInProcessId({}): {}", stepInProcessId, e.getStackTrace());
            return false;
        }
    }
}
