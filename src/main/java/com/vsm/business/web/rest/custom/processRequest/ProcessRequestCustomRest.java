package com.vsm.business.web.rest.custom.processRequest;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.service.auth.bo.MyUserDetail;
import com.vsm.business.service.custom.processRequest.ConcurrentProcessRequestCustomService;
import com.vsm.business.service.custom.processRequest.ProcessRequestCustomService;
import com.vsm.business.service.custom.processRequest.bo.ApproveOption;

import com.vsm.business.service.custom.processRequest.bo.ConcurrentApproveOption;
import com.vsm.business.service.custom.processRequest.bo.ReSendOption;
import com.vsm.business.web.rest.errors.bo.exception.AuthorityException;
import com.vsm.business.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST controller for managing {@link com.vsm.business.domain.RequestData}.
 */
@RestController
@RequestMapping("/api")
public class ProcessRequestCustomRest {

    private final Logger log = LoggerFactory.getLogger(ProcessRequestCustomRest.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRequestData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessRequestCustomService processRequestCustomService;

    private final ConcurrentProcessRequestCustomService concurrentProcessRequestCustomService;

    private final UserUtils userUtils;

    public ProcessRequestCustomRest(ProcessRequestCustomService processRequestCustomService, ConcurrentProcessRequestCustomService concurrentProcessRequestCustomService, UserUtils userUtils) {
        this.processRequestCustomService = processRequestCustomService;
        this.concurrentProcessRequestCustomService = concurrentProcessRequestCustomService;
        this.userUtils = userUtils;
    }

    @PostMapping("/process/request-data")
    public ResponseEntity<IResponseMessage> approve(@RequestBody ApproveOption approveOption) throws AuthorityException {
        if(!this.checkUser(approveOption.getUserId())){
            throw new AuthorityException(this.userUtils.MESS_NOT_AUTHORITY);
        }
        Boolean result = this.processRequestCustomService.actionRequestData(approveOption);
        if(!result) return ResponseEntity.ok().body(new FailLoadMessage(approveOption.getErrorMessageReturn()));
        return ResponseEntity.ok().body(new LoadedMessage(approveOption.getSuccessMessageReturn()));
    }

    @PostMapping("/process_concurrent/request-data")
    public ResponseEntity<IResponseMessage> concurrentApprove(@RequestBody ConcurrentApproveOption concurrentApproveOption) throws AuthorityException {
        if(!this.checkUser(concurrentApproveOption.getUserId())){
            throw new AuthorityException(this.userUtils.MESS_NOT_AUTHORITY);
        }
        Map<Long, Boolean> result = this.concurrentProcessRequestCustomService.concurrentActionRequest(concurrentApproveOption);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/resend_mail/request-data")
    public ResponseEntity<IResponseMessage> resendMail(@RequestBody ReSendOption reSendOption) throws AuthorityException {
       if(!this.checkUser(reSendOption.getUserId())){
           throw new AuthorityException(this.userUtils.MESS_NOT_AUTHORITY);
       }
       Boolean result = this.processRequestCustomService.resendMail(reSendOption);
       if(result)
           return ResponseEntity.ok().body(new LoadedMessage(result));
       else
           return ResponseEntity.ok().body(new FailLoadMessage(result));
    }

    @GetMapping("/check_resend_mail/{requestDataId}/request-data")
    public ResponseEntity<IResponseMessage> checkReSendMail(@PathVariable("requestDataId") Long requestDataId) throws AuthorityException {
        MyUserDetail currentUser = this.userUtils.getCurrentUser();
        if(currentUser == null){
            throw new AuthorityException(this.userUtils.MESS_NOT_AUTHORITY);
        }
        Boolean result = this.processRequestCustomService.checkReSendMail(requestDataId, currentUser.getId());
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    private boolean checkUser(Long userId){
        MyUserDetail currentUser = this.userUtils.getCurrentUser();
        if(userId != null && currentUser != null){
            return userId.equals(currentUser.getId());
        }
        return false;
    }
}
