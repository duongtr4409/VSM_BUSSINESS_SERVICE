package com.vsm.business.web.rest.custom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.graph.models.Permission;
import com.microsoft.graph.requests.PermissionGrantCollectionPage;
import com.microsoft.graph.requests.PermissionGrantCollectionRequestBuilder;
import com.vsm.business.common.Graph.GraphService;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.service.custom.mail.MailSchedule;
import com.vsm.business.service.mapper.AttachmentFileMapper;
import com.vsm.business.utils.WordXmlUtils;
import com.vsm.business.utils.wordhelper.Docx4JSRUtil;
import joptsimple.internal.Strings;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart;
import org.docx4j.wml.*;
import org.jvnet.jaxb2_commons.ppp.Child;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBElement;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        try {
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
        }finally {
            for(int i=0; i<listFile.size(); i++){
                listFile.get(i).toFile().delete();
            }
        }
    }

//    @GetMapping("/duowngTora")
//    public ResponseEntity<IResponseMessage> testMailScheduled(){
//        mailSchedule.sendMailWarningOutOfDateV2();
//        return ResponseEntity.ok().body(new LoadedMessage("DuowngTora"));
//    }

    @GetMapping("/duowngtora_split_file_v2")
    public ResponseEntity<IResponseMessage> splitDocxFileV2(@RequestParam("fileName") String fileName, @RequestParam("sizeOfText") Long sizeOfText) throws Docx4JException {
        String directoryTemp = "C:\\Users\\Admin\\Desktop\\temp\\temp\\";
        String outputDirectory = directoryTemp + "result\\split\\";
        WordprocessingMLPackage docxPackage = WordprocessingMLPackage.load(new File(directoryTemp + fileName));

        List<Object> listObject = docxPackage.getMainDocumentPart().getContent();       // lấy ra các phần tử là con trực tiếp của body
        List<Long> listSizeOfObject = new ArrayList<>();

//        listObject.forEach(ele -> {
//            AtomicLong size = new AtomicLong(0l);
//            if(ele.getClass().equals(P.class)){
//                P p = (P)ele;
//                p.getContent().stream().filter(ele1 -> ele1.getClass().equals(R.class)).forEach(ele1 -> {
//                    R r = (R)ele1;
//                    size.addAndGet(r.getContent().stream().filter(ele2 -> ele2.getClass().equals(Text.class) && ((Text) ele2).getValue() != null)
//                        .map(ele2 -> ((Text) ele2).getValue()).collect(Collectors.joining("")).split(" ").length + r.getContent().stream().filter(ele2 -> !ele2.getClass().equals(Text.class)).count());
//                });
//            }else{
//                size.set(1l);
//            }
//            mapSizeOfObject.put(ele.hashCode(), size.get());
//        });

        Map<Integer, Long> mapSizeOfObject = new HashMap<>();
        int n = listObject.size();
        for(int i=0; i<n; i++){
            long size = 0l;
            Object temp = listObject.get(i);
            if(temp.getClass().equals(P.class)){
                P p = (P)temp;
                for(int j=0; j<p.getContent().size(); j++){
                    Object tempOfP = p.getContent().get(j);
                    if(tempOfP.getClass().equals(R.class)){
                        R r = (R)tempOfP;
                        StringBuilder text = new StringBuilder();
                        for(int k=0; k<r.getContent().size(); k++){
                            Object tempOfRun = r.getContent().get(k);
                            if(tempOfRun.getClass().equals(JAXBElement.class) && ((JAXBElement)tempOfRun).getDeclaredType().equals(Text.class)){
                                text.append(((Text)((JAXBElement<?>) tempOfRun).getValue()).getValue());
                            }else{
                                size++;
                            }
                        }
                        size += temp.toString().split(" ").length;
                    }else{
                        size++;
                    }
                }
            }else{
                size = 1l;
            }
            listSizeOfObject.add(size);
            mapSizeOfObject.put(temp.hashCode(), size);
        }

        return ResponseEntity.ok().body(new LoadedMessage(true));
    }

    @GetMapping("/duowngtora-split-text")
    public ResponseEntity<IResponseMessage> splitTextV2(@RequestParam("fileName") String fileName, @RequestParam("maxLength") long maxLength) throws Docx4JException, IOException {
        String directoryTemp = "C:\\Users\\Admin\\Desktop\\temp\\temp\\";
        String outPutDirectory = "C:\\Users\\Admin\\Desktop\\temp\\temp\\result\\";
        WordprocessingMLPackage docxPackage = WordprocessingMLPackage.load(new File(directoryTemp + fileName));
        List<Object> objectList = docxPackage.getMainDocumentPart().getContent();
        List<Text> textList = Docx4JSRUtil.getAllElementsOfType(docxPackage.getMainDocumentPart(), Text.class);

        XpathAddressCustom xpathAddressCustom = new XpathAddressCustom();

        String folderSplitName = outPutDirectory + "\\split\\" + fileName.hashCode() + "\\";
        File folderSplit = new File(folderSplitName);
        if(!folderSplit.exists()){
            folderSplit.mkdirs();
        }else{
            Files.walk(Paths.get(folderSplit.getAbsolutePath())).parallel().forEach(ele -> {
                ele.toFile().delete();
            });
        }

        textList.stream().parallel().forEach(ele -> {
            try {
//                System.out.println("Text: " + ele.getValue());
//                System.out.println("xPath: " + getXpathOfText(ele));


                UUID uuid = UUID.randomUUID();
                getXpathCustomOfText2(ele, xpathAddressCustom, uuid);

                String text = ele.getValue();
                String[] texts = text.split(" ");
                if(texts.length > maxLength){
                    int n = texts.length;
                    int count = 0;
                    int partCount = 0;
                    StringBuilder stringBuilder = new StringBuilder();
                    boolean start = true;
                    for(int i=0; i<n; i++){
                        count++;
//                        if(!stringBuilder.toString().isEmpty()){
//                            stringBuilder.append(" ");
//                        }
                        if(!start){
                            stringBuilder.append(" ");
                        }
                        stringBuilder.append(texts[i]);
                        start = false;
                        if(count == maxLength){
                            UUID uuidPart = UUID.randomUUID();
                            createNewFileDocx(docxPackage, folderSplit, stringBuilder.toString(), uuidPart);
                            List<XpathPartCustom> xpathPartCustomList = xpathAddressCustom.getXpathPartCustomList().get(uuid.toString()) == null ? new ArrayList<>() : xpathAddressCustom.getXpathPartCustomList().get(uuid.toString());
                            xpathPartCustomList.add(new XpathPartCustom(uuidPart.toString(), ++partCount));
                            xpathAddressCustom.getXpathPartCustomList().put(uuid.toString(), xpathPartCustomList);

                            stringBuilder = new StringBuilder();
                            start = true;
                            count = 0;
                        }
                    }

                    if(count > 0){
                        UUID uuidPart = UUID.randomUUID();
                        createNewFileDocx(docxPackage, folderSplit, stringBuilder.toString(), uuidPart);
                        List<XpathPartCustom> xpathPartCustomList = xpathAddressCustom.getXpathPartCustomList().get(uuid.toString()) == null ? new ArrayList<>() : xpathAddressCustom.getXpathPartCustomList().get(uuid.toString());
                        xpathPartCustomList.add(new XpathPartCustom(uuidPart.toString(), ++partCount));
                        xpathAddressCustom.getXpathPartCustomList().put(uuid.toString(), xpathPartCustomList);
                    }

                }else{
                    WordprocessingMLPackage wordprocessingMLPackage = null;
                    wordprocessingMLPackage = WordprocessingMLPackage.createPackage();
                    wordprocessingMLPackage.getMainDocumentPart().getStyleDefinitionsPart()
                        .setJaxbElement(docxPackage.getMainDocumentPart().getStyleDefinitionsPart().getContents());

                    ObjectFactory factory = new ObjectFactory();
                    P p = factory.createP();
                    R r = factory.createR();
                    r.getContent().add(ele);
                    p.getContent().add(r);
                    wordprocessingMLPackage.getMainDocumentPart().getContent().add(p);

//                wordprocessingMLPackage.save(new File(folderSplit.getAbsolutePath() + "\\" + ele.hashCode() + ".docx"));
                    wordprocessingMLPackage.save(new File(folderSplit.getAbsolutePath() + "\\" + uuid.toString() + ".docx"));
                }
                ele.setValue("");
            } catch (InvalidFormatException e) {
                throw new RuntimeException(e);
            } catch (Docx4JException e) {
                throw new RuntimeException(e);
            }
        });

        File resultFolderTemp = new File(outPutDirectory + "merge\\" + fileName.hashCode());
        if(!resultFolderTemp.exists()) resultFolderTemp.mkdirs();
        docxPackage.save(new File(resultFolderTemp + "\\" + fileName));

        xpathAddressCustom.setFileName(fileName);
        writeDataToFile(xpathAddressCustom, new File(outPutDirectory + "mapdata\\" + fileName.hashCode() + ".json"));

        return ResponseEntity.ok().body(new LoadedMessage(fileName.hashCode()));
    }

    @GetMapping("/duowngtora-merge-text")
    public void mergeTextV2(HttpServletResponse response, @RequestParam("id") String id) throws IOException, InstantiationException, IllegalAccessException, Docx4JException {
        String outPutDirectory = "C:\\Users\\Admin\\Desktop\\temp\\temp\\result\\";
        XpathAddressCustom xpathAddressCustom = this.readDataFromFile(new File(outPutDirectory + "\\mapdata\\" + id + ".json"), XpathAddressCustom.class);

        Map<String, List<XpathCustom>> mapDataElement = xpathAddressCustom.getXpathCustomList();
        File fileResult = new File( outPutDirectory + "merge\\" + id + "\\" + xpathAddressCustom.getFileName());
        WordprocessingMLPackage docxPackage = WordprocessingMLPackage.load(fileResult);

        Files.walk(Paths.get(outPutDirectory + "split\\" + id)).filter(Files::isRegularFile).parallel().forEach(ele -> {
            try {
                String fileName = FilenameUtils.removeExtension(ele.getFileName().toString());
                if(mapDataElement.containsKey(fileName)){
                    WordprocessingMLPackage wordprocessingMLPackageTemp = WordprocessingMLPackage.load(ele.toFile());
                    List<Text> listText = Docx4JSRUtil.getAllElementsOfType(wordprocessingMLPackageTemp.getMainDocumentPart(), Text.class);
                    if(listText != null){
                        String value = listText.stream().map(ele1 -> ele1.getValue()).collect(Collectors.joining(""));
                        List<XpathCustom> xpathCustomList = mapDataElement.get(fileName);
                        if(xpathCustomList != null){
                            fillTextToXpath(docxPackage.getMainDocumentPart(), value, xpathCustomList);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("DuowngTora Lỗi ở file: " + ele);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

        xpathAddressCustom.getXpathPartCustomList().keySet().forEach(ele -> {
            List<XpathPartCustom> xpathPartCustoms = xpathAddressCustom.getXpathPartCustomList().get(ele);
            if(xpathPartCustoms != null && !xpathPartCustoms.isEmpty()){
                xpathPartCustoms.sort((e1, e2) -> e1.getStt() - e2.getStt());
                StringBuilder textBuilder = new StringBuilder();
                boolean start = true;
                for(XpathPartCustom ele1 : xpathPartCustoms){
                    try {
                        WordprocessingMLPackage wordprocessingMLPackageTemp = WordprocessingMLPackage.load(new File(outPutDirectory + "split\\" + id + "\\" + ele1.getName() + ".docx"));
                        List<Text> textList = Docx4JSRUtil.getAllElementsOfType(wordprocessingMLPackageTemp.getMainDocumentPart(), Text.class);
//                        if(!textBuilder.toString().isEmpty()){
//                            textBuilder.append(" ");
//                        }
                        if(!start){
                            textBuilder.append(" ");
                        }
                        textBuilder.append(textList.stream().map(ele2 -> ele2.getValue()).collect(Collectors.joining(" ")));
                    } catch (Docx4JException e) {
                        System.out.println("DuowngTora Lỗi ở file part: " + ele);
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
                try {
                    List<XpathCustom> xpathCustomList = mapDataElement.get(ele);
                    fillTextToXpath(docxPackage.getMainDocumentPart(), textBuilder.toString(), xpathCustomList);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });

        docxPackage.save(fileResult);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment; filename=\"result" + id + ".docx\"");
        response.getOutputStream().write(IOUtils.toByteArray(new FileInputStream(fileResult)));
    }

    private final ObjectMapper objectMapper = new ObjectMapper();
    private void writeDataToFile(Object object, File file) {
        CompletableFuture.runAsync(() -> {
            try {
                if(!file.exists()) file.createNewFile();
                byte data[];
                data = objectMapper.writeValueAsBytes(object);
                Files.write(file.toPath(), data);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    private <T> T readDataFromFile(File file, Class<T> tclass) throws InstantiationException, IllegalAccessException, IOException {
        return this.objectMapper.readValue(Files.readAllBytes(file.toPath()), tclass);
    }

    private void createNewFileDocx(WordprocessingMLPackage docxSourceStyle,File folderSave, String textValue, UUID uuidPart) throws Docx4JException {
        WordprocessingMLPackage wordprocessingMLPackage = null;
        wordprocessingMLPackage = WordprocessingMLPackage.createPackage();
        wordprocessingMLPackage.getMainDocumentPart().getStyleDefinitionsPart()
            .setJaxbElement(docxSourceStyle.getMainDocumentPart().getStyleDefinitionsPart().getContents());

        ObjectFactory factory = new ObjectFactory();
        P p = factory.createP();
        R r = factory.createR();
        Text t = factory.createText();
        t.setValue(textValue);
        r.getContent().add(t);
        p.getContent().add(r);
        wordprocessingMLPackage.getMainDocumentPart().getContent().add(p);
        wordprocessingMLPackage.save(new File(folderSave.getAbsolutePath() + "\\" + uuidPart.toString() + ".docx"));
    }



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

    private String getXpathOfText(Text text){
        List<String> xpath = new ArrayList<>();
        Object parentOfText = text.getParent();
        xpath.add(parentOfText.getClass().toString());
        while(!parentOfText.getClass().equals(Body.class)){
            if(parentOfText instanceof org.jvnet.jaxb2_commons.ppp.Child && parentOfText instanceof ContentAccessor){
               parentOfText = ((org.jvnet.jaxb2_commons.ppp.Child) parentOfText).getParent();
               xpath.add(parentOfText.getClass().toString());
            }else{
                break;
            }
        }
        Collections.reverse(xpath);
        return xpath.stream().collect(Collectors.joining(" -> "));
    }

    private void getXpathCustomOfText(Text text, XpathAddressCustom xpathAddressCustom){
        List<XpathCustom> xpathCustomList = new ArrayList<>();
        Object parent = text.getParent();
        XpathCustom xpathCustom = new XpathCustom();
        xpathCustom.setClassName(parent.getClass().getName());
        xpathCustom.setIndexOfParent(((ContentAccessor)((Child)parent).getParent()).getContent().indexOf(parent));
        xpathCustomList.add(xpathCustom);
        while(!parent.getClass().equals(Body.class)){
            if(parent instanceof Child && parent instanceof ContentAccessor){
                parent = ((Child)parent).getParent();
                XpathCustom xpathCustomTemp = new XpathCustom();
                xpathCustomTemp.setClassName(parent.getClass().getName());
                int indexOfParent = ((ContentAccessor) ((Child)parent).getParent()).getContent().indexOf(parent);
                if(indexOfParent > -1){
                    xpathCustomTemp.setIndexOfParent(((ContentAccessor)((Child)parent).getParent()).getContent().indexOf(parent));
                }else{
                    List<Object> listChildOfParent = ((ContentAccessor)((Child)parent).getParent()).getContent();
                    Object finalParent = parent;
                    IntStream.range(0, listChildOfParent.size()).filter(eleIndex -> {
                        if(listChildOfParent.get(eleIndex) instanceof JAXBElement){
                            return finalParent.equals(((JAXBElement)listChildOfParent.get(eleIndex)).getValue());
                        }
                        return false;
                    }).findFirst().orElse(-1);
                }
                xpathCustomList.add(xpathCustomTemp);
            }else{
                break;
            }
        }
        xpathCustomList.get(0).setTextValue(text.getValue());
        Collections.reverse(xpathCustomList);
        xpathAddressCustom.getXpathCustomList().put(String.valueOf(text.hashCode()), xpathCustomList);
    }

    private void getXpathCustomOfText2(Text text, XpathAddressCustom xpathAddressCustom, UUID uuid){
        List<XpathCustom> xpathCustomList = new ArrayList<>();

        XpathCustom xpathCustomText = new XpathCustom();
        xpathCustomText.setClassName(text.getClass().getName());
        xpathCustomText.setTextValue(text.getValue());
        xpathCustomText.setIndexOfParent(findIndexOfParent(text));
        xpathCustomList.add(xpathCustomText);

        Object parent = text.getParent();
        XpathCustom xpathCustom = new XpathCustom();
        xpathCustom.setClassName(parent.getClass().getName());
        xpathCustom.setIndexOfParent(findIndexOfParent(parent));
        xpathCustomList.add(xpathCustom);
        while(!parent.getClass().equals(Body.class)){
            if(parent instanceof Child && parent instanceof ContentAccessor){
                parent = ((Child)parent).getParent();
                XpathCustom xpathCustomTemp = new XpathCustom();
                xpathCustomTemp.setClassName(parent.getClass().getName());
                xpathCustomTemp.setIndexOfParent(findIndexOfParent(parent));
                xpathCustomList.add(xpathCustomTemp);
            }else{
                break;
            }
        }
        xpathCustomList.get(0).setTextValue(text.getValue());
        Collections.reverse(xpathCustomList);
        //xpathAddressCustom.getXpathCustomList().put(String.valueOf(text.hashCode()), xpathCustomList);
        xpathAddressCustom.getXpathCustomList().put(uuid.toString(), xpathCustomList);
    }

    private int findIndexOfParent(Object child){
        List<Object> listChildOfParent = ((ContentAccessor) ((Child)child).getParent()).getContent();
        int indexOfParent = listChildOfParent.indexOf(child);
        if(indexOfParent > -1){
            return indexOfParent;
        }else{
            return IntStream.range(0, listChildOfParent.size()).filter(eleIndex -> {
                if(listChildOfParent.get(eleIndex) instanceof JAXBElement){
                    return child.equals(((JAXBElement)listChildOfParent.get(eleIndex)).getValue());
                }
                return false;
            }).findFirst().orElse(-1);
        }
    }

    private void fillTextToXpath(MainDocumentPart mainDocumentPart, String value, List<XpathCustom> xpathCustomList) throws ClassNotFoundException {
        int n = xpathCustomList.size();
        List<Object> content = mainDocumentPart.getContent();
        for(int i=0; i<n; i++){
            XpathCustom xpathCustom = xpathCustomList.get(i);
            if(xpathCustom.getClassName().equals(Body.class.getName())){
                continue;
            }else{
                if(xpathCustom.getClassName().equals(Text.class.getName())){
                    Text text = (Text)((JAXBElement)content.get(xpathCustom.getIndexOfParent())).getValue();
                    text.setValue(value);
                }else{
                    if(content.get(xpathCustom.getIndexOfParent()) instanceof ContentAccessor && !(content.get(xpathCustom.getIndexOfParent()) instanceof JAXBElement)){
                        content = ((ContentAccessor) content.get(xpathCustom.getIndexOfParent())).getContent();
                    }else if(content.get(xpathCustom.getIndexOfParent()) instanceof JAXBElement){
                        content = ((ContentAccessor)((JAXBElement) content.get(xpathCustom.getIndexOfParent())).getValue()).getContent();
                    }
                }
            }
        }
    }

    private static class XpathAddressCustom{
        private String fileName;
        Map<String, List<XpathCustom>> xpathCustomList = new HashMap<>();

        Map<String, List<XpathPartCustom>> xpathPartCustomList = new HashMap<>();

        public XpathAddressCustom(){
        }

        public XpathAddressCustom(String fileName, Map<String, List<XpathCustom>> xpathCustomList, Map<String, List<XpathPartCustom>> xpathPartCustomList) {
            this.fileName = fileName;
            this.xpathCustomList = xpathCustomList;
            this.xpathPartCustomList = xpathPartCustomList;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public Map<String, List<XpathCustom>> getXpathCustomList() {
            return xpathCustomList;
        }

        public void setXpathCustomList(Map<String, List<XpathCustom>> xpathCustomList) {
            this.xpathCustomList = xpathCustomList;
        }

        public Map<String, List<XpathPartCustom>> getXpathPartCustomList() {
            return xpathPartCustomList;
        }

        public void setXpathPartCustomList(Map<String, List<XpathPartCustom>> xpathPartCustomList) {
            this.xpathPartCustomList = xpathPartCustomList;
        }
    }

    private static class XpathCustom{
        private String className;

        private int indexOfParent;

        private String textValue;

        public XpathCustom(){

        }

        public XpathCustom(String className, int indexOfParent, String textValue) {
            this.className = className;
            this.indexOfParent = indexOfParent;
            this.textValue = textValue;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public int getIndexOfParent() {
            return indexOfParent;
        }

        public void setIndexOfParent(int indexOfParent) {
            this.indexOfParent = indexOfParent;
        }

        public String getTextValue() {
            return textValue;
        }

        public void setTextValue(String textValue) {
            this.textValue = textValue;
        }
    }

    private static class XpathPartCustom{

        private String name;

        private int stt;

        public XpathPartCustom() {
        }

        public XpathPartCustom(String name, int stt) {
            this.name = name;
            this.stt = stt;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStt() {
            return stt;
        }

        public void setStt(int stt) {
            this.stt = stt;
        }
    }
}
