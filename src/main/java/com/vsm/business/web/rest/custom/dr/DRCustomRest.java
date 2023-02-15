package com.vsm.business.web.rest.custom.dr;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.graph.models.Site;
import com.vsm.business.common.Graph.GraphService;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;

@RestController
@RequestMapping("/api/DR")
public class DRCustomRest {
    private final Logger log = LoggerFactory.getLogger(DRCustomRest.class);

    private GraphService graphService;

    public DRCustomRest(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("/_all/sites")
    public ResponseEntity<IResponseMessage> getAllSite() {
        List<Site> result = graphService.getAllSite();
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }
}
