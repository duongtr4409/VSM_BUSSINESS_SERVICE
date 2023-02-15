package com.vsm.business.common.Sign;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.graphics.PdfFont;
import com.spire.pdf.graphics.PdfFontFamily;
import com.spire.pdf.graphics.PdfFontStyle;
import com.spire.pdf.graphics.PdfImage;
import com.spire.pdf.security.GraphicMode;
import com.spire.pdf.security.PdfCertificate;
import com.spire.pdf.security.PdfCertificationFlags;
import com.spire.pdf.security.PdfSignature;
import com.vsm.business.common.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.geom.Rectangle2D;
import java.io.File;

@Service
public class CertSign {

    private final Logger logger = LoggerFactory.getLogger(CertSign.class);

    public boolean sign(String pdfPath, String outputPath, String privateKeyPath, String password, String signatureName, Rectangle2D position, String signer, String contactInfo, String location, String reason, String imagePath) {

        File file = new File(pdfPath);
        if (!(file.exists() && file.isFile())) {
            logger.error("{}", AppConstant.Error.ErrorCode.FILE_NOT_FOUND);
            return false;
        }

        //Create a PdfDocument object
        PdfDocument doc = new PdfDocument();
        try {
            //Load a sample PDF file
            doc.loadFromFile(pdfPath);

            //Load a pfx certificate
            PdfCertificate cert = new PdfCertificate(privateKeyPath, password);

            //Create a PdfSignature object and specify its position and size
            PdfSignature signature = new PdfSignature(doc, doc.getPages().get(0), cert, signatureName);
//        Rectangle2D rect = new Rectangle2D.Float();
//        rect.setFrame(new Point2D.Float((float) doc.getPages().get(0).getActualSize().getWidth() - 320, (float) doc.getPages().get(0).getActualSize().getHeight() - 140), new Dimension(270, 100));
//        signature.setBounds(rect);
            signature.setBounds(position);
            //Set the graphics mode
            signature.setGraphicMode(GraphicMode.Sign_Image_And_Sign_Detail);

            //Set the signature content
            signature.setNameLabel("Signer:");
            signature.setName(signer);
            signature.setContactInfoLabel("ContactInfo:");
            signature.setContactInfo(contactInfo);
            signature.setDateLabel("Date:");
            signature.setDate(new java.util.Date());
            signature.setLocationInfoLabel("Location:");
            signature.setLocationInfo(location);
            signature.setReasonLabel("Reason: ");
            signature.setReason(reason);
            signature.setDistinguishedNameLabel("DN: ");
            signature.setDistinguishedName(signature.getCertificate().get_IssuerName().getName());
            signature.setSignImageSource(PdfImage.fromFile(imagePath));

            //Set the signature font
            signature.setSignDetailsFont(new PdfFont(PdfFontFamily.Helvetica, 10f, PdfFontStyle.Regular));

            //Set the document permission
            signature.setDocumentPermissions(PdfCertificationFlags.Forbid_Changes);
            signature.setCertificated(true);

            //Save to file
            doc.saveToFile(outputPath);
        } catch (Exception e) {
            logger.error("{} {}", AppConstant.Error.ErrorCode.SIGN, e);
            return false;
        } finally {
            doc.close();
        }
        return true;
    }
}
