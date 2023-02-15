package com.vsm.business.utils;

import com.itextpdf.layout.property.Background;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.*;


import java.io.File;
import java.util.*;
import java.util.List;
import java.util.function.Function;

@Component
public class QRCodeUtils {

    @Autowired
    private FileUtils fileUtils;

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

    public InputStream addQRCode(InputStream source, String link) throws DocumentException, IOException {
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
        byteArrayOutputStream.close();
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

}
