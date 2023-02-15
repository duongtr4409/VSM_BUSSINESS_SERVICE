package com.vsm.business.web.rest.custom;

import com.microsoft.graph.models.Permission;
import com.microsoft.graph.requests.PermissionGrantCollectionPage;
import com.microsoft.graph.requests.PermissionGrantCollectionRequestBuilder;
import com.vsm.business.common.Graph.GraphService;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.domain.AttachmentFile;
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.service.custom.mail.MailSchedule;
import com.vsm.business.service.dto.AttachmentFileDTO;
import com.vsm.business.service.mapper.AttachmentFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/test")
public class wTestController {

    private final GraphService graphService;

    private final MailSchedule mailSchedule;

    private final AttachmentFileRepository attachmentFileRepository;

    private final AttachmentFileMapper attachmentFileMapper;

    public wTestController(GraphService graphService, MailSchedule mailSchedule, AttachmentFileRepository attachmentFileRepository, AttachmentFileMapper attachmentFileMapper) {
        this.graphService = graphService;
        this.mailSchedule = mailSchedule;
        this.attachmentFileRepository = attachmentFileRepository;
        this.attachmentFileMapper = attachmentFileMapper;
    }

    @PostMapping("/duowngTora")
    public ResponseEntity<IResponseMessage> testPermisionOfFile(@RequestParam("link") String link, @RequestBody List<String> listEmail) throws UnsupportedEncodingException {
        PermissionGrantCollectionPage permissionGrantCollectionPage = this.graphService.grantAccessToUser(listEmail, link, "");
        List<Permission> result = new ArrayList<>();
        do{
            List<Permission> currentPage = permissionGrantCollectionPage.getCurrentPage();
            result.addAll(currentPage);
            PermissionGrantCollectionRequestBuilder nextPage = permissionGrantCollectionPage.getNextPage();
            permissionGrantCollectionPage = nextPage == null ? null : nextPage.buildRequest().getHttpRequest();
        }while (permissionGrantCollectionPage != null);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

//    @GetMapping("/duowngTora")
//    public ResponseEntity<IResponseMessage> testMailScheduled(){
//        mailSchedule.sendMailWarningOutOfDateV2();
//        return ResponseEntity.ok().body(new LoadedMessage("DuowngTora"));
//    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        AttachmentFile attachmentFile1 = this.attachmentFileRepository.findById(4896552L).get();
        AttachmentFile attachmentFile2 = this.attachmentFileRepository.findById(4896552L).get();
        System.out.println("AttachmentFile1: " + System.identityHashCode(attachmentFile1));
        System.out.println("AttachmentFile2: " + System.identityHashCode(attachmentFile2));
        System.out.println(attachmentFile1 == attachmentFile2);
        AttachmentFileDTO attachmentFileDTO1 = this.attachmentFileMapper.toDto(attachmentFile1);
        AttachmentFileDTO attachmentFileDTO2 = this.attachmentFileMapper.toDto(attachmentFile2);
        System.out.println("AttachmentFileDTO1: " + System.identityHashCode(attachmentFileDTO1));
        System.out.println("AttachmentFileDTO2: " + System.identityHashCode(attachmentFileDTO2));
        System.out.println(attachmentFileDTO1 == attachmentFileDTO2);
        List<AttachmentFile> list = new ArrayList<>();
        list.add(attachmentFile1);
        list.add(attachmentFile2);
        return ResponseEntity.ok().body("DuowngTora");
    }

}
