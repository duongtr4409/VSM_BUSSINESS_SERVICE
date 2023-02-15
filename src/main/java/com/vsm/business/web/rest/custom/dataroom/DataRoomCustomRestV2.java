package com.vsm.business.web.rest.custom.dataroom;

import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.tasks.LargeFileUploadResult;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.service.custom.dataroom.DataRoomCustomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/data-room/v2")
public class DataRoomCustomRestV2 {

    private final Logger log = LoggerFactory.getLogger(DataRoomCustomRestV2.class);

    private final DataRoomCustomService dataRoomCustomService;

    public DataRoomCustomRestV2(DataRoomCustomService dataRoomCustomService) {
        this.dataRoomCustomService = dataRoomCustomService;
    }

//    @GetMapping("/_all_item")
//    public ResponseEntity<IResponseMessage> getAllItem(@RequestParam(value = "itemId", required = false) String itemId){
//        List<DriveItem> result = dataRoomCustomService.getAllItem(itemId);
//        return ResponseEntity.ok().body(new LoadedMessage(result));
//    }

    @PostMapping("/_all_item")
    public ResponseEntity<IResponseMessage> getAllItemV2(@RequestParam(value = "itemId", required = false) String itemId){
        List<DriveItem> result = dataRoomCustomService.getAllItemV2(itemId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/sync_file")
    public ResponseEntity<IResponseMessage> syncFileV2(@RequestParam("file")MultipartFile file, @RequestParam("itemId") String itemId, @RequestParam("driveId") String driveId) throws IOException {
        log.debug("DataRoomCustomRestV2: syncFileV2()");
        LargeFileUploadResult<DriveItem> result = this.dataRoomCustomService.syncFileV2(file, itemId, driveId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_all/directory_object")
    public ResponseEntity<IResponseMessage> getAllDirectoryObject(@RequestParam(value = "siteId", required = false) String siteId){
        log.debug("DataRoomCustomRestV2: getAllDirectoryObject({})", siteId);
        List<Object> result = this.dataRoomCustomService.getAllDirectoryObject(siteId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/_all/item_of_drive")
    public ResponseEntity<IResponseMessage> getAllItemFormDrive(@RequestParam("driveId") String driveId, @RequestParam(value = "itemId", required = false) String itemId){
        log.debug("DataRoomCustomRestV2: getAllItemFormDrive({})", driveId);
        List<DriveItem> result = this.dataRoomCustomService.getAllItemFormDrive(driveId, itemId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/sync/request_data/{requestDataId}")
    public ResponseEntity<IResponseMessage> syncRequestData(@PathVariable("requestDataId") Long requestDataId) throws Exception {
        this.dataRoomCustomService.syncToDataRoom(requestDataId);
        return ResponseEntity.ok().body(new LoadedMessage(true));
    }
}
