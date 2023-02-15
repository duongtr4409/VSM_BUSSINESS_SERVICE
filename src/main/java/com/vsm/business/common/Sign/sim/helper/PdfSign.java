package com.vsm.business.common.Sign.sim.helper;

import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.security.*;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.vsm.business.common.Sign.sim.helper.Constants;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;
import java.util.List;

@Component
public class PdfSign {
    public static final Logger logger = LoggerFactory.getLogger(PdfSign.class);

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

//    @Autowired
//    Constants Constants;

    private PdfReader reader = null;
    private ByteArrayOutputStream count = null;
    private PdfSignatureAppearance pdfSignatureAppearance;
    private OcspClient ocspClient = null;
    private TSAClient tsaClient = null;
    private Collection<byte[]> crlBytes = null;
    private MakeSignature.CryptoStandard sigtype = MakeSignature.CryptoStandard.CMS;

    private String apEndPoint;

    private void closeObjectStream(){
        try {
            if(count != null) count.close();
        } catch (Exception e){};

        try {
            if(reader != null) reader.close();
        } catch (Exception e){};
    }

    public PdfSign() {
    }

    public PdfSign(String apEndPoint) {
        this.apEndPoint = apEndPoint;
    }

    public JSONObject getRes() {
        return res;
    }

    public void setRes(JSONObject res) {
        this.res = res;
    }

    private JSONObject res;

    public byte[] hashPdfFile(byte[] pdfContent, String reason) throws Exception {
        try {
            int estimatedSize = 8192;
            if (crlBytes != null) {
                for (byte[] element : crlBytes) {
                    estimatedSize += element.length + 10;
                }
            }
            if (ocspClient != null)
                estimatedSize += 4192;
            if (tsaClient != null)
                estimatedSize += 4192;

            reader = new PdfReader(pdfContent);
            count = new ByteArrayOutputStream();
            AcroFields af = reader.getAcroFields();
            List<String> signatureNameNames = af.getSignatureNames();
            int signatureNumber = signatureNameNames.size() + 1;
            PdfStamper pdfStamper;
            if (signatureNumber > 1) {
                pdfStamper = PdfStamper.createSignature(reader, count, '\0', null, true);
            } else {
                pdfStamper = PdfStamper.createSignature(reader, count, '\0');
            }
            pdfSignatureAppearance = pdfStamper.getSignatureAppearance();
            ExternalDigest externalDigest = new BouncyCastleDigest();

//            sap.setCertificate(chain[0]);
            if (sigtype == MakeSignature.CryptoStandard.CADES) {
                pdfSignatureAppearance.addDeveloperExtension(PdfDeveloperExtension.ESIC_1_7_EXTENSIONLEVEL2);
            }
            PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, sigtype == MakeSignature.CryptoStandard.CADES ? PdfName.ETSI_CADES_DETACHED : PdfName.ADBE_PKCS7_DETACHED);
//            dic.setReason(pdfSignatureAppearance.getReason());
            dic.setReason(reason);
            dic.setLocation(pdfSignatureAppearance.getLocation());
            dic.setSignatureCreator(pdfSignatureAppearance.getSignatureCreator());
            dic.setContact(pdfSignatureAppearance.getContact());
            dic.setDate(new PdfDate(pdfSignatureAppearance.getSignDate()));
            pdfSignatureAppearance.setCryptoDictionary(dic);

            ExternalSignature pks = new PrivateKeySignature(DigestAlgorithms.SHA256, "BC");
            String hashAlgorithm = pks.getHashAlgorithm();

            InputStream data;
            HashMap<PdfName, Integer> exc = new HashMap<>();
            exc.put(PdfName.CONTENTS, estimatedSize * 2 + 2);
            pdfSignatureAppearance.preClose(exc);
            data = pdfSignatureAppearance.getRangeStream();

            return DigestAlgorithms.digest(data, externalDigest.getMessageDigest(hashAlgorithm));
        } catch (Exception ex) {
            logger.info("hashPdfFile: error", ex);
            return null;
        }finally {
            this.closeObjectStream();
        }
    }

    public byte[] signHashDataMobi(byte[] hash, String signer, String promptText) {
        try {
            String sDataToSignDigest = Base64Utils.base64Encode(hash);
            logger.info("SignService signHashDataMobi: "+ signer +" - "+ promptText + " - " + sDataToSignDigest);
            byte[] dataSigned = sign(signer, promptText, sDataToSignDigest);

            return dataSigned;
        } catch (Exception ex) {
            logger.info("signHashData: error", ex);
            return null;
        }
    }

    private static String removeSpecialCharToPrompt(String input) {
        String whiteListChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz,.:;0123456789- ";
        StringBuffer sb = new StringBuffer();
        //StringBuilder sb = new StringBuilder(); // chuyển sang StringBuilder fix báo lỗi khi chạy mvn verify
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            for (int j = 0; j < whiteListChars.length(); j++) {
                char allowdChar = whiteListChars.charAt(j);
                if (ch == allowdChar) {
                    sb.append(ch);
                    break;
                }
            }
        }
        return sb.toString();
    }

    private byte[] sign(String MSISDN, String sTextDisplay, String sBase64) {

        sTextDisplay = removeSpecialCharToPrompt(sTextDisplay);

        try {
            SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                .build();

            HostnameVerifier allowAllHosts = new NoopHostnameVerifier();

            SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslcontext, allowAllHosts);

            RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(600000)
                .build();

            CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(connectionFactory)
//                    .setProxy(new HttpHost("10.20.23.210", 9090, "http"))
//                    .setProxy(new HttpHost("10.20.24.249", 8080, "http"))
                .setDefaultRequestConfig(config)
                .build();

            Unirest.setHttpClient(httpclient);
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }


        try {
            JSONObject bodyObj = new JSONObject();
            JSONObject MSS_SignatureReq = new JSONObject();
            MSS_SignatureReq.put("MessagingMode", "synch");
//            MSS_SignatureReq.put("SignatureProfile", "http://alauda.mobi/digitalSignature");
            MSS_SignatureReq.put("SignatureProfile", "http://alauda.mobi/nonRepudiation");

            JSONObject MobileUser = new JSONObject();
            MobileUser.put("MSISDN", MSISDN);
            MSS_SignatureReq.put("MobileUser", MobileUser);

            JSONObject DataToBeDisplayed = new JSONObject();
            DataToBeDisplayed.put("Encoding", "UTF-8");
            DataToBeDisplayed.put("Data", sTextDisplay);
            DataToBeDisplayed.put("MimeType", "text/plain");
            MSS_SignatureReq.put("DataToBeDisplayed", DataToBeDisplayed);

            JSONObject DataToBeSigned = new JSONObject();
            DataToBeSigned.put("Encoding", "base64");
            DataToBeSigned.put("Data", sBase64);
            DataToBeSigned.put("MimeType", "application/x-sha256");
            MSS_SignatureReq.put("DataToBeSigned", DataToBeSigned);

            JSONArray AdditionalServices = new JSONArray();
            JSONObject validate = new JSONObject();
            validate.put("Description", "http://uri.etsi.org/TS102204/v1.1.2#validate");
            AdditionalServices.put(validate);
            MSS_SignatureReq.put("AdditionalServices", AdditionalServices);

            bodyObj.put("MSS_SignatureReq", MSS_SignatureReq);

            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
            String date = sdf.format(now);
            String path = "/rest/service";
            String method = "POST";
            String host = Constants.HOST;
            String content = date + "\n" + method + "\n" + host + "\n" + path + "\n" + bodyObj.toString() + "\n";
            String signature = String.valueOf(Hex.encodeHex(CryptoUtil.HmacSHA256(content, Constants.API_KEY)));
            String base64appid = CryptoUtil.Base64encode(Constants.AP_ID);
            String auth = CryptoUtil.Base64encode(base64appid + ":" + signature);
            logger.info(bodyObj.toString());
            JSONObject json;

            com.mashape.unirest.http.HttpResponse<JsonNode> response =
                Unirest.post(apEndPoint)
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Basic " + auth)
                    .header("cache-control", "no-cache")
                    .header("Date", date)
                    .body(bodyObj.toString())
                    .asJson();

            JsonNode node = response.getBody();
            json = node.getObject();
            res = json;
            logger.info(json.toString());

            if (json.has("MSS_SignatureResp")) {
                String a = json.getJSONObject("MSS_SignatureResp").getJSONObject("MSS_Signature").getString("Base64Signature");
                byte[] bReturn = Base64Utils.base64Decode(a);
                return bReturn;
            } else {
                return null;
            }
        } catch (Exception ex) {
            logger.info("sign: error", ex);
            return null;
        }
    }

    public byte[] signHashData(byte[] hash, PrivateKey privKey, Certificate[] chain, Provider provider) {
        try {
            Calendar cal = Calendar.getInstance();
            byte[] ocsp = null;
            if (chain.length >= 2 && ocspClient != null) {
                ocsp = ocspClient.getEncoded((X509Certificate) chain[0], (X509Certificate) chain[1], null);
            }

            ExternalSignature externalSignature = new PrivateKeySignature(DigestAlgorithms.SHA1, "BC");
            String hashAlgorithm = externalSignature.getHashAlgorithm();
            PdfPKCS7 sgn1 = new PdfPKCS7(privKey, chain, hashAlgorithm, provider.getName(), null, false);

            byte[] sh = sgn1.getAuthenticatedAttributeBytes(hash, /*cal,*/ ocsp, crlBytes, sigtype);
            sgn1.update(sh, 0, sh.length);
            byte[] extSignature = sgn1.getEncodedPKCS7(hash, /*cal,*/ tsaClient, ocsp, crlBytes, sigtype);
            return extSignature;
        } catch (Exception ex) {
            logger.info("signHashData: error", ex);
            return null;
        }
    }

    public byte[] addExternalSignature(byte[] extSignature) throws Exception {
        try {
            BouncyCastleProvider provider = new BouncyCastleProvider();
            Security.addProvider(provider);

            byte[] encodedSig = extSignature;
            int estimatedSize = 0;

            if (estimatedSize == 0) {
                estimatedSize = 8192;
            }
            if (estimatedSize + 2 < encodedSig.length) {
                throw new IOException("Not enough space");
            }

            byte[] paddedSig = new byte[estimatedSize];
            System.arraycopy(encodedSig, 0, paddedSig, 0, encodedSig.length);

            PdfDictionary dic2 = new PdfDictionary();
            dic2.put(PdfName.CONTENTS, new PdfString(paddedSig).setHexWriting(true));
            pdfSignatureAppearance.close(dic2);

            count.flush();
            count.close();
            reader.close();
            return count.toByteArray();
        } catch (Exception ex) {
            logger.info("addExternalSignature: error", ex);
            return null;
        }
    }
}
