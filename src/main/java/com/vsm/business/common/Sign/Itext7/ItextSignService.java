package com.vsm.business.common.Sign.Itext7;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.signatures.*;
import com.itextpdf.signatures.PdfSignatureAppearance;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.FileSystems;
import java.security.*;
import java.security.cert.Certificate;
import java.util.Collection;

@Service
public class ItextSignService {
    public static final String DEST = "";

    public static final String SRC = "E:\\Downloads\\PTYC_HCVP_DKCT_FULL.pdf";

    public static final String[] RESULT_FILES = new String[]{"itext_signed.pdf"};

    public static void _sign(String src, String dest, Rectangle position, String creator, String reason, String location,
                             String fieldName, String imagePath,
                             Certificate[] chain, PrivateKey pk, String digestAlgorithm, String provider, PdfSigner.CryptoStandard subfilter,
                             Collection<ICrlClient> crlList, IOcspClient ocspClient, ITSAClient tsaClient, int estimatedSize) throws GeneralSecurityException, IOException {
        PdfReader reader = new PdfReader(src);

        // lấy danh sách field trong file pdf \\


        PdfSigner signer = new PdfSigner(reader, new FileOutputStream(dest), new StampingProperties());
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

    public void sign() throws GeneralSecurityException, IOException {
        File file = new File(DEST);
        String path = "D:\\BackUp_o_D\\DuowngTora\\EOffice\\VMN\\trunk\\VSM_BUSINESS_SERVICE\\temp\\signfile\\0100686999.pfx";
        char[] password = "123456789".toCharArray();
        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
        KeyStore ks = KeyStore.getInstance("pkcs12", provider.getName());
        ks.load(new FileInputStream(path), password);
        String alias = ks.aliases().nextElement();
        PrivateKey pk = (PrivateKey) ks.getKey(alias, password);
        Certificate[] chain = ks.getCertificateChain(alias);

    }

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        String path = "D:\\BackUp_o_D\\DuowngTora\\EOffice\\VMN\\trunk\\VSM_BUSINESS_SERVICE\\temp\\signfile\\0100686999.pfx";
        char[] password = "123456789".toCharArray();
        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
        KeyStore ks = KeyStore.getInstance("pkcs12", provider.getName());
        ks.load(new FileInputStream(path), password);
        String alias = ks.aliases().nextElement();
        PrivateKey pk = (PrivateKey) ks.getKey(alias, password);
        Certificate[] chain = ks.getCertificateChain(alias);
        ItextSignService itextSignService = new ItextSignService();
        Rectangle rectangle = new Rectangle(36, 648, 200, 100);
        itextSignService._sign("C:\\Users\\Admin\\Desktop\\1. Mau OTL_QC LED_(E).pdf",
            "C:\\Users\\Admin\\Desktop\\1. Mau OTL_QC LED_(E)_Signed.pdf",
            rectangle,
            "DuowngTora",
            "DuowngTora test",
            "HADONGHANOIVIETNAM",
            "duowngtora",
            "D:\\BackUp_o_D\\DuowngTora\\EOffice\\VMN\\trunk\\VSM_BUSINESS_SERVICE\\temp\\watermark\\vincomretail.jpg",
            chain,
            pk,
            DigestAlgorithms.SHA256,
            provider.getName(),
            PdfSigner.CryptoStandard.CMS,
            null,
            null,
            null,
            0
        );
    }
}
