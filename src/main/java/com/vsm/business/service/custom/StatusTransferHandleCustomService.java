package com.vsm.business.service.custom;

import com.vsm.business.domain.StatusTransferHandle;
import com.vsm.business.repository.StatusTransferHandleRepository;
import com.vsm.business.repository.search.StatusTransferHandleSearchRepository;
import com.vsm.business.service.dto.StatusTransferHandleDTO;
import com.vsm.business.service.mapper.StatusTransferHandleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusTransferHandleCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);

    private StatusTransferHandleRepository statusTransferHandleRepository;
    private StatusTransferHandleSearchRepository statusTransferHandleSearchRepository;
    private StatusTransferHandleMapper statusTransferHandleMapper;

    public StatusTransferHandleCustomService(StatusTransferHandleRepository statusTransferHandleRepository, StatusTransferHandleSearchRepository statusTransferHandleSearchRepository, StatusTransferHandleMapper statusTransferHandleMapper) {
        this.statusTransferHandleRepository = statusTransferHandleRepository;
        this.statusTransferHandleSearchRepository = statusTransferHandleSearchRepository;
        this.statusTransferHandleMapper = statusTransferHandleMapper;
    }

    public List<StatusTransferHandleDTO> getAll() {
        log.debug("StatusTransferHandleCustomService: getAll()");
        List<StatusTransferHandle> statusTransferHandles = this.statusTransferHandleRepository.findAll();
        List<StatusTransferHandleDTO> result = new ArrayList<>();
        for (StatusTransferHandle transferHandle :
            statusTransferHandles) {
            StatusTransferHandleDTO statusTransferHandleDTO = statusTransferHandleMapper.toDto(transferHandle);
            result.add(statusTransferHandleDTO);
        }
        return result;
    }

    public List<StatusTransferHandleDTO> deleteAll(List<StatusTransferHandleDTO> statusTransferHandleDTOS) {
        log.debug("StatusTransferHandleCustomService: deleteAll({})", statusTransferHandleDTOS);
        List<Long> ids = statusTransferHandleDTOS.stream().map(StatusTransferHandleDTO::getId).collect(Collectors.toList());
        this.statusTransferHandleRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            statusTransferHandleRepository.deleteById(id);
            statusTransferHandleSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("StatusTransferHandleCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

	public List<StatusTransferHandleDTO> saveAll(List<StatusTransferHandleDTO> statusTransferHandleDTOS){
        List<StatusTransferHandleDTO> result = statusTransferHandleRepository.saveAll(statusTransferHandleDTOS.stream().map(statusTransferHandleMapper::toEntity).collect(Collectors.toList())).stream().map(statusTransferHandleMapper::toDto).collect(Collectors.toList());
        log.debug("StatusTransferHandleCustomService: saveAll({}): {}", statusTransferHandleDTOS, result);
        return result;
    }

}
