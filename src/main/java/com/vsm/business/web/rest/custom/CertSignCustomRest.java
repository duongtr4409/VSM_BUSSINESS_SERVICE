package com.vsm.business.web.rest.custom;

import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.Sign.CertSign;
import com.vsm.business.common.Sign.Itext7.ItextSignService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/api/cert")
public class CertSignCustomRest {
    private final CertSign certSign;
    private final ItextSignService itextSignService;

    public CertSignCustomRest(CertSign certSign, ItextSignService itextSignService) {
        this.certSign = certSign;
        this.itextSignService = itextSignService;
    }

    @GetMapping("/sign")
    public ResponseEntity<IResponseMessage> sign() throws GeneralSecurityException, IOException {
//        certSign.sign();
        return ResponseEntity.ok().body(new LoadedMessage(""));
    }

    @GetMapping("/itext7/sign")
    public ResponseEntity<IResponseMessage> itext7_sign() throws GeneralSecurityException, IOException {
        itextSignService.sign();
        return ResponseEntity.ok().body(new LoadedMessage(""));
    }
}
