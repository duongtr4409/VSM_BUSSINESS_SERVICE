package com.vsm.business.common.Sign.Itext7;

import com.azure.core.implementation.AccessibleByteArrayOutputStream;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.signatures.*;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.Vector;
import com.vsm.business.common.Sign.Itext7.helper.SimpleTextRemover;
import com.vsm.business.utils.VNCharacterUtils;
import joptsimple.internal.Strings;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class ItextSignService_v2 {

    @Value("${system.folder.TEMP_FOLDER_SIGN_SOFT:./temp/signfile/}")
    public String FOLDER_SIGNFILE;
    //public final String FOLDER_SIGNFILE = new File("").getAbsolutePath() + "/temp/signfile/";

    private static final String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();

    private final String EXTENSION_IMAGE = ".png";

    private final String EXTENSION_CEFFITICATE = ".pfx";


    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private void _sign(FileOutputStream fileOutputStream, InputStream inputStream, Rectangle position, String creator, String reason, String location,
                             String fieldName, String imagePath,
                             Certificate[] chain, PrivateKey pk, String digestAlgorithm, String provider, PdfSigner.CryptoStandard subfilter,
                             Collection<ICrlClient> crlList, IOcspClient ocspClient, ITSAClient tsaClient, int estimatedSize) throws GeneralSecurityException, IOException, DocumentException {
        PdfReader reader = new PdfReader(inputStream);

        // tìm vị trí để điền chữ lý vô
        com.itextpdf.text.pdf.PdfReader pdfReader = new com.itextpdf.text.pdf.PdfReader(new FileInputStream(new File("C:\\Users\\Admin\\Desktop\\1. Mau OTL_QC LED_(E).pdf")));
        PdfStamper pdfStamper = new PdfStamper(pdfReader, new AccessibleByteArrayOutputStream());
        SimpleTextRemover remover = new SimpleTextRemover();

        System.out.printf("\ntest.pdf - Test\n");
        for (int i = 1; i <= pdfReader.getNumberOfPages(); i++)
        {
            System.out.printf("Page %d:\n", i);
            List<List<SimpleTextRemover.Glyph>> matches = remover.remove(pdfStamper, i, "{{DuowngTora}}");
            for (List<SimpleTextRemover.Glyph> match : matches) {
                SimpleTextRemover.Glyph first = match.get(0);
                Vector baseStart = first.base.getStartPoint();
                SimpleTextRemover.Glyph last = match.get(match.size()-1);
                Vector baseEnd = last.base.getEndPoint();
                System.out.printf("%d  Match from (%3.1f %3.1f) to (%3.1f %3.1f)\n", i, baseStart.get(Vector.I1), baseStart.get(Vector.I2), baseEnd.get(Vector.I1), baseEnd.get(Vector.I2));
                Rectangle rectangle = new Rectangle(baseStart.get(Vector.I1), baseStart.get(Vector.I2), 200, 100);

            }
        }
        pdfStamper.close();

        // lấy danh sách field trong file pdf \\


        PdfSigner signer = new PdfSigner(reader, fileOutputStream, new StampingProperties());
        // Create the signature appearance
        ImageData image = ImageDataFactory.create(imagePath);

        PdfSignatureAppearance appearance = signer.getSignatureAppearance();
//        appearance.setSignatureCreator().setReason(reason).setLocation(location).setImage(image)
        appearance.setSignatureCreator(creator).setReason(reason).setLocation(location).setImage(image)

            // Specify if the appearance before field is signed will be used
            // as a background for the signed field. The "false" value is the default value.
            .setReuseAppearance(false).setPageRect(position).setPageNumber(1);
        signer.setFieldName(fieldName);

        IExternalSignature pks = new PrivateKeySignature(pk, digestAlgorithm, provider);
        IExternalDigest digest = new BouncyCastleDigest();

        // Sign the document using the detached mode, CMS or CAdES equivalent.
        signer.signDetached(digest, pks, chain, crlList, ocspClient, tsaClient, estimatedSize, subfilter);
    }

    private void _signV1(String dest , InputStream inputStream, Rectangle position, int pageNumber, String creator, String reason, String location,
                       String fieldName, String imagePath,
                       Certificate[] chain, PrivateKey pk, String digestAlgorithm, String provider, PdfSigner.CryptoStandard subfilter,
                       Collection<ICrlClient> crlList, IOcspClient ocspClient, ITSAClient tsaClient, int estimatedSize) throws GeneralSecurityException, IOException, DocumentException {
        PdfReader reader = new PdfReader(inputStream);

        // enCode text
//        if(!Strings.isNullOrEmpty(reason)) reason = VNCharacterUtils.removeAccent(reason);
//        if(!Strings.isNullOrEmpty(location)) location = VNCharacterUtils.removeAccent(location);
//        if(!Strings.isNullOrEmpty(creator)) creator = VNCharacterUtils.removeAccent(creator);

        PdfFont f = PdfFontFactory.createFont(BaseFont.TIMES_ROMAN, PdfEncodings.UTF8);

        // lấy danh sách field trong file pdf \\

        StampingProperties stampingProperties = new StampingProperties();
        stampingProperties.useAppendMode();     // thêm phần ghi đè
        PdfSigner signer = new PdfSigner(reader, new FileOutputStream(dest), stampingProperties);
        signer.getDocument().addFont(f);    // thêm font tiếng việt
        // Create the signature appearance
        ImageData image = ImageDataFactory.create(imagePath);

        PdfSignatureAppearance appearance = signer.getSignatureAppearance();                                                                             // set thông tin hình ảnh hiển thị
        //appearance.setSignatureCreator(creator).setReason(reason).setLocation(location).setImage(image)
        //appearance.setImage(image)
        //.setReuseAppearance(false).setPageRect(position).setPageNumber(pageNumber);
            // Specify if the appearance before field is signed will be used
            // as a background for the signed field. The "false" value is the default value.

        signer.setFieldName(fieldName);


        appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC_AND_DESCRIPTION);
        // cấu hình mode hiển thị thông tin chỉ hiển thị ảnh
        appearance.setSignatureGraphic(image);
        appearance
        .setReuseAppearance(false).setPageRect(position).setPageNumber(pageNumber);
        appearance.setLayer2Text("Ngày ký: \n" + simpleDateFormat.format(new Date()));
        appearance.setLayer2FontSize(16);
        appearance.setLayer2Font(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));


        IExternalSignature pks = new PrivateKeySignature(pk, digestAlgorithm, provider);
        IExternalDigest digest = new BouncyCastleDigest();

        // Sign the document using the detached mode, CMS or CAdES equivalent.
        signer.signDetached(digest, pks, chain, crlList, ocspClient, tsaClient, estimatedSize, subfilter);
        reader.close();
    }

    public FileOutputStream sign(File fileSigned, InputStream inputStream, InputStream inputStreamRead, String password, String createdName, String reason, String location, String fieldName, String symbol, String signFolderName) throws GeneralSecurityException, IOException, DocumentException {
        String pathSignPfx = FOLDER_SIGNFILE + signFolderName + this.PATH_SEPARATOR + signFolderName + EXTENSION_CEFFITICATE;
        pathSignPfx = pathSignPfx.replaceAll("//", this.PATH_SEPARATOR);
        String pathSignImage = FOLDER_SIGNFILE + signFolderName + this.PATH_SEPARATOR + signFolderName + EXTENSION_IMAGE;
        pathSignImage = pathSignImage.replaceAll("//", this.PATH_SEPARATOR);
        char[] passwordChar = password.toCharArray();
        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
        KeyStore ks = KeyStore.getInstance("pkcs12", provider.getName());
        ks.load(new FileInputStream(pathSignPfx), passwordChar);
        String alias = ks.aliases().nextElement();
        PrivateKey pk = (PrivateKey) ks.getKey(alias, passwordChar);
        Certificate[] chain = ks.getCertificateChain(alias);

        // tìm vị trí để điền chữ ký
        FileOutputStream result = new FileOutputStream(fileSigned);
        com.itextpdf.text.pdf.PdfReader pdfReader = new com.itextpdf.text.pdf.PdfReader(inputStreamRead);
        PdfStamper pdfStamper = new PdfStamper(pdfReader, new AccessibleByteArrayOutputStream());
        SimpleTextRemover remover = new SimpleTextRemover();
        System.out.printf("\nRead Page: \n");
        int countSymbol = 0;            // biến kiểm tra xem có ký tự để ký vào không (th không có sẽ ký lên đầu trang)
        for (int i = 1; i <= pdfReader.getNumberOfPages(); i++)
        {
            int stt = 1;
            System.out.printf("Page %d:\n", i);
            List<List<SimpleTextRemover.Glyph>> matches = remover.remove(pdfStamper, i, symbol);
            for (List<SimpleTextRemover.Glyph> match : matches) {
                SimpleTextRemover.Glyph first = match.get(0);
                Vector baseStart = first.base.getStartPoint();
                SimpleTextRemover.Glyph last = match.get(match.size()-1);
                Vector baseEnd = last.base.getEndPoint();
                System.out.printf(" Match from (%3.1f %3.1f) to (%3.1f %3.1f)\n", baseStart.get(Vector.I1), baseStart.get(Vector.I2), baseEnd.get(Vector.I1), baseEnd.get(Vector.I2));
                Rectangle positon = new Rectangle(baseStart.get(Vector.I1), baseStart.get(Vector.I2), 200, 100);
                fieldName = fieldName + stt;
                if(!fileSigned.exists()) fileSigned.createNewFile();
                _signV1(fileSigned.getAbsolutePath(),
                    inputStream,
                    positon,
                    i,
                    createdName,
                    reason,
                    location,
                    fieldName + System.currentTimeMillis(),
                    pathSignImage,
                    chain,
                    pk,
                    DigestAlgorithms.SHA256,
                    provider.getName(),
                    PdfSigner.CryptoStandard.CMS,
                    null,
                    null,
                    null,
                    0);
                inputStream = new FileInputStream(fileSigned);
                countSymbol++;
                stt++;
            }
        }
        if(countSymbol == 0){       // TH không có ký tự để ký -> ký ở đầu file luôn
            Rectangle rectangle = new Rectangle(/*-200*/ PageSize.A4.getWidth() - 200, /*-100*/ 0, 200, 100);
            _signV1(fileSigned.getAbsolutePath(),
                inputStream,
                rectangle,
                1,
                createdName,
                reason,
                location,
                fieldName + System.currentTimeMillis(),
                pathSignImage,
                chain,
                pk,
                DigestAlgorithms.SHA256,
                provider.getName(),
                PdfSigner.CryptoStandard.CMS,
                null,
                null,
                null,
                0);
        }
        pdfStamper.close();
        pdfReader.close();
        result.flush();
        result.close();
        return result;
    }


    private String enCodeString(String source){
//        if(Strings.isNullOrEmpty(source)) return source;
//        byte[] bytes = source.getBytes();
//        return PdfEncodings.convertToString(bytes, "UTF-8");
////        return StringUtils.newStringUtf8(bytes);

        if(Strings.isNullOrEmpty(source)) return source;
        byte[] germanBytes = source.getBytes();
        String asciiEncodedString = new String(germanBytes, StandardCharsets.US_ASCII);
        return asciiEncodedString;
    }

    private String getInfoFromChain(Certificate[] chain) throws CertificateException, IOException, CRLException {
        for (int i = 0; i < chain.length; i++) {
            X509Certificate cert = (X509Certificate) chain[i];
            System.out.println(String.format("[%s] %s", i, cert.getSubjectDN()));
            if(cert.getSubjectDN() != null) return cert.getSubjectDN().toString();
        }
        return null;
    }

}
