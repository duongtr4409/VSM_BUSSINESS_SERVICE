package com.vsm.business.common.Sign.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.tasks.LargeFileUploadResult;
import com.spire.ms.System.Security.Cryptography.X509Certificates.X509Certificate2;
import com.vsm.business.common.Graph.GraphService;
import com.vsm.business.domain.AttachmentFile;
import com.vsm.business.domain.Organization;
import com.vsm.business.domain.Rank;
import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.service.custom.sign.utils.SignUtils;
import com.vsm.business.utils.FileUtils;
import joptsimple.internal.Strings;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class TokenSignService {

    public final Logger log = LoggerFactory.getLogger(TokenSignService.class);

    private final String PDF_EXTENSION = "pdf";

    private final String PDF_FORMAT = "pdf";

    @Autowired
    private GraphService graphService;
    @Autowired
    private AttachmentFileRepository attachmentFileRepository;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private SignUtils signUtils;

    private boolean _sign(String base64File, String parentItemId, AttachmentFile attachmentFile, UserInfo userInfo, List<String> listFileName) throws IOException {
        byte[] data = Base64.decodeBase64(base64File);
        //byte[] data = java.util.Base64.getDecoder().decode(base64File); // chuyển fix báo lỗi khi chạy mvn verify

        String fileName = "";
        if(PDF_EXTENSION.equals(attachmentFile.getFileExtension())){        // nếu file là PDF -> update luôn file lên office 365
            if(!Strings.isNullOrEmpty(attachmentFile.getSignOfFile())){
                DriveItem driveItem = this.graphService.updateFile(attachmentFile.getItemId365(), data);
                attachmentFile.setFileSize(driveItem.size);
                if(userInfo != null){
                    attachmentFile.setModifiedName(userInfo.getFullName());
                    attachmentFile.setModified(userInfo);
                }
                attachmentFile.setModifiedDate(Instant.now());
                attachmentFileRepository.save(attachmentFile);
            }else{
                String[] names = attachmentFile.getFileName().split("\\.");
                names = Arrays.copyOfRange(names, 0, names.length-1);
                fileName = String.join("", names) + "_sign." + PDF_FORMAT;
                fileName = this.signUtils.getFileName(listFileName, fileName);

                LargeFileUploadResult<DriveItem> uploadResult = this.graphService.createFile(fileName, parentItemId, data);
                AttachmentFile attachmentFileNew = new AttachmentFile();
                BeanUtils.copyProperties(attachmentFile, attachmentFileNew);
                attachmentFileNew.setId(null);
                attachmentFileNew.setItemId365(uploadResult.responseBody.id);
                attachmentFileNew.setFileName(fileName);
                attachmentFileNew.setParentId(attachmentFile.getParentId());
                attachmentFileNew.setFileExtension(PDF_FORMAT);
                attachmentFileNew.setOfice365Path(uploadResult.responseBody.webUrl);
                attachmentFileNew.setFileSize(uploadResult.responseBody.size);
                attachmentFileNew.setContentType(uploadResult.responseBody.oDataType);
                attachmentFileNew.setIsActive(true);
                attachmentFileNew.setIsFolder(false);
                attachmentFileNew.setIsDelete(false);
                //attachmentFile.setDescription(this.objectMapper.writeValueAsString(uploadResult));

                //  update signOfFile   \\
                String fileKey = String.join("", names) + "_" + attachmentFile.getId();
                attachmentFileNew.setSignOfFile(fileKey);

                if(userInfo != null){
                    addUserInFo(attachmentFileNew, userInfo);
                }
                attachmentFileNew = resetOneToManyValue(attachmentFileNew);
                this.attachmentFileRepository.save(attachmentFileNew);
            }
        }else{                                                                  // nếu file ko là PDF -> tạo file mới dạng pdf lên office 365
                String[] names = attachmentFile.getFileName().split("\\.");
                names = Arrays.copyOfRange(names, 0, names.length-1);
                fileName = String.join("", names) + "." + PDF_FORMAT;
                fileName = this.signUtils.getFileName(listFileName, fileName);

                LargeFileUploadResult<DriveItem> uploadResult = this.graphService.createFile(fileName, parentItemId, data);
                AttachmentFile attachmentFileNew = new AttachmentFile();
                BeanUtils.copyProperties(attachmentFile, attachmentFileNew);
                attachmentFileNew.setId(null);
                attachmentFileNew.setItemId365(uploadResult.responseBody.id);
                attachmentFileNew.setFileName(fileName);
                attachmentFileNew.setParentId(attachmentFile.getParentId());
                attachmentFileNew.setFileExtension(PDF_FORMAT);
                attachmentFileNew.setOfice365Path(uploadResult.responseBody.webUrl);
                attachmentFileNew.setFileSize(uploadResult.responseBody.size);
                attachmentFileNew.setContentType(uploadResult.responseBody.oDataType);
                attachmentFileNew.setIsActive(true);
                attachmentFileNew.setIsFolder(false);
                attachmentFileNew.setIsDelete(false);
                //attachmentFile.setDescription(this.objectMapper.writeValueAsString(uploadResult));

                    //  update signOfFile   \\
                String fileKey = String.join("", names) + "_" + attachmentFile.getId();
                attachmentFileNew.setSignOfFile(fileKey);

                if(userInfo != null){
                    addUserInFo(attachmentFileNew, userInfo);
                }
                attachmentFileNew = resetOneToManyValue(attachmentFileNew);
                this.attachmentFileRepository.save(attachmentFileNew);
        }
        return true;
    }

    public boolean sign(String base64File, String parentItemId, AttachmentFile attachmentFile, UserInfo userInfo, List<String> listFileName){
        try {
            return this._sign(base64File, parentItemId, attachmentFile, userInfo, listFileName);
        } catch (IOException e) {
            return false;
        }
    }

    private AttachmentFile addUserInFo(AttachmentFile attachmentFile, UserInfo userInfo){
        if(userInfo != null){
            attachmentFile.setCreated(userInfo);
            attachmentFile.setCreatedName(userInfo.getFullName());
            attachmentFile.setCreatedDate(Instant.now());
            attachmentFile.setModified(userInfo);
            attachmentFile.setModifiedName(userInfo.getFullName());
            attachmentFile.setModifiedDate(Instant.now());
            attachmentFile.setCreatedOrgName(userInfo.getOrganizations().stream().findFirst().orElse(new Organization()).getOrganizationName());
            attachmentFile.setCreatedRankName(userInfo.getRanks().stream().findFirst().orElse(new Rank()).getRankName());
        }
        return attachmentFile;
    }

    private AttachmentFile resetOneToManyValue(AttachmentFile attachmentFile){
        if(attachmentFile != null){
            attachmentFile.setAttachmentPermisitions(new HashSet<>());
            attachmentFile.setChangeFileHistories(new HashSet<>());
        }
        return attachmentFile;
    }

}
