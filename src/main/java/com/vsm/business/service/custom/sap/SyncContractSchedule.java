package com.vsm.business.service.custom.sap;

import com.vsm.business.domain.RequestData;
import com.vsm.business.repository.RequestDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SyncContractSchedule {

    private final Logger log = LoggerFactory.getLogger(SyncContractSchedule.class);


    private final SyncContractCustomService syncContractCustomService;

    private final RequestDataRepository requestDataRepository;

    public SyncContractSchedule(SyncContractCustomService syncContractCustomService, RequestDataRepository requestDataRepository) {
        this.syncContractCustomService = syncContractCustomService;
        this.requestDataRepository = requestDataRepository;
    }


    /**
     * hàm thực hiện đồng bộ dữ liệu sang cho SAP (những dữ liệu đã từng đồng bộ và bị faild)
     */
    @Scheduled(cron = "${cron.tab}")
    public void SyncContractToSAP(){
        List<Long> listRequestDataId = this.requestDataRepository.getAllRequestDataSyncSAPFailed();
        if(listRequestDataId != null && !listRequestDataId.isEmpty()){
            try {
                for(Long requestDataId : listRequestDataId){
                    Map<String, Object> result = this.syncContractCustomService.syncDataToSAP(requestDataId);
                    Boolean resultRes = (Boolean) result.get("res");
                    String contractNumber = result.get("mess") != null ? result.get("mess").toString() : null;
                    RequestData requestData = this.requestDataRepository.findById(requestDataId).orElse(null);
                    if(requestData == null) continue;
                    requestData.setContractNumber(contractNumber);
                    requestData.setResultSyncContract(resultRes);
                    this.requestDataRepository.save(requestData);
                }
            }catch (Exception e){
                log.error("{}", e);
            }
        }
    }

}
