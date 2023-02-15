package com.vsm.business.service.custom.dataroom;

import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.Site;
import com.microsoft.graph.tasks.LargeFileUploadResult;
import com.vsm.business.common.Graph.GraphService;
import com.vsm.business.domain.AttachmentFile;
import com.vsm.business.domain.RequestData;
import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.repository.UserInfoRepository;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataRoomCustomService {

    private final Logger log = LoggerFactory.getLogger(DataRoomCustomService.class);

    @Autowired
    private GraphService graphService;

    @Autowired
    private RequestDataRepository requestDataRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Value("${dr.default-sync.is-default:true}")
    public String IS_DEFAULT;

    public List<Site> getAllSite(){
        List<Site> result = this.graphService.getAllSite();
        log.debug("DataRoomCustomService: getAllSite(): {}", result);
        return result;
    }

    public List<Object> getAllDriverOfSite(String siteId){
        List<Object> result = this.graphService.getAllItemOfSite_v2(siteId);
        log.debug("DataRoomCustomService: getAllItemOfSite_v2({}): {}", siteId, result);
        return result;
    }


    // v2 \\
    public List<DriveItem> getAllItem(String itemId){
        List<DriveItem> result = this.graphService.getAllItem(itemId).stream().filter(ele -> ele.folder != null).collect(Collectors.toList());
        return result;
    }

    public List<DriveItem> getAllItemV2(String itemId){
        List<DriveItem> result = this.graphService.getAllItem(itemId).stream().filter(ele -> ele.folder != null).collect(Collectors.toList());
        return result;
    }

    public LargeFileUploadResult<DriveItem> syncFileV2(MultipartFile file, String itemId, String driveId) throws IOException {
        LargeFileUploadResult<DriveItem> uploadResult = this.graphService.createFileDrive(file.getOriginalFilename(), driveId, itemId, file.getBytes());
        log.debug("uploadResult: {}", uploadResult);
        return uploadResult;
    }


    public List<Object> getAllDirectoryObject(String siteId){
        List<Object> result = this.graphService.getAllDriectoryObject(siteId);
        log.debug("DataRoomCustomService: getAllDirectoryObject({}): {}", siteId, result);
        return result;
    }

    public List<DriveItem> getAllItemFormDrive(String driveId, String itemId){
        List<DriveItem> result = this.graphService.getAllItemFormDrive(driveId, itemId);
        log.debug("DataRoomCustomService: getAllItemFormDrive({}): {}", driveId, result);
        return result;
    }



    // đồng bộ sang Data-Room \\
    @Value("${graph-api.time-wait-copy:3000}")
    private String TIME_WAIT_COPY;
    public void syncToDataRoom(Long requestDataId) throws Exception {
        RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
        String targetItemId = requestData.getDataRoomId();
        String driveId = requestData.getDataRoomDriveId();
        if(!"TRUE".equalsIgnoreCase(IS_DEFAULT) && Strings.isNullOrEmpty(targetItemId) && Strings.isNullOrEmpty(driveId)) return;
        List<String> urlResultList = new ArrayList<>();
        List<AttachmentFile> attachmentFileListSync = new ArrayList<>();
        for(AttachmentFile attachmentFile : requestData.getAttachmentFiles()){
            if(attachmentFile.getTemplateForm() != null){   // nếu có TemplateForm -> là tài liệu chính -> cần đồng bộ
                String urlResult = this.graphService.copyFileDrive(attachmentFile.getItemId365(), targetItemId, driveId, attachmentFile.getFileName());
                urlResultList.add(urlResult);
            }
        }

        // lấy kết quả coppy
        try {
            Thread.sleep(Long.valueOf(TIME_WAIT_COPY));
        }catch (Exception e){
            try {Thread.sleep(4000);}catch (Exception e1){log.error("{}", e1);}
            log.error("{}", e);
        }
        for(String urlResult : urlResultList){
            String resultCopyToDrive = this.graphService.getResultCopyToDrive(urlResult);
            log.info("resultCopyToDrive: {}", resultCopyToDrive);
        }
    }
}
