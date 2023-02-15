package com.vsm.business.web.rest.custom.mule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.service.mule.PartnerDTO;
import com.vsm.business.service.mule.VibdroDTO;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

//@RestController
@RequestMapping("/api/sap/_sync")
public class MulesoftCustomRest {
    private final Logger log = LoggerFactory.getLogger(MulesoftCustomRest.class);

    @PostMapping("/vibdro")
    public ResponseEntity<IResponseMessage> syncVibdro(@RequestBody List<VibdroDTO> vibdroDtos) {
        log.debug("/api/sap/_sync/vibdro");
        if (vibdroDtos.isEmpty()) {
            return ResponseEntity.ok().body(new FailLoadMessage(vibdroDtos));
        }
        return ResponseEntity.ok().body(new LoadedMessage(vibdroDtos));
    }

    @PostMapping("/partner")
    public ResponseEntity<IResponseMessage> syncParner(@RequestBody List<PartnerDTO> partnerDtos) {
        log.debug("/api/sap/_sync/partner");
        if (partnerDtos.isEmpty()) {
            return ResponseEntity.ok().body(new FailLoadMessage(partnerDtos));
        }
        return ResponseEntity.ok().body(new LoadedMessage(partnerDtos));
    }

}
