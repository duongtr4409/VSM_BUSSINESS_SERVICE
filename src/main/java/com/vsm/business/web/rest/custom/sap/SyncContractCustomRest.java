package com.vsm.business.web.rest.custom.sap;

import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.service.custom.sap.SyncContractCustomService;
import com.vsm.business.service.custom.sap.bo.SyncContractDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sync_sap")
public class SyncContractCustomRest {

    private final Logger log = LoggerFactory.getLogger(SyncContractCustomRest.class);

    @Autowired
    private SyncContractCustomService syncContractCustomService;

    @PostMapping("/contract")
    public ResponseEntity<IResponseMessage> syncContract(@RequestBody SyncContractDTO syncContractDto) throws Exception {
        String result = this.syncContractCustomService.callSAPAPI(syncContractDto);
        log.debug("SyncContractCustomRest({}): {}", syncContractDto, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/contract/{requestDataId}")
    public ResponseEntity<IResponseMessage> syncContractWithRequestDataId(@PathVariable("requestDataId") Long requestDataId){
        Map<String, Object> result = this.syncContractCustomService.syncDataToSAP(requestDataId);
        log.debug("syncContractWithRequestDataId({}): {}", requestDataId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
