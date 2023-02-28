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
import com.vsm.business.utils.WordXmlUtils;
import com.vsm.business.utils.wordhelper.Docx4JSRUtil;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart;
import org.docx4j.wml.Styles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/test")
public class wTestController {

    private final GraphService graphService;

    private final MailSchedule mailSchedule;

    private final AttachmentFileRepository attachmentFileRepository;

    private final AttachmentFileMapper attachmentFileMapper;

    private final WordXmlUtils wordXmlUtils;

    public wTestController(GraphService graphService, MailSchedule mailSchedule, AttachmentFileRepository attachmentFileRepository, AttachmentFileMapper attachmentFileMapper, WordXmlUtils wordXmlUtils) {
        this.graphService = graphService;
        this.mailSchedule = mailSchedule;
        this.attachmentFileRepository = attachmentFileRepository;
        this.attachmentFileMapper = attachmentFileMapper;
        this.wordXmlUtils = wordXmlUtils;
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

    @GetMapping("/duowngtora-spliting-file")
    public ResponseEntity<IResponseMessage> testSplitFileDoc(@RequestParam("fileName") String fileName) throws Exception {

        File file = new File("C:\\Users\\Admin\\Desktop\\temp\\temp\\" + fileName);
        WordprocessingMLPackage sourceDocxDoc = WordprocessingMLPackage.load(file);

//        Docx4JSRUtil.searchAndReplace_v2(sourceDocxDoc, new HashMap<>(), new HashMap<>());

        List<Object> listObject = sourceDocxDoc.getMainDocumentPart().getContent();
        int part = 0;
        List<Object> listObjectPart = new ArrayList<>();
        WordprocessingMLPackage docPart = null;
        for(int i=0; i<listObject.size(); i++){
            if(i != 0 && i%10 == 0){
                listObjectPart.add(listObject.get(i));
                part++;
                docPart = (WordprocessingMLPackage)sourceDocxDoc.clone();
                docPart.getMainDocumentPart().getContent().clear();
                docPart.getMainDocumentPart().getContent().addAll(listObjectPart);
                File fileResult = new File("C:\\Users\\Admin\\Desktop\\temp\\temp\\result\\split\\" + part + "_" + fileName);
                docPart.save(fileResult);
                listObjectPart = new ArrayList<>();
            }else{
                listObjectPart.add(listObject.get(i));
            }
        }
        if(listObjectPart.size() > 0){
            part++;
            docPart = (WordprocessingMLPackage)sourceDocxDoc.clone();
            docPart.getMainDocumentPart().getContent().clear();
            docPart.getMainDocumentPart().getContent().addAll(listObjectPart);
            File fileFinalPart = new File("C:\\Users\\Admin\\Desktop\\temp\\temp\\result\\split\\" + part + "_" + fileName);
            docPart.save(fileFinalPart);
        }

        return ResponseEntity.ok().body(new LoadedMessage(true));
    }

    @GetMapping("/duowngtora-merge-file")
    public ResponseEntity<IResponseMessage> testMergeDocxFile() throws IOException, Docx4JException {
        List<Path> listFile = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get("C:\\Users\\Admin\\Desktop\\temp\\temp\\result\\split"))){
            listFile = paths.filter(Files::isRegularFile)
                .sorted((ele1, ele2) ->
                    Integer.valueOf(ele1.getFileName().toString().split("_")[0]).compareTo(Integer.valueOf(ele2.getFileName().toString().split("_")[0]))
                ).collect(Collectors.toList());
        }
        WordprocessingMLPackage docResult = WordprocessingMLPackage.load(listFile.get(0).toFile());
        for(int i=1; i<listFile.size(); i++){
            WordprocessingMLPackage docTempPart = WordprocessingMLPackage.load(listFile.get(i).toFile());
            docResult.getMainDocumentPart().getContent().addAll(docTempPart.getMainDocumentPart().getContent());
        }

        docResult.save(new File("C:\\Users\\Admin\\Desktop\\temp\\temp\\result\\merge\\ResultMerge.docx"));

        return ResponseEntity.ok().body(new LoadedMessage(true));
    }

//    @GetMapping("/duowngTora")
//    public ResponseEntity<IResponseMessage> testMailScheduled(){
//        mailSchedule.sendMailWarningOutOfDateV2();
//        return ResponseEntity.ok().body(new LoadedMessage("DuowngTora"));
//    }


    public static void main(String[] args) throws Docx4JException {
        String INPUT_DIR = "C:\\Users\\Admin\\Desktop\\temp\\temp";
        String OUTPUT_DIR = "C:\\Users\\Admin\\Desktop\\";
        File dir = new File(INPUT_DIR);
        String[] files = dir.list();
        File file = null;
        if (files.length == 0) {
            System.out.println("The directory is empty");
        } else {
            for (String aFile : files) {
                System.out.println(aFile);

                file = new File(INPUT_DIR
                    + "/" + aFile);
            }
        }

        // Creating new documents
        WordprocessingMLPackage doc1 = WordprocessingMLPackage.createPackage();
        WordprocessingMLPackage doc2 = WordprocessingMLPackage.createPackage();

        // loading existing document
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
            .load(new java.io.File(file.getPath()));
        MainDocumentPart tempDocPart = wordMLPackage.getMainDocumentPart();
        List<Object> obj = wordMLPackage.getMainDocumentPart().getContent();

        // for copying styles from existing doc to new docs
        StyleDefinitionsPart sdp = tempDocPart.getStyleDefinitionsPart();
        Styles tempStyle = sdp.getContents();
        doc1.getMainDocumentPart().getStyleDefinitionsPart()
            .setJaxbElement(tempStyle);
        doc2.getMainDocumentPart().getStyleDefinitionsPart()
            .setJaxbElement(tempStyle);

        boolean flag = false;
        for (Object object : obj) {
            if (!flag) {
                if (object.toString().equalsIgnoreCase("CONSTRUCTION DETAILS:")) {
                    flag = true;
                }
                doc1.getMainDocumentPart().addObject(object);
            } else {
                doc2.getMainDocumentPart().addObject(object);
            }

        }
        String fileName = file.getName().toString().replace(".docx", "");
        doc1.save(new File(OUTPUT_DIR + "/" + fileName + "-1.docx"));
        doc2.save(new File(OUTPUT_DIR + "/" + fileName + "-2.docx"));
    }



}
