package com.vsm.business.web.rest.custom.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.service.custom.file.FolderCustomService;
import com.vsm.business.service.custom.file.bo.CreateFolderOption;
import com.vsm.business.service.custom.file.bo.response.BaseClientRp;
import com.vsm.business.service.dto.AttachmentFileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/folder")
public class FolderCustomRest {

    private final Logger log = LoggerFactory.getLogger(FolderCustomRest.class);

    private FolderCustomService folderCustomService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public FolderCustomRest(FolderCustomService folderCustomService) {
        this.folderCustomService = folderCustomService;
    }

    @PostMapping("/")
    public ResponseEntity<IResponseMessage> createFolder(@RequestBody CreateFolderOption createFolderOption) throws Exception {
//        BaseClientRp result = this.folderCustomService.createFolder(createFolderOption);
        BaseClientRp result = this.folderCustomService.createFolder_v1(createFolderOption);
        if(!result.getState())
            throw new Exception(objectMapper.writeValueAsString(result));
        return ResponseEntity.ok().body(new LoadedMessage(result.getData()));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<IResponseMessage> showFolderContent(@PathVariable("id") String id) throws Exception {
//        BaseClientRp result = this.folderCustomService.showFolderContent(id);
//        if(!result.getState())
//            throw new Exception(objectMapper.writeValueAsString(result));
//        return  ResponseEntity.ok().body(new LoadedMessage(result.getData()));
//    }
    @GetMapping("/{id}")
    public ResponseEntity<IResponseMessage> showFolderContent(@PathVariable("id") String id) throws Exception {
        List<AttachmentFileDTO> result = this.folderCustomService.showFolderContent_v1(id);
        return  ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<IResponseMessage> deleteFolder(@PathVariable("id") String id) throws Exception {
        BaseClientRp result = this.folderCustomService.deleteFolder(id);
        if(!result.getState()){
            throw new Exception(this.objectMapper.writeValueAsString(result));
        }
        return ResponseEntity.ok().body(new LoadedMessage(result.getData()));
    }

    @GetMapping("/restore/{id}")
    public ResponseEntity<IResponseMessage> restoreFolder(@PathVariable("id") String id) throws Exception {
        BaseClientRp result = this.folderCustomService.restoreFolder(id);
        if(!result.getState()){
            throw new Exception(this.objectMapper.writeValueAsString(result));
        }
        return ResponseEntity.ok().body(new LoadedMessage(result.getData()));
    }

    @PostMapping("/copy{id}")
    public ResponseEntity<IResponseMessage> copyFolder(@PathVariable("id") String id, @RequestParam("targerId") String targerId) throws Exception {
        BaseClientRp result = this.folderCustomService.copyFolder(id, targerId);
        if(!result.getState())
            throw new Exception(this.objectMapper.writeValueAsString(result));
        return ResponseEntity.ok().body(new LoadedMessage(result.getData()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IResponseMessage> renameFolder(@PathVariable("id") String id, @RequestParam("folderName") String folderName) throws Exception {
        BaseClientRp result = this.folderCustomService.renameFolder(id, folderName);
        if(!result.getState()){
            throw new Exception(this.objectMapper.writeValueAsString(result));
        }
        return ResponseEntity.ok().body(new LoadedMessage(result.getData()));
    }

}
