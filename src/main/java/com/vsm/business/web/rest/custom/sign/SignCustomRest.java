package com.vsm.business.web.rest.custom.sign;

import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.domain.AttachmentFile;
import com.vsm.business.service.auth.bo.MyUserDetail;
import com.vsm.business.web.rest.errors.bo.exception.AuthorityException;
import com.vsm.business.service.custom.sign.SimSign;
import com.vsm.business.service.custom.sign.SoftSign;
import com.vsm.business.service.custom.sign.TokenSign;
import com.vsm.business.service.custom.sign.bo.SignDTO;
import com.vsm.business.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sign")
public class SignCustomRest {

    private final Logger log = LoggerFactory.getLogger(SignCustomRest.class);

    private final String SIGN_SUCCESS_MSG = "Ký thành công.";

    private final String SIGN_ERROR_MSG = "Ký thất bại";

    private final SoftSign softSign;

    private final SimSign simSign;

    private final TokenSign tokenSign;

    private final UserUtils userUtils;

    public SignCustomRest(SoftSign softSign, SimSign simSign, TokenSign tokenSign, UserUtils userUtils) {
        this.softSign = softSign;
        this.simSign = simSign;
        this.tokenSign = tokenSign;
        this.userUtils = userUtils;
    }

    private final String NO_TYPE_SIGN_ERROR = "No Type Sign";

    @PostMapping("/")
    public ResponseEntity<IResponseMessage> signOne(@RequestBody SignDTO signDTO) throws Exception {
        if(!this.checkUser(signDTO.getUserId())){
            throw new AuthorityException(this.userUtils.MESS_NOT_AUTHORITY);
        }
        Object result = false;
        switch (signDTO.getSignType()){
            case Sim:
                result = simSign.signOne(signDTO);
                break;
            case Soft:
                result = softSign.signOne(signDTO);
                break;
            case Token:
                result = tokenSign.signOne(signDTO);
                break;
            default:
                throw new RuntimeException(this.NO_TYPE_SIGN_ERROR);
        }
        if(result instanceof Boolean && !(Boolean) result)
            return ResponseEntity.ok().body(new FailLoadMessage(result));
        else
            return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/anonymous")
    public ResponseEntity<IResponseMessage> anonymouseSign(@RequestBody SignDTO signDTO) throws Exception {
        Object result = false;
        switch (signDTO.getSignType()){
            case Sim:
                result = simSign.anonymousSignOne(signDTO, null);
                break;
            case Soft:
                result = softSign.signOne(signDTO);
                break;
            case Token:
                result = tokenSign.anonymousSign(signDTO, null);
                break;
            default:
                throw new RuntimeException(this.NO_TYPE_SIGN_ERROR);
        }
        if(result instanceof Boolean && !(Boolean) result)
            return ResponseEntity.ok().body(new FailLoadMessage(result));
        else
            return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/all")
    public ResponseEntity<IResponseMessage> signAll(@RequestBody SignDTO signDTO) throws Exception {
        if(!this.checkUser(signDTO.getUserId())){
            throw new AuthorityException(this.userUtils.MESS_NOT_AUTHORITY);
        }
        Object result = false;
        switch (signDTO.getSignType()){
            case Sim:
                result = simSign.sign(signDTO);
                break;
            case Soft:
                result = softSign.sign(signDTO);
                break;
            case Token:
                result = tokenSign.sign(signDTO);
                break;
            default:
                throw new RuntimeException(this.NO_TYPE_SIGN_ERROR);
        }
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_all/softSigns")
    public ResponseEntity<IResponseMessage> getAllSoftSignData(){
        List<String> result = softSign.getAllSoftSign();
        log.debug("SignCustomRest: getAllSoftSignData(): {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/_all/{requestDataId}/file_sign")
    public ResponseEntity<IResponseMessage> getAllFileSign(@PathVariable("requestDataId") Long requestDataId){
        List<AttachmentFile> result = this.tokenSign.getAllFileSign(requestDataId);
        log.debug("SignCustomRest: getAllFileSign({}): {}", requestDataId, result);
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
