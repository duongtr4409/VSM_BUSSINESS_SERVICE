package com.vsm.business.utils;

import com.google.common.io.Files;
import com.vsm.business.service.custom.RequestDataCustomService;
import com.vsm.business.utils.wordhelper.Docx4JSRUtil;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.OpcPackage;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.util.*;


@Component
public class WordXmlUtils extends OpcPackage implements IBaseFileUtils{

    private Logger log = LoggerFactory.getLogger(WordXmlUtils.class);

    @Autowired
    public Docx4JSRUtil docx4JSRUtil;

    @Autowired
    public WordXmlUtilsV2 wordXmlUtilsV2;

    @Value("${system.folder.TEMP_FOLDER:./temp/}")
    public String tempFolder;

    private static final String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();

    public OutputStream writeTextToFile(Map<String, Object> props, InputStream inputStream) throws IOException {
        OutputStream result = new ByteArrayOutputStream();

        Map<String, String> placeholderMap = new HashMap<>();
        Map<String, RequestDataCustomService.TableFieldDuowngTora> tablesMap = new HashMap<>();
        Map<String, byte[]> imageMap = new HashMap<>();
        Set<String> listProps = props.keySet();
        listProps.forEach(ele -> {
            try {
                Object value = props.get(ele);
                if(value instanceof RequestDataCustomService.TableFieldDuowngTora){     // nếu là field dạng bảng
                    RequestDataCustomService.TableFieldDuowngTora tableFieldDuowngTora = (RequestDataCustomService.TableFieldDuowngTora) value;
                    tablesMap.put(ele, tableFieldDuowngTora);
                }else if(value instanceof byte[]){
                    imageMap.put(ele, (byte[])value);
                }else{
                    String newValue;
                    if(List.class.isAssignableFrom(value.getClass())){
                        newValue = String.join(", ", (List) value);
                    }else if(Set.class.isAssignableFrom(value.getClass())){
                        newValue = String.join(", ", (Set) value);
                    }else{
                        newValue = String.valueOf(value);
                    }

                    if(!Strings.isNullOrEmpty(newValue))
                        placeholderMap.put(ele, newValue);
                    else
                        placeholderMap.put(ele, "");

                }
            }catch (Exception e){log.debug("{}", e);}
        });

        String tempFolderPath = (tempFolder + String.valueOf(System.currentTimeMillis()) + PATH_SEPARATOR).replace("//", PATH_SEPARATOR);
        File folderTemp = new File(tempFolderPath);
        if(!folderTemp.exists()) folderTemp.mkdir();

        String timeMillisTemp = String.valueOf(System.currentTimeMillis());
        File sourceFileTemp = new File(folderTemp.getAbsolutePath() + PATH_SEPARATOR + "DuowngTora_" + timeMillisTemp + ".docx");
        File resultFileTemp = new File(folderTemp.getAbsolutePath() + PATH_SEPARATOR + "DuowngToraResult_" + timeMillisTemp + ".docx");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(sourceFileTemp);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();

            //this.writeTextToDocx_v2(sourceFileTemp, placeholderMap, resultFileTemp);
            this.writeTextToDocx_v3(sourceFileTemp, placeholderMap, tablesMap, resultFileTemp, imageMap);

//            FileInputStream fileInputStream = new FileInputStream(resultFileTemp);
            //byte[] byteResult = Files.toByteArray(resultFileTemp);
            byte[] byteResult = FileUtils.readFileToByteArray(resultFileTemp);
            result.write(byteResult);
            result.flush();
            result.close();

            //
            FileInputStream fileInputStream = new FileInputStream(resultFileTemp);
            ByteArrayOutputStream outputStream = (ByteArrayOutputStream)wordXmlUtilsV2.writeTextToFile(props, fileInputStream);
            return outputStream;
            //
        }catch (Exception e){
            log.error("{}", e);
        }finally {
            sourceFileTemp.delete();
            resultFileTemp.delete();
            folderTemp.delete();
        }
        return result;
    }

    public void writeTextToDocx_v2(File fileSource, Map<String, String> placeholderMap, File fileResult) throws IOException {
        if(!fileResult.exists()) fileResult.createNewFile();
        try {
            // this max take 4 seconds but this happens only once (internal heatup of data structures)
            // https://stackoverflow.com/questions/18975049/how-to-decrease-docx4j-load-time
            WordprocessingMLPackage sourceDocxDoc = WordprocessingMLPackage.load(fileSource);

            Docx4JSRUtil.searchAndReplace(sourceDocxDoc, placeholderMap);

            sourceDocxDoc.save(fileResult);
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param fileSource        : file cần fill in
     * @param placeholderMap    : mapping data
     * @param tablesMap         : mapping các trường bảng trong form
     * @param fileResult        : file lưu kết quả
     * @throws IOException
     */
    public void writeTextToDocx_v3(File fileSource, Map<String, String> placeholderMap, Map<String, RequestDataCustomService.TableFieldDuowngTora> tablesMap, File fileResult, Map<String, byte[]> imageMap) throws IOException {
        if(!fileResult.exists()) fileResult.createNewFile();
        try {
            // this max take 4 seconds but this happens only once (internal heatup of data structures)
            // https://stackoverflow.com/questions/18975049/how-to-decrease-docx4j-load-time
            WordprocessingMLPackage sourceDocxDoc = WordprocessingMLPackage.load(fileSource);

            try {
                if("TRUE".equalsIgnoreCase(this.HAS_FILL)){
                    if(imageMap != null && !imageMap.isEmpty())
                        this.writeImageToDocx(sourceDocxDoc, imageMap, placeholderMap);
                }
            }catch (Exception e){
                log.error("{}", e);
            }

            try {
                Docx4JSRUtil.searchAndReplace_v2(sourceDocxDoc, placeholderMap, tablesMap);
            }catch (Exception e){
                log.error("{}", e);
            }

            sourceDocxDoc.save(fileResult);
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
    }

                        // Thêm ảnh vào file \\
    @Value("${system.fill-in.IMAGE.HAS_FILL:FALSE}")
    public String HAS_FILL;

    @Value("${system.fill-in.IMAGE.SIZE_OF_IMAGE:300000}")
    public String SIZE_OF_IMAGE;

    private void writeImageToDocx(WordprocessingMLPackage sourceDocxDoc, Map<String, byte[]> imageMap, Map<String, String> placeholderMap){
        int size_of_image = 300000;
        try {
            size_of_image = Integer.valueOf(SIZE_OF_IMAGE);
        }catch (Exception e){log.error("{}", e); size_of_image = 300000; }

        MainDocumentPart mainDocumentPart = sourceDocxDoc.getMainDocumentPart();
        List<Text> texts = Docx4JSRUtil.getAllElementsOfType(mainDocumentPart, Text.class);
        List<Docx4JSRUtil.TextMetaItem> metaItemList = Docx4JSRUtil.buildMetaItemList(texts);
        Set<P> paragraphs = new HashSet<>();
        for(Docx4JSRUtil.TextMetaItem textMetaItem : metaItemList){
            paragraphs.add(getParagraphWithText(textMetaItem.getText()));
        }
        if(!paragraphs.isEmpty()){
            for(P paragraph : paragraphs){
                StringBuilder textOfParagraph = new StringBuilder();
                for(Text text : Docx4JSRUtil.getAllElementsOfType(paragraph, Text.class)){
                    textOfParagraph.append(text.getValue());
                }

                int finalSize_of_image = size_of_image;
                imageMap.keySet().forEach(ele -> {
                    if(textOfParagraph.toString().contains(ele)){
                        int index = mainDocumentPart.getContent().indexOf(paragraph);
                        if(index > -1) {
                            try {
                                BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(sourceDocxDoc, imageMap.get(ele));
                                if (imagePart.getImageInfo().getSize().getWidthMpt() > finalSize_of_image) {
                                    try {
                                        Double ratio = Double.valueOf(finalSize_of_image) / Double.valueOf(imagePart.getImageInfo().getSize().getWidthMpt());
                                        imagePart.getImageInfo().getSize().setSizeInMillipoints(finalSize_of_image, (int) Math.ceil(imagePart.getImageInfo().getSize().getHeightMpt() * ratio));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                Inline inline = null;
                                inline = imagePart.createImageInline("DuowngTora Image " + ele, "DuowngTora Alt Text " + ele, 1, 2, false);
                                P Imageparagraph = addImageToParagraph(inline);
                                mainDocumentPart.getContent().add(index + 1, Imageparagraph);
                                placeholderMap.put(ele, "");
                            } catch (Exception e) {
                                log.error("{}", e);
                            }
                        }else{  // Th ko tìm thấy paramgraph (đang nghĩ là do paragraph nămg trong table -> lỗi ko tìm được) thì xử lý đặc thù
                            try {
                                Tc tc = (Tc)paragraph.getParent();
                                int indexInTc = tc.getContent().indexOf(paragraph);
                                if(indexInTc > -1){
                                    BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(sourceDocxDoc, imageMap.get(ele));
                                    int wtemp = tc.getTcPr().getTcW().getW().intValue();
                                    if (imagePart.getImageInfo().getSize().getWidthMpt() > finalSize_of_image) {
                                        try {
                                            Double ratio = Double.valueOf(finalSize_of_image) / Double.valueOf(imagePart.getImageInfo().getSize().getWidthMpt());
                                            imagePart.getImageInfo().getSize().setSizeInMillipoints(finalSize_of_image, (int) Math.ceil(imagePart.getImageInfo().getSize().getHeightMpt() * ratio));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    Inline inline = null;
                                    inline = imagePart.createImageInline("DuowngTora Image " + ele, "DuowngTora Alt Text " + ele, 1, 2, false, wtemp);
                                    P Imageparagraph = addImageToParagraph(inline);
                                    tc.getContent().add(indexInTc + 1, Imageparagraph);
                                    placeholderMap.put(ele, "");
                                }
                            }catch (Exception e){
                                log.error("{}", e);
                            }
                        }
                    }
                });

            }
        }
    }

    private P getParagraphWithText(Text text){
        try {
            R rParent = (R)text.getParent();
            P pParent = (P)rParent.getParent();
            return pParent;
        }catch (Exception e){
            e.printStackTrace();
            ObjectFactory factory = new ObjectFactory();
            P p = factory.createP();
            return p;
        }
    }

    private P addImageToParagraph(Inline inline){
        ObjectFactory factory = new ObjectFactory();
        P p = factory.createP();
        R r = factory.createR();
        p.getContent().add(r);
        Drawing drawing = factory.createDrawing();
        r.getContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);
        return p;
    }
}
