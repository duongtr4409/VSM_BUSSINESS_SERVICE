package com.vsm.business.web.rest.custom.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.graph.models.ItemPreviewInfo;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.domain.AttachmentFile;
import com.vsm.business.domain.Request;
import com.vsm.business.domain.RequestData;
import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.service.auth.bo.MyUserDetail;
import com.vsm.business.service.custom.file.UploadFile365CustomService;
import com.vsm.business.service.custom.file.bo.Office365.Download365Option;
import com.vsm.business.service.custom.file.bo.Office365.Edit365Option;
import com.vsm.business.service.custom.file.bo.Office365.HashFileOption;
import com.vsm.business.service.custom.file.bo.Office365.Upload365Option;
import com.vsm.business.service.dto.AttachmentFileDTO;
import com.vsm.business.utils.AuthenticateUtils;
import com.vsm.business.utils.PermissionFileUtils;
import com.vsm.business.utils.UserUtils;
import org.apache.commons.io.FilenameUtils;
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
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/file/office365")
public class File365CustomRest {

    private final Logger log = LoggerFactory.getLogger(FileCustomRest.class);

    @Value("${role-request.check-role:TRUE}")
    public String CHECK_ROLE;

    @Value("${upload.file.file-extension}")
    private String[] FILE_EXTENSION;

    private final String FILE_TYPE_EXCEPTION_MESS = "File Type Not Support !!!";

    private UploadFile365CustomService uploadFile365CustomService;

    private AttachmentFileRepository attachmentFileRepository;

    private AuthenticateUtils authenticateUtils;

    private PermissionFileUtils permissionFileUtils;

    private ObjectMapper objectMapper = new ObjectMapper();

    public File365CustomRest(UploadFile365CustomService uploadFile365CustomService, AttachmentFileRepository attachmentFileRepository, AuthenticateUtils authenticateUtils, PermissionFileUtils permissionFileUtils) {
        this.uploadFile365CustomService = uploadFile365CustomService;
        this.attachmentFileRepository = attachmentFileRepository;
        this.authenticateUtils = authenticateUtils;
        this.permissionFileUtils = permissionFileUtils;
    }

    @PostMapping("/upload")
    public ResponseEntity<IResponseMessage> uploadFile(@RequestParam("file")MultipartFile file, Upload365Option upload365Option) throws Exception {

        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String finalFileExtension = fileExtension.toLowerCase();
        if(!Arrays.stream(this.FILE_EXTENSION).anyMatch(ele -> finalFileExtension.equalsIgnoreCase(ele))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.FILE_TYPE_EXCEPTION_MESS);

        // kiểm tra xem người dùng có được upload file vào trong phiếu yêu cầu hay không \\ api đặc thù -> viết riêng
//        if(upload365Option.getRequestDataId() != null){
//            if(!this.authenticateUtils.checkPermisionForDataOfUser(upload365Option.getRequestDataId()))
//                throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
//        }
        // update 15/11/2022: cập nhật thêm nếu được phân quyền thì sẽ được quyền upload file
        Long parentId = upload365Option.getParentId();
        if(parentId == null){
            if(upload365Option.getParentItemId() != null && upload365Option.getParentItemId().contains("\""))
                upload365Option.setParentItemId(upload365Option.getParentItemId().replace("\"", ""));
            if(upload365Option.getParentItemId() != null)
                parentId = this.attachmentFileRepository.findAllByItemId365(upload365Option.getParentItemId()).get(0).getId();
        }
        if(upload365Option.getRequestDataId() != null){
            if(!this.authenticateUtils.checkPermisionForDataOfUser(upload365Option.getRequestDataId()) && !this.permissionFileUtils.checkPermisstionToFile(parentId, PermissionFileUtils.EDIT))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }else{
            if(!this.permissionFileUtils.checkPermisstionToFile(parentId, PermissionFileUtils.EDIT))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }

        AttachmentFileDTO result = this.uploadFile365CustomService.uploadFile(file, upload365Option);
        log.info("FileCustomRest: uploadFile(file: {}, Upload365Option: {}): {}", file, upload365Option, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/download/{fileName}.{fileExtension}")
    public void downloadFile(HttpServletResponse response, @PathVariable("fileName") String fileName , @PathVariable("fileExtension") String fileExtension, Download365Option download365Option) throws Exception {

        // kiểm tra người có quyền download file này hay không
        AttachmentFile attachmentFile = this.attachmentFileRepository.findById(download365Option.getAttachmentFileId()).orElse(null);
        if(attachmentFile == null) {
            attachmentFile = this.attachmentFileRepository.findAllByItemId365(download365Option.getItemId()).get(0);
        }
        if(attachmentFile.getRequestData() != null) {
            if(!this.authenticateUtils.checkPermisionForDataOfUser(attachmentFile.getRequestData().getId())
                && (!this.authenticateUtils.checkPermisionForDataStatisticToUser(attachmentFile.getRequestData().getId()))
                && (!CHECK_ROLE.equalsIgnoreCase("FALSE") && !this.checkPermissionDownLoadFile(attachmentFile.getRequestData())))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }else{  // nếu không có requestData -> là dữ liệu phía quản trị -> cần có quyền ADMIN
            if(!this.authenticateUtils.checkPermisionADMIN())
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }

        this.uploadFile365CustomService.downloadFile(response, fileName + "." + fileExtension, download365Option);
    }

//    @GetMapping("/download/{fileNameId}.{fileExtension}")
//    public void downloadFile_v1(HttpServletResponse response, @PathVariable("fileNameId") String fileNameId, @PathVariable("fileExtension") String fileExtension, DownloadOption downloadOption) throws Exception {
//        this.uploadFileCustomService.downloadFile(response, fileNameId, downloadOption);
//    }
//
//    @GetMapping("/v1/download/{fileNameId}")
//    public void downloadFile_v2(HttpServletResponse response, @PathVariable("fileNameId") String fileNameId, DownloadOption downloadOption) throws Exception {
//        this.uploadFileCustomService.downloadFile_v1(response, fileNameId, downloadOption);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<IResponseMessage> deleteFile(@PathVariable("id") Long id) throws Exception {

        // update 15/11/2022: cập nhật thêm nếu được phân quyền thì sẽ được quyền delete file
        if(!this.permissionFileUtils.checkPermisstionToFile(id, PermissionFileUtils.DELETE))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        this.uploadFile365CustomService.deleteFile(id);
        return ResponseEntity.ok().body(new LoadedMessage(true));
    }

    @GetMapping("/restore/{id}")
    public ResponseEntity<IResponseMessage> restoreFile(@PathVariable("id") Long id) throws Exception {
        this.uploadFile365CustomService.restoreFile(id);
        return ResponseEntity.ok().body(new LoadedMessage(true));
    }

    @PutMapping("/rename/{id}")
    public ResponseEntity<IResponseMessage> renameFile(@PathVariable("id") Long id, @RequestParam("fileName") String fileName) throws Exception {
        this.uploadFile365CustomService.renameFile(id, fileName);
        return ResponseEntity.ok().body(new LoadedMessage(true));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<IResponseMessage> detailFile(@PathVariable("id") Long id) throws Exception {
        return  ResponseEntity.ok().body(new LoadedMessage(this.uploadFile365CustomService.detailFile(id)));
    }

    @PostMapping("/copy-file")
    public ResponseEntity<IResponseMessage> copyFile(@RequestBody Edit365Option edit365Option) throws Exception {
        return ResponseEntity.ok().body(new LoadedMessage(this.uploadFile365CustomService.copyFile(edit365Option)));
    }

    @PostMapping("/move-file")
    public ResponseEntity<IResponseMessage> moveFile(@RequestBody Edit365Option edit365Option){
        return ResponseEntity.ok().body(new LoadedMessage(this.uploadFile365CustomService.moveFile(edit365Option)));
    }

    @GetMapping("/preview/{id}")
    public ResponseEntity<IResponseMessage> previewFile(@PathVariable("id") Long id){

        // kiểm tra người dùng có quyền xem dữ liệu của file không \\ API đặc thù -> viết riêng
        AttachmentFile attachmentFile = this.attachmentFileRepository.findById(id).get();
//        if(!this.authenticateUtils.checkPermisionForDataOfUser(attachmentFile.getRequestData() != null ? attachmentFile.getRequestData().getId() : null))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        // udpate 15/11/2022: cập nhật thêm nếu được phân quyền thì sẽ được quyền xem file  + 21/11/2022: cập nhật thêm nếu được phân quyền thống kê cũng được xem file
        if(!this.authenticateUtils.checkPermisionForDataOfUser(attachmentFile.getRequestData() != null ? attachmentFile.getRequestData().getId() : null)
            && !this.permissionFileUtils.checkPermisstionToFile(id, PermissionFileUtils.VIEW)
            && !this.authenticateUtils.checkPermisionForDataStatisticToUser(attachmentFile.getRequestData() == null ? null : attachmentFile.getRequestData().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }

        ItemPreviewInfo itemPreviewInfo = this.uploadFile365CustomService.previewFile(id);
        String urlPreview = itemPreviewInfo.getUrl;
        return ResponseEntity.ok().body(new LoadedMessage(urlPreview));
    }

    @GetMapping("/hash-file/{id}")
    public ResponseEntity<IResponseMessage> hashPDFFile(@PathVariable("id") Long id) throws Exception {
        String hashFile = this.uploadFile365CustomService.hashFile(id);
        return ResponseEntity.ok(new LoadedMessage(hashFile));
    }

    @PostMapping("/decode-file")
    public ResponseEntity<IResponseMessage> deCodeFile(@RequestBody HashFileOption hashFileOption) throws IOException {
        this.uploadFile365CustomService.deCodeFile(hashFileOption);
        return ResponseEntity.ok(new LoadedMessage(true));
    }

//    @PutMapping("/update-file/{id}")
//    public ResponseEntity<IResponseMessage> updateFile(@PathVariable("id") String id, String fileName, @RequestParam("file") MultipartFile file) throws Exception {
//        return ResponseEntity.ok().body(new LoadedMessage(this.uploadFileCustomService.updateFile(id, fileName, file)));
//    }
//
//    @GetMapping("/download-qrcode/{fileNameId}")
//    public void getFileWithQrCode(@PathVariable("fileNameId") String fileNameId, HttpServletResponse response, DownloadOption downloadOption) throws Exception {
//        this.uploadFileCustomService.getFileWithQRCode(response, fileNameId, downloadOption);
//    }
//
//    @Autowired
//    public GraphService graphService;
//    @GetMapping("/office-365/test_v3/{id}")
//    public void test_V3(HttpServletResponse response, @PathVariable("id") Long id) throws Exception {
//        this.graphService.downLoadFilePDF(response, id);
//    }
//
//    @GetMapping("/office-365/test_v4/{id}")
//    public void test_V4 (HttpServletResponse response, @PathVariable("id") String id) throws Exception {
//        this.graphService.getFilePDF_v4(response, id);
//    }
//
//    @GetMapping("/office-365/test_v4/delete/{id}")
//    public void test_v4_delete(HttpServletResponse response, @PathVariable("id") String id, @RequestParam("format") String format, @RequestParam("fileName") String fileName) throws Exception {
//        File file = new File("exceltest_v1.xlsx");
//        String itemId = this.graphService.createFile(file.getName(), id, FileUtils.readFileToByteArray(file)).responseBody.id;
//        InputStream inputStream = this.graphService.getFile(itemId, format);
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName +"\"");
//        DownloadOption downloadOption = new DownloadOption();
//        downloadOption.setAddQrCode(true);
//        inputStream = this.uploadFileCustomService.getFileContentWithOption(inputStream, downloadOption);
//        response.getOutputStream().write(IOUtils.toByteArray(inputStream));
//    }

    @Autowired
    public UserUtils userUtils;

    /**
     * Hàm thực hiện kiểm tra User hiện tại có quyền DOWNLOAD tài liệu của phiếu hiện tại không.
     * @param requestData   : thông tin phiếu yêu cầu
     * @return
     */
    private boolean checkPermissionDownLoadFile(RequestData requestData){
        Request request = requestData.getRequest();
        UserInfo created = requestData.getCreated();

        if(request == null) return false;           // phiếu không có loại yêu cầu -> false
        MyUserDetail currentUser = this.userUtils.getCurrentUser();
        if(currentUser == null) return false;       // không có thông tin đăng nhập -> false
        List<Long> requestIds = this.authenticateUtils.getRequestForUserWithHasAction(currentUser.getId(), AuthenticateUtils.DOWNLOAD);
        List<Long> organizationIds = this.authenticateUtils.getOrganizationForUserWithHasAction(currentUser.getId(), AuthenticateUtils.DOWNLOAD).stream().map(ele -> ele.getId()).collect(Collectors.toList());

        boolean result = true;
        // kiểm tra user có quyền DOWNLOAD của loại yêu cầu này không
        result = result && requestIds.stream().anyMatch(ele -> ele.equals(request.getId()));
        if(!result) return false;
        // kiểm tra user có quyền DOWNLOAD của phòng ban này không
        return result && created.getOrganizations().stream().map(ele -> ele.getId()).anyMatch(ele -> {
            return organizationIds.stream().anyMatch(ele1 -> ele1.equals(ele));
        });
    }
}
