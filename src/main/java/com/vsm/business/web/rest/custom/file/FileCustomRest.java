package com.vsm.business.web.rest.custom.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsm.business.common.Graph.GraphService;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.service.custom.file.UploadFileCustomService;
import com.vsm.business.service.custom.file.bo.DownloadOption;
import com.vsm.business.service.custom.file.bo.FileRq;
import com.vsm.business.service.custom.file.bo.UploadOption;
import com.vsm.business.service.custom.file.bo.response.BaseClientRp;
import com.vsm.business.service.dto.AttachmentFileDTO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;

@RestController
@RequestMapping("/api/file")
public class FileCustomRest {

    private final Logger log = LoggerFactory.getLogger(FileCustomRest.class);

    @Value("${upload.file.file-extension}")
    private String[] FILE_EXTENSION;

    private final String FILE_TYPE_EXCEPTION_MESS = "File Type Not Support !!!";

    private UploadFileCustomService uploadFileCustomService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public FileCustomRest(UploadFileCustomService uploadFileCustomService) {
        this.uploadFileCustomService = uploadFileCustomService;
    }

    @PostMapping("/upload")
    public ResponseEntity<IResponseMessage> uploadFile(@RequestParam("file")MultipartFile file, @RequestParam(value = "templateFormId", required = false) Long templateFormId, UploadOption option) throws Exception {
//        AttachmentFileDTO result = this.uploadFileCustomService.upLoadFile(file, templateFormId, option);
//        log.info("FileCustomRest: uploadFile(file: {}, TemplateFormDTO {}, option: {})", file, templateFormId, option);
//        return ResponseEntity.ok().body(new LoadedMessage(result));


        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String finalFileExtension = fileExtension.toLowerCase();
        if(!Arrays.stream(this.FILE_EXTENSION).anyMatch(ele -> finalFileExtension.equalsIgnoreCase(ele))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.FILE_TYPE_EXCEPTION_MESS);

        AttachmentFileDTO result = this.uploadFileCustomService.upLoadFile(file, templateFormId, option);
        log.info("FileCustomRest: uploadFile(file: {}, TemplateFormDTO {}, option: {})", file, templateFormId, option);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    //@PostMapping("/v1_download/")
//    public void downloadFile_v1(HttpServletResponse response, @RequestBody AttachmentFileDTO attachmentFileDTO, DownloadOption downloadOption) throws Exception {
//        byte[] result = this.uploadFileCustomService.downloadFile_v1(attachmentFileDTO, downloadOption);
//        response.getOutputStream().write(result);
//    }

    @GetMapping("/download/{fileNameId}/{fileName}.{fileExtension}")
    public void downloadFile(HttpServletResponse response, @PathVariable("fileNameId") String fileNameId, @PathVariable("fileName") String fileName , @PathVariable("fileExtension") String fileExtension, DownloadOption downloadOption) throws Exception {
        this.uploadFileCustomService.downloadFile(response, fileNameId, downloadOption);
    }

    @GetMapping("/download/{fileNameId}.{fileExtension}")
    public void downloadFile_v1(HttpServletResponse response, @PathVariable("fileNameId") String fileNameId, @PathVariable("fileExtension") String fileExtension, DownloadOption downloadOption) throws Exception {
        this.uploadFileCustomService.downloadFile(response, fileNameId, downloadOption);
    }

    @GetMapping("/v1/download/{fileNameId}")
    public void downloadFile_v2(HttpServletResponse response, @PathVariable("fileNameId") String fileNameId, DownloadOption downloadOption) throws Exception {
        this.uploadFileCustomService.downloadFile_v1(response, fileNameId, downloadOption);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<IResponseMessage> deleteFile(@PathVariable("id") String id) throws Exception {
        BaseClientRp result = this.uploadFileCustomService.deleteFile(id);
        if(!result.getState())
            throw new Exception(objectMapper.writeValueAsString(result));
        return ResponseEntity.ok().body(new LoadedMessage(result.getData()));
    }

    @GetMapping("/restore/{id}")
    public ResponseEntity<IResponseMessage> restoreFile(@PathVariable("id") String id) throws Exception {
        BaseClientRp result = this.uploadFileCustomService.restoreFile(id);
        if(!result.getState())
            throw new Exception(objectMapper.writeValueAsString(result));
        return ResponseEntity.ok().body(new LoadedMessage(result.getData()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IResponseMessage> renameFile(@PathVariable("id") String id, @RequestParam("fileName") String fileName) throws Exception {
        BaseClientRp result = this.uploadFileCustomService.renameFile(id, fileName);
        if(!result.getState())
            throw new Exception(objectMapper.writeValueAsString(result));
        return ResponseEntity.ok().body(new LoadedMessage(result.getData()));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<IResponseMessage> detailFile(@PathVariable("id") String id) throws Exception {
        return  ResponseEntity.ok().body(new LoadedMessage(this.uploadFileCustomService.detailFile(id)));
    }

    @PostMapping("/copy-file")
    public ResponseEntity<IResponseMessage> copyFile(@RequestBody FileRq fileRq) throws Exception {
        return ResponseEntity.ok().body(new LoadedMessage(this.uploadFileCustomService.copyFile(fileRq)));
    }

    @PostMapping("/move-file")
    public ResponseEntity<IResponseMessage> moveFile(@RequestBody FileRq fileRq) throws Exception {
        return ResponseEntity.ok().body(new LoadedMessage(this.uploadFileCustomService.moveFile(fileRq)));
    }

    @PutMapping("/update-file/{id}")
    public ResponseEntity<IResponseMessage> updateFile(@PathVariable("id") String id, String fileName, @RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok().body(new LoadedMessage(this.uploadFileCustomService.updateFile(id, fileName, file)));
    }

    @GetMapping("/download-qrcode/{fileNameId}")
    public void getFileWithQrCode(@PathVariable("fileNameId") String fileNameId, HttpServletResponse response, DownloadOption downloadOption) throws Exception {
        this.uploadFileCustomService.getFileWithQRCode(response, fileNameId, downloadOption);
    }

    @Autowired
    public GraphService graphService;
    @GetMapping("/office-365/test_v3/{id}")
    public void test_V3(HttpServletResponse response, @PathVariable("id") Long id) throws Exception {
        //this.graphService.downLoadFilePDF(response, id);
    }

    @GetMapping("/office-365/test_v4/{id}")
    public void test_V4 (HttpServletResponse response, @PathVariable("id") String id) throws Exception {
        this.graphService.getFilePDF_v4(response, id);
    }

    @GetMapping("/office-365/test_v4/delete/{id}")
    public void test_v4_delete(HttpServletResponse response, @PathVariable("id") String id, @RequestParam("format") String format, @RequestParam("fileName") String fileName) throws Exception {
        File file = new File("exceltest_v1.xlsx");
        String itemId = this.graphService.createFile(file.getName(), id, FileUtils.readFileToByteArray(file)).responseBody.id;
        InputStream inputStream = this.graphService.getFile(itemId, format);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName +"\"");
        DownloadOption downloadOption = new DownloadOption();
        downloadOption.setAddQrCode(true);
        inputStream = this.uploadFileCustomService.getFileContentWithOption(inputStream, downloadOption);
        response.getOutputStream().write(IOUtils.toByteArray(inputStream));
    }

}
