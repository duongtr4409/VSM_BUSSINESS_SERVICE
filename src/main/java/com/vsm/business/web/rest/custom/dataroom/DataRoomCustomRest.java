package com.vsm.business.web.rest.custom.dataroom;

import com.microsoft.graph.models.Site;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.service.custom.dataroom.DataRoomCustomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/data-room")
public class DataRoomCustomRest {

    private final Logger log = LoggerFactory.getLogger(DataRoomCustomRest.class);

    private final DataRoomCustomService dataRoomCustomService;

    public DataRoomCustomRest(DataRoomCustomService dataRoomCustomService) {
        this.dataRoomCustomService = dataRoomCustomService;
    }

    @PostMapping("/all_site")
    public ResponseEntity<IResponseMessage> getAllSites() {
        List<Site> result = this.dataRoomCustomService.getAllSite();
        log.debug("DataRoomCustomRest: DataRoomCustomRest(): {}", result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/all_item/{siteId}")
    public ResponseEntity<IResponseMessage> getAllItemOfSite(@PathVariable("siteId") String siteId) {
        List<Object> result = this.dataRoomCustomService.getAllDriverOfSite(siteId);
        log.debug("DataRoomCustomRest: getAllDriverOfSite({}): {}", siteId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
