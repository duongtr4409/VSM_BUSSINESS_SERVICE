package com.vsm.business.web.rest.custom.mule;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.service.mule.PartnerDTO;
import com.vsm.business.service.mule.PartnerService;
import com.vsm.business.service.mule.VibdroDTO;
import com.vsm.business.service.mule.VibdroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sap/_sync")
public class MulesoftCustomRest1 {

    private final Logger log = LoggerFactory.getLogger(MulesoftCustomRest1.class);

    @Autowired
    public VibdroService vibdroService;

    @Autowired
    public PartnerService partnerService;

    @PostMapping("/vibdro")
    public ResponseEntity<IResponseMessage> syncVibdro(@RequestBody List<VibdroDTO> vibdroDTOList){
        log.debug("/api/sap/_sync/vibdro");
        if(vibdroDTOList == null || vibdroDTOList.isEmpty()){
            return ResponseEntity.ok().body(new FailLoadMessage(vibdroDTOList));
        }
        List<?> result = vibdroService.sync(vibdroDTOList);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/partner")
    public ResponseEntity<IResponseMessage> syncParner(@RequestBody List<PartnerDTO> partnerDTOList){
        log.debug("/api/sap/_sync/partner");
        if(partnerDTOList == null || partnerDTOList.isEmpty()){
            return ResponseEntity.ok().body(new FailLoadMessage(partnerDTOList));
        }
        List<?> result = this.partnerService.sync(partnerDTOList);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
