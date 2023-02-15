package com.vsm.business.service.custom.mail;

import com.vsm.business.common.Graph.GraphService;
import com.vsm.business.repository.MailLogRepository;
import com.vsm.business.service.custom.mail.bo.MailInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class SendMailCustomService {

    private final MailLogRepository mailLogRepository;

    public SendMailCustomService(MailLogRepository mailLogRepository) {
        this.mailLogRepository = mailLogRepository;
    }

    @Autowired
    public GraphService graphService;

    public void sendMail(List<File> files, MailInfoDTO mailInfoDTO){
        this.graphService.sendMail(files, mailInfoDTO);
    }

}
