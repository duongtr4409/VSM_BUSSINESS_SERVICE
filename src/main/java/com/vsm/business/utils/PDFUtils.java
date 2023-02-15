package com.vsm.business.utils;

import com.itextpdf.awt.geom.AffineTransform;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;


import java.io.File;
import java.nio.file.FileSystems;

@Component
public class PDFUtils {

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private VNCharacterUtils vnCharacterUtils;

    private static final String PROJECT_FOLDER = new File("").getAbsolutePath() + "/temp/watermark/image001-7.jpg";
    private static final String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();
    private static String WATERMARK_FILE_PATH = null;

    public File addQRCode(File file, String link) throws IOException, DocumentException{
        if(!"pdf".equals(FilenameUtils.getExtension(file.getName()))) file = this.fileUtils.convertFile(file);

        BarcodeQRCode qrCode = new BarcodeQRCode(link, 1, 1, null);
        Image qrCodeImage = qrCode.getImage();
        qrCodeImage.scaleAbsolute(80f, 80f);
        qrCodeImage.setAbsolutePosition(0f, 0f);

        PdfReader pdfReader = new PdfReader( new FileInputStream(file));
        PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(file));
        for(int i = 1; i<=pdfReader.getNumberOfPages(); i++){
            PdfContentByte contentByte = pdfStamper.getUnderContent(i);
            contentByte.addImage(qrCodeImage);
        }
        pdfStamper.close();
        return file;
    }

    public InputStream addQRCode_bak(InputStream source, String link) throws DocumentException, IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BarcodeQRCode qrCode = new BarcodeQRCode(link, 1, 1, null);
        Image qrCodeImage = qrCode.getImage();
        qrCodeImage.scaleAbsolute(80f, 80f);
        qrCodeImage.setAbsolutePosition(0f, 0f);
        PdfReader pdfReader = new PdfReader(source);
        PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
        for(int i=1; i<=pdfReader.getNumberOfPages(); i++){
            PdfContentByte contentByte = pdfStamper.getUnderContent(i);
            contentByte.addImage(qrCodeImage);
        }
        pdfStamper.close();
        try {
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        }finally {
            try{byteArrayOutputStream.close();}catch (Exception e){}
        }
    }

    public File addWatermark(File file, String link) throws IOException, DocumentException{
        if(!"pdf".equals(FilenameUtils.getExtension(file.getName()))) file = this.fileUtils.convertFile(file);
        PdfReader pdfReader = new PdfReader(new FileInputStream(file));
        PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(file));

        Font FONT = new Font(Font.FontFamily.HELVETICA, 34, Font.BOLD, new GrayColor(0.5f));
        Phrase p = new Phrase("Memorynotfound (watermark)", FONT);

        Image img = Image.getInstance("Premium.jpg");
        float w = img.getScaledWidth();
        float h = img.getScaledHeight();

        PdfContentByte over;
        Rectangle pageSize;
        float x, y;

        int n = pdfReader.getNumberOfPages();
        for(int i=1; i<=n; i++){
            pageSize = pdfReader.getPageSizeWithRotation(i);
            x = (pageSize.getLeft() + pageSize.getRight())/2;
            y = (pageSize.getTop() + pageSize.getBottom())/2;
            over = pdfStamper.getOverContent(i);
            over.saveState();;

            PdfGState pdfGState = new PdfGState();
            pdfGState.setFillOpacity(0.2f);
            over.setGState(pdfGState);

            if(i % 2 == 1){
                ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, y, 0);
            }else{
                over.addImage(img, w, 0, 0, h, x-(w/2), y-(h/2));
            }
            over.restoreState();
        }
        pdfStamper.close();
        pdfReader.close();
        return file;
    }


    public InputStream addWatermark_bak(InputStream source) throws IOException, DocumentException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfReader pdfReader = new PdfReader(source);
        PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);

//        Font FONT = new Font(Font.FontFamily.HELVETICA, 34, Font.BOLD, new GrayColor(0.5f));
//        Phrase p = new Phrase("Memorynotfound (watermark)", FONT);

        if(this.WATERMARK_FILE_PATH == null) this.WATERMARK_FILE_PATH = this.PROJECT_FOLDER.replaceAll("//", this.PATH_SEPARATOR);
        Image img = Image.getInstance(this.WATERMARK_FILE_PATH);
        float w = img.getScaledWidth();
        float h = img.getScaledHeight();

        PdfContentByte over;
        Rectangle pageSize;
        float x, y;

        int n = pdfReader.getNumberOfPages();
        for(int i=1; i<=n; i++){
            pageSize = pdfReader.getPageSizeWithRotation(i);
            x = (pageSize.getLeft() + pageSize.getRight()) / 2;
            y = (pageSize.getTop() + pageSize.getBottom()) / 2;
            over = pdfStamper.getOverContent(i);
            over.saveState();;

            PdfGState pdfGState = new PdfGState();
            pdfGState.setFillOpacity(0.2f);
            over.setGState(pdfGState);

//            if(i % 2 == 1){
//                ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, y, 0);
//            }else{
//                over.addImage(img, w, 0, 0, h, x-(w/2), y-(h/2));
//            }
            over.addImage(img, w, 0, 0, h, x-(w/2), y-(h/2));
            over.restoreState();
        }
        pdfStamper.close();
        pdfReader.close();
        try {
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        }finally {
            try {byteArrayOutputStream.close();}catch (Exception e){};
        }
    }

    public static InputStream addWatermarkTest(InputStream source) throws IOException, DocumentException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfReader pdfReader = new PdfReader(source);
        PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);

//        Font FONT = new Font(Font.FontFamily.HELVETICA, 34, Font.BOLD, new GrayColor(0.5f));
//        Phrase p = new Phrase("Memorynotfound (watermark)", FONT);

        if(WATERMARK_FILE_PATH == null) WATERMARK_FILE_PATH = PROJECT_FOLDER.replaceAll("//", PATH_SEPARATOR);
        Image img = Image.getInstance(WATERMARK_FILE_PATH);
        float w = img.getScaledWidth();
        float h = img.getScaledHeight();

        PdfContentByte over;
        Rectangle pageSize;
        float x, y;

        int n = pdfReader.getNumberOfPages();
        for(int i=1; i<=n; i++){
            pageSize = pdfReader.getPageSizeWithRotation(i);
            x = (pageSize.getLeft() + pageSize.getRight()) / 2;
            y = (pageSize.getTop() + pageSize.getBottom()) / 2;
            over = pdfStamper.getOverContent(i);
            over.saveState();;

            PdfGState pdfGState = new PdfGState();
            pdfGState.setFillOpacity(0.2f);
            over.setGState(pdfGState);

//            if(i % 2 == 1){
//                ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, y, 0);
//            }else{
//                over.addImage(img, w, 0, 0, h, x-(w/2), y-(h/2));
//            }
//            over.addImage(img, w, 0, 0, h, x-(w/2), y-(h/2));
            AffineTransform affineTransform  =  AffineTransform.getTranslateInstance(x, y);
            affineTransform.rotate(45);
            over.addImage(img, affineTransform);
//            PdfCanvas
            over.restoreState();
        }
        pdfStamper.close();
        pdfReader.close();
        try {
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        }finally {
            try{byteArrayOutputStream.close();}catch (Exception e){};
        }
    }















    public InputStream addWatermark(InputStream source, String watermarkText) throws IOException, DocumentException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfReader pdfReader = new PdfReader(source);
        PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);

        while (watermarkText.length() < 800){
            watermarkText += "    " + watermarkText;
        }

        Font FONT = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, new GrayColor(0.5f));
        Phrase p = new Phrase(vnCharacterUtils.removeAccent(watermarkText), FONT);

//        if(this.WATERMARK_FILE_PATH == null) this.WATERMARK_FILE_PATH = this.PROJECT_FOLDER.replaceAll("//", this.PATH_SEPARATOR);
//        Image img = Image.getInstance(this.WATERMARK_FILE_PATH);
//        float w = img.getScaledWidth();
//        float h = img.getScaledHeight();

        PdfContentByte over;
        Rectangle pageSize;
        float x, y;

        int n = pdfReader.getNumberOfPages();
        for(int i=1; i<=n; i++){
            pageSize = pdfReader.getPageSizeWithRotation(i);
            x = (pageSize.getLeft() + pageSize.getRight()) / 2;
            y = (pageSize.getTop() + pageSize.getBottom()) / 2;
            over = pdfStamper.getOverContent(i);
            over.saveState();

            PdfGState pdfGState = new PdfGState();
            pdfGState.setFillOpacity(0.3f);
            over.setGState(pdfGState);

//            if(i % 2 == 1){
//                ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, y, 0);
//            }else{
//                over.addImage(img, w, 0, 0, h, x-(w/2), y-(h/2));
//            }
//            over.addImage(img, w, 0, 0, h, x-(w/2), y-(h/2));
            ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, y/3, 45);
            ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, 5*y/3, 45);
            ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, y, 45);
            over.restoreState();
        }
        pdfStamper.close();
        pdfReader.close();
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }


    public InputStream addQRCode(InputStream source, String link, String requestDataCode, String status) throws DocumentException, IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BarcodeQRCode qrCode = new BarcodeQRCode(link, 1, 1, null);
        Image qrCodeImage = qrCode.getImage();
        qrCodeImage.scaleAbsolute(80f, 80f);
        qrCodeImage.setAbsolutePosition(0f, 0f);
        PdfReader pdfReader = new PdfReader(source);
        PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);

        PdfContentByte over;
        Font FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new GrayColor(0.2f));
        Phrase p = new Phrase(vnCharacterUtils.removeAccent(requestDataCode), FONT);
        Phrase p2 = new Phrase(vnCharacterUtils.removeAccent(status), FONT);
        for(int i=1; i<=pdfReader.getNumberOfPages(); i++){
            PdfContentByte contentByte = pdfStamper.getUnderContent(i);
            contentByte.addImage(qrCodeImage);

            over = pdfStamper.getOverContent(i);
            over.saveState();
            PdfGState pdfGState = new PdfGState();
            pdfGState.setFillOpacity(0.8f);
            over.setGState(pdfGState);
            if(!Strings.isNullOrEmpty(requestDataCode))
                ColumnText.showTextAligned(over, Element.ALIGN_LEFT, p, /*85*/ 80, /*36*/ 8, 0);
//            if(!Strings.isNullOrEmpty(status))
//                ColumnText.showTextAligned(over, Element.ALIGN_LEFT, p2, 85, 24, 0);
        }
        pdfStamper.close();
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }


//    public static void main(String[] args) throws IOException, DocumentException {
//        PDFUtils pdfUtils = new PDFUtils();
//        File file = new File("C:\\Users\\Admin\\Desktop\\DuowngToraSigned.pdf");
//        FileInputStream inputStream = new FileInputStream(file);
//
//        InputStream inputStream1 = pdfUtils.addWatermark(inputStream, "DuowngTora");
//        InputStream inputStream2 = pdfUtils.addQRCode(inputStream1, "https://memorynotfound.com/add-watermark-to-pdf-document-using-itext-and-java/", "DuowngToraaa DuowngToraaa", "DuowngToraaa ");
//
//        File fileResult = new File("C:\\Users\\Admin\\Desktop\\Result - DuowngToraSigned.pdf");
//        if(!fileResult.exists()) fileResult.createNewFile();
//        FileOutputStream fileOutputStream = new FileOutputStream(fileResult);
//        fileOutputStream.write(IOUtils.toByteArray(inputStream2));
//        fileOutputStream.flush();
//    }
}
