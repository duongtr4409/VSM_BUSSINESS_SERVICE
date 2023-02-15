package com.vsm.business.utils;

import com.vsm.business.utils.wordhelper.Docx4JSRUtil;
import liquibase.pro.packaged.f;
import liquibase.pro.packaged.in;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.XmlUtils;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.*;

public class DuowngToratestAddImage {

    public static void main(String[] args) throws Exception {
        String money = "1000000";
        Double moneyD = Double.valueOf(money);
        Locale locale = new Locale("vi", "US");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        System.out.println("DuowngTora: " + numberFormat.format(moneyD).substring(4));

        FileInputStream fis = new FileInputStream("C:\\Users\\Admin\\Desktop\\base64.txt");
        String base64 = IOUtils.toString(fis, "UTF-8");
        byte[] base64Content = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
//        FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\Admin\\Desktop\\duowngTora.jpg"));
//        fileOutputStream.write(base64Content);
//        fileOutputStream.flush();
//        fileOutputStream.close();

        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.load(new File("C:\\Users\\Admin\\Desktop\\Công văn đối chiếu công nợ1658229703517.docx"));
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();

        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordPackage, base64Content);
        if(imagePart.getImageInfo().getSize().getWidthMpt() > 300000){
            try {
                Double ratio = Double.valueOf(300000) / Double.valueOf(imagePart.getImageInfo().getSize().getWidthMpt());
                imagePart.getImageInfo().getSize().setSizeInMillipoints(300000, (int)Math.ceil(imagePart.getImageInfo().getSize().getHeightMpt() * ratio));
            }catch (Exception e){e.printStackTrace();}
        }
        Inline inline = imagePart.createImageInline("DuowngTora Image", "DuowngTora Alt Text", 1, 2, false);
        P Imageparagraph = addImageToParagraph(inline);

        Map<String, P> imageMap = new HashMap<>();
        imageMap.put("{{Duowng_Tora}}", Imageparagraph);
        replaceTextWithImage(mainDocumentPart, imageMap);

        wordPackage.save(new File("C:\\Users\\Admin\\Desktop\\Test_202208Aug0001_DuowngTora-testfillinimage.docx"));
    }

    private static P addImageToParagraph(Inline inline){
        ObjectFactory factory = new ObjectFactory();
        P p = factory.createP();
        R r = factory.createR();
        p.getContent().add(r);
        Drawing drawing = factory.createDrawing();
        r.getContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);
        return p;
    }

    public static void replaceTextWithImage(MainDocumentPart mainDocumentPart, Map<String, P> mapImage){
        String findText = "{{Duowng_Tora}}";
        P image = mapImage.get(findText);
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

                if(textOfParagraph.toString().contains(findText)){
                    int index = mainDocumentPart.getContent().indexOf(paragraph);
                    if(index > -1){
                        System.out.println("DuowngTora: " + index);
                        mainDocumentPart.getContent().add(index + 1, image);
                    }
                }
            }
        }
    }

    public static P getParagraphWithText(Text text){
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
}
