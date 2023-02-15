package com.vsm.business.web.rest.custom;

import com.microsoft.graph.requests.DriveItemCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;
import com.vsm.business.common.Graph.GraphService;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/graph")
public class GraphCustomRest {
    private final Logger log = LoggerFactory.getLogger(GraphCustomRest.class);

    private final GraphService graphService;

    public GraphCustomRest(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("/test")
    public ResponseEntity<IResponseMessage> test() {
        GraphServiceClient graphClient = graphService.buildGraphClient();
        DriveItemCollectionPage children = graphClient.me().drive().root().children().buildRequest().get();
        return ResponseEntity.ok().body(new LoadedMessage(children));
    }
}
