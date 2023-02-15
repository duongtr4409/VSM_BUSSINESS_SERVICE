package com.vsm.business.web.rest.custom.mail;

import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.service.custom.mail.MailCustomService;
import com.vsm.business.service.custom.mail.bo.MailInfoDTO;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@RestController
@RequestMapping("/api/mail")
public class MailCustomRest {

    private final Logger log = LoggerFactory.getLogger(MailCustomRest.class);

    @Value("${upload.file.file-extension}")
    private String[] FILE_EXTENSION;

    private final String FILE_TYPE_EXCEPTION_MESS = "File Type Not Support !!!";

    private final MailCustomService mailCustomService;

    public MailCustomRest(MailCustomService mailCustomService){
        this.mailCustomService = mailCustomService;
    }

    @PostMapping("/send-mail")
    public ResponseEntity<IResponseMessage> sendMail(@RequestParam(value = "files", required = false)MultipartFile files[], MailInfoDTO mailInfoDTO) throws Exception {

        if(files != null){
            for (MultipartFile file : files) {
                String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
                String finalFileExtension = fileExtension.toLowerCase();
                if(!Arrays.stream(this.FILE_EXTENSION).anyMatch(ele -> finalFileExtension.equalsIgnoreCase(ele))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.FILE_TYPE_EXCEPTION_MESS);
            }
        }

        IResponseMessage result = mailCustomService.sendMail(files, mailInfoDTO);
//        IResponseMessage result = mailCustomService.sendMailOffice365(files, mailInfoDTO);
        log.debug("MailCustomRest: sendMail({}, {}): {}", files, mailInfoDTO, result);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/send-mail-365")
    public ResponseEntity<IResponseMessage> sendMail365(@RequestParam(value = "files", required = false)MultipartFile files[], MailInfoDTO mailInfoDTO) throws Exception {
//        IResponseMessage result = mailCustomService.sendMail(files, mailInfoDTO);
        if(files != null){
            for (MultipartFile file : files) {
                String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
                String finalFileExtension = fileExtension.toLowerCase();
                if(!Arrays.stream(this.FILE_EXTENSION).anyMatch(ele -> finalFileExtension.equalsIgnoreCase(ele))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.FILE_TYPE_EXCEPTION_MESS);
            }
        }

        IResponseMessage result = mailCustomService.sendMailOffice365(files, mailInfoDTO);
        log.debug("MailCustomRest: sendMail365({}, {}): {}", files, mailInfoDTO, result);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/send-mail-with-template/{requestDataId}")
    public ResponseEntity<IResponseMessage> sendMailWithTemplate(@PathVariable("requestDataId") Long requestDataId, MailInfoDTO mailInfoDTO) throws Exception {
        IResponseMessage result = mailCustomService.sendMailWithTemplate(mailInfoDTO, requestDataId);
        log.debug("MailCustomRest: sendMail({}, {}): {}", requestDataId, mailInfoDTO, result);
        return ResponseEntity.ok().body(result);
    }
}
