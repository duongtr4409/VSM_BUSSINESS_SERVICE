package com.vsm.business.common.Sign.sim;

import com.vsm.business.common.Sign.sim.helper.PdfSign;
import com.vsm.business.common.Sign.sim.helper.SimSignServiceException;
import com.vsm.business.utils.VNCharacterUtils;
import joptsimple.internal.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class MobiSignService {

    public final Logger log = LoggerFactory.getLogger(MobiSignService.class);

    @Value("${system.sign.sim.mobi.appEndPoint:http://mobica.smart-office.vn/signMobiNew/rest/service}")
    private String appEndPoint;

    private final String NO_SIGNATURE_INFO_ERROR = "Không có thông tin chữ ký";

    private final String HASH_FILE_ERROR = "Lỗi khi hash file";

    public void _sign(InputStream inputStream, String msisdn, String prompt, String reason, File fileResult) throws Exception {
        byte[] pdfContent = new byte[inputStream.available()];
        inputStream.read(pdfContent);
        inputStream.close();

        PdfSign signer = new PdfSign(appEndPoint);

        byte[] hashValue = signer.hashPdfFile(pdfContent, reason);
        if(hashValue == null){
            return;
        }

        byte[] bSignature = signer.signHashDataMobi(hashValue, msisdn, prompt);

        if(bSignature == null){
            throw new SimSignServiceException(this.NO_SIGNATURE_INFO_ERROR);
        }

        byte[] dataSigned = signer.addExternalSignature(bSignature);
        if(dataSigned == null){
            return;
        }

        FileOutputStream fileOutputStream = new FileOutputStream(fileResult);
        fileOutputStream.write(dataSigned);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public byte[] sign(InputStream inputStream, String msisdn, String prompt, String reason) throws Exception {
        prompt = remoteVICharacter(prompt);
        reason = remoteVICharacter(reason);

        byte[] pdfContent = new byte[inputStream.available()];
        inputStream.read(pdfContent);
        inputStream.close();

        PdfSign signer = new PdfSign(appEndPoint);

        byte[] hashValue = signer.hashPdfFile(pdfContent, reason);
        if(hashValue == null){
            throw new SimSignServiceException(this.HASH_FILE_ERROR);
        }

        byte[] bSignature = signer.signHashDataMobi(hashValue, msisdn, prompt);

        if(bSignature == null){
            throw new SimSignServiceException(this.NO_SIGNATURE_INFO_ERROR);
        }

        byte[] dataSigned = signer.addExternalSignature(bSignature);
        if(dataSigned == null){
            throw new SimSignServiceException(this.NO_SIGNATURE_INFO_ERROR);
        }

        return dataSigned;
    }

    private String remoteVICharacter(String str){
        if(Strings.isNullOrEmpty(str)) return "";
        String result = VNCharacterUtils.removeAccent(str);
        return result;
    }

    public static void main(String[] args) throws Exception {
        MobiSignService mobiSignService = new MobiSignService();
        File file = new File("C:\\Users\\Admin\\Desktop\\1. Mau OTL_QC LED_(E).pdf");
        File fileResult = new File("C:\\Users\\Admin\\Desktop\\1. Mau OTL_QC LED_(E) - sim result.pdf");
        InputStream inputStream = new FileInputStream(file);
        String msisdn = "84765059619";
        String prompt = "DuowngTora";
        String reason = "DuowngToratestsimsign";
        mobiSignService.appEndPoint = "http://mobica.smart-office.vn/signMobiNew/rest/service";
        mobiSignService._sign(inputStream, msisdn, prompt, reason, fileResult);
    }
}
