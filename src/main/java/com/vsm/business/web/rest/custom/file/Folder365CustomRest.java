package com.vsm.business.web.rest.custom.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.domain.AttachmentFile;
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.service.custom.file.Folder365CustomService;
import com.vsm.business.service.custom.file.UploadFile365CustomService;
import com.vsm.business.service.custom.file.bo.Office365.CreateFolder365Option;
import com.vsm.business.service.custom.file.bo.Office365.Edit365Option;
import com.vsm.business.service.dto.AttachmentFileDTO;
import com.vsm.business.service.mapper.AttachmentFileMapper;
import com.vsm.business.utils.AuthenticateUtils;
import com.vsm.business.utils.PermissionFileUtils;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/folder/office365")
public class Folder365CustomRest {

    private final Logger log = LoggerFactory.getLogger(FolderCustomRest.class);

    private Folder365CustomService folder365CustomService;

    private AuthenticateUtils authenticateUtils;

    private PermissionFileUtils permissionFileUtils;

    private AttachmentFileRepository attachmentFileRepository;

    private UploadFile365CustomService uploadFile365CustomService;

    private AttachmentFileMapper attachmentFileMapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    public Folder365CustomRest(Folder365CustomService folder365CustomService, AuthenticateUtils authenticateUtils, PermissionFileUtils permissionFileUtils, AttachmentFileRepository attachmentFileRepository, UploadFile365CustomService uploadFile365CustomService, AttachmentFileMapper attachmentFileMapper) {
        this.folder365CustomService = folder365CustomService;
        this.authenticateUtils = authenticateUtils;
        this.permissionFileUtils = permissionFileUtils;
        this.attachmentFileRepository = attachmentFileRepository;
        this.uploadFile365CustomService = uploadFile365CustomService;
        this.attachmentFileMapper = attachmentFileMapper;
    }

    @PostMapping("/")
    public ResponseEntity<IResponseMessage> createFolder(@RequestBody CreateFolder365Option createFolder365Option) throws Exception {

        // kiểm tra người dùng có quyền ADMIN không \\ api đặc thù -> viết riêng
        if(!this.authenticateUtils.checkPermisionADMIN()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        // 14/11/2022: kiểm tra thêm người dùng ADMIN này có quyền tạo với Folder cha hay không
        Long attachmentFileIdParent = this.uploadFile365CustomService.getROOT_FOLDER().getParentId();
        if(createFolder365Option.getParentId() != null){
            attachmentFileIdParent = createFolder365Option.getParentId();
        }else{
            if(!Strings.isNullOrEmpty(createFolder365Option.getParentItemId())){
                List<AttachmentFile> attachmentFileListParent = this.attachmentFileRepository.findAllByIdInFileService(createFolder365Option.getParentItemId());
                if(attachmentFileListParent != null && !attachmentFileListParent.isEmpty()){
                    attachmentFileIdParent = attachmentFileListParent.get(0).getId();
                }
            }
        }
        if(!this.permissionFileUtils.checkPermisstionToFile(attachmentFileIdParent, PermissionFileUtils.EDIT)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }


        AttachmentFileDTO result = this.folder365CustomService.createFolder(createFolder365Option);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IResponseMessage> showFolderContent(@PathVariable("id") Long id) throws Exception {

        // kiểm tra người dùng có quyền ADMIN không \\ api đặc thù -> viết riêng
        if(!this.authenticateUtils.checkPermisionADMIN()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        // 14/11/2022: kiểm tra thêm người dùng ADMIN này có quyền xem với Folder cha hay không (API này nếu tham số truyền lên là id của ROOT -> cho qua)
        if(!id.equals(this.uploadFile365CustomService.getROOT_FOLDER().getId())){
            if(!this.permissionFileUtils.checkPermisstionToFile(id, PermissionFileUtils.VIEW)
                && !this.permissionFileUtils.chechPermisitionViewFile(id))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }

        List<AttachmentFileDTO> result = this.folder365CustomService.showFolderContent(id);
        return  ResponseEntity.ok().body(new LoadedMessage(this.filterWithRole(result, id)));
    }

    @GetMapping("/child/{id}")
    public ResponseEntity<IResponseMessage> showChildFolder(@PathVariable("id") Long id) throws Exception {

        // kiểm tra người dùng có quyền ADMIN không \\ api đặc thù -> viết riêng
        if(!this.authenticateUtils.checkPermisionADMIN()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        // 14/11/2022: kiểm tra thêm người dùng ADMIN này có quyền xem với Folder cha hay không (API này nếu tham số truyền lên là id của ROOT -> cho qua)
        if(!id.equals(this.uploadFile365CustomService.getROOT_FOLDER().getId())) {
            if(!this.permissionFileUtils.checkPermisstionToFile(id, PermissionFileUtils.VIEW))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);
        }

        List<AttachmentFileDTO> result = this.folder365CustomService.showFolderContent(id);
        return  ResponseEntity.ok().body(new LoadedMessage(result));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<IResponseMessage> deleteFolder(@PathVariable("id") Long id) throws Exception {

        // kiểm tra người dùng có quyền ADMIN không \\ api đặc thù -> viết riêng
        if(!this.authenticateUtils.checkPermisionADMIN()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        // 14/11/2022: kiểm tra thêm người dùng ADMIN này có quyền xóa với Folder(File) hay không
        if(!this.permissionFileUtils.checkPermisstionToFile(id, PermissionFileUtils.DELETE))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        this.folder365CustomService.deleteFolder(id);
        return ResponseEntity.ok().body(new LoadedMessage(true));
    }

    @GetMapping("/restore/{id}")
    public ResponseEntity<IResponseMessage> restoreFolder(@PathVariable("id") Long id) throws Exception {

        // kiểm tra người dùng có quyền ADMIN không \\ api đặc thù -> viết riêng
        if(!this.authenticateUtils.checkPermisionADMIN()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        this.folder365CustomService.restoreFolder(id);
        return ResponseEntity.ok().body(new LoadedMessage(true));
    }

    @PostMapping("/move")
    public ResponseEntity<IResponseMessage> moveFolder(@RequestBody Edit365Option edit365Option){
        return ResponseEntity.ok().body(new LoadedMessage(this.folder365CustomService.moveFolder(edit365Option)));
    }

    @PostMapping("/copy")
    public ResponseEntity<IResponseMessage> copyFolder(@RequestBody Edit365Option edit365Option) throws Exception {
        return ResponseEntity.ok(new LoadedMessage(this.folder365CustomService.copyFolder(edit365Option)));
    }

    @PutMapping("/rename/{id}")
    public ResponseEntity<IResponseMessage> renameFile(@PathVariable("id") Long id, @RequestParam("folderName") String folderName) throws Exception {
        this.folder365CustomService.reNameFolder(id, folderName);
        return ResponseEntity.ok().body(new LoadedMessage(true));
    }

    @GetMapping("/root/folder")
    public ResponseEntity<IResponseMessage> getRootFolder(){

        // kiểm tra người dùng có quyền ADMIN không \\ api đặc thù -> viết riêng
        if(!this.authenticateUtils.checkPermisionADMIN()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, this.authenticateUtils.NOT_AUTHORITY_MESS);

        AttachmentFileDTO rootFolder = this.folder365CustomService.getRootFolder();
        return ResponseEntity.ok().body(new LoadedMessage(rootFolder));
    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<IResponseMessage> renameFolder(@PathVariable("id") String id, @RequestParam("folderName") String folderName) throws Exception {
//        BaseClientRp result = this.folderCustomService.renameFolder(id, folderName);
//        if(!result.getState()){
//            throw new Exception(this.objectMapper.writeValueAsString(result));
//        }
//        return ResponseEntity.ok().body(new LoadedMessage(result.getData()));
//    }


    private List<AttachmentFileDTO> filterWithRole(List<AttachmentFileDTO> listSource, Long parentId){
        if(listSource.stream().anyMatch(ele -> ele.getParentId().equals(this.uploadFile365CustomService.getROOT_FOLDER().getId()))){    // nếu có từ ROOT rồi -> filter luôn
            List<Long> attachmentHasPermision = this.permissionFileUtils.getAllFileHasPermisition(PermissionFileUtils.VIEW).stream().map(ele -> ele.getId()).collect(Collectors.toList());        // dánh sách file mà user có quyền
            if(attachmentHasPermision.stream().anyMatch(ele -> ele.equals(this.uploadFile365CustomService.getROOT_FOLDER().getId())))       // nếu có quyền với ROOT -> trả về toàn bộ
                return listSource;
        }
        List<AttachmentFile> attachmentHasPermision = this.permissionFileUtils.getAllFileHasPermisition(PermissionFileUtils.VIEW);
        if(attachmentHasPermision.stream().map(ele -> ele.getId()).anyMatch(ele -> ele.equals(parentId)) || this.permissionFileUtils.checkPermisstionToFile(parentId, PermissionFileUtils.VIEW)){          // TH nếu có quyền với folder parentId -> trả về toàn bộ
            return listSource;
        }else {     // TH ko có quyền với folder parentId -> chỉ trả về các folder được phân quyền  (TH bị phần quyền ở những cấp nhỏ -> cần phải cho xem cha (ông, ...) của nó)
            Set<AttachmentFileDTO> listResult = new HashSet<>();
            int n = attachmentHasPermision.size();
            for(int i=0; i<n; i++){
                try {
                    AttachmentFile attachmentFileHasRoleByChild = this.attachmentFileRepository.getAllParent(attachmentHasPermision.get(i).getId(), parentId).get();            //attachmentFileHasRoleByChild: folder được cho xem vì user được cấp quyền ở con (cháu) của nó
                    listResult.add(this.attachmentFileMapper.toDto(attachmentFileHasRoleByChild));
                    try{listResult.add(this.attachmentFileMapper.toDto(this.attachmentFileRepository.getAllParent(attachmentHasPermision.get(i).getId(), attachmentFileHasRoleByChild.getId()).get()));}catch (Exception e){}             // lấy thêm con trực tiếp của folder này nếu folder này ko được phân quyền trực tiếp mà lấy quyền từ con (cháu của nó)
                    listResult.addAll(this.attachmentFileRepository.findAllByParentId(attachmentHasPermision.get(i).getId()).stream().map(ele -> this.attachmentFileMapper.toDto(ele)).collect(Collectors.toSet()));            // cần thêm cả con trực tiếp của nó để frontend xử lý
                }catch (Exception e){
                    log.error("{}", e);
                }
            }
            return listResult.stream().collect(Collectors.toList());
        }
    }

}
