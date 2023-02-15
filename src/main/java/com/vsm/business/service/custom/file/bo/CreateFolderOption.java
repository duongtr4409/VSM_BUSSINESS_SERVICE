package com.vsm.business.service.custom.file.bo;

import java.io.Serializable;

public class CreateFolderOption implements Serializable {



    private String fileName;
    private Boolean needEncrypt;
    private String contentType;
    private String fileBase64;
    private long isFolder;
    private String parentId;
//    private Long parentId;
    private Long createdId;

    public CreateFolderOption() {
    }

    public CreateFolderOption(String fileName, Boolean needEncrypt, String contentType, String fileBase64, long isFolder, String parentId, Long createdId) {
        this.fileName = fileName;
        this.needEncrypt = needEncrypt;
        this.contentType = contentType;
        this.fileBase64 = fileBase64;
        this.isFolder = isFolder;
        this.parentId = parentId;
        this.createdId = createdId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getNeedEncrypt() {
        return needEncrypt;
    }

    public void setNeedEncrypt(Boolean needEncrypt) {
        this.needEncrypt = needEncrypt;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileBase64() {
        return fileBase64;
    }

    public void setFileBase64(String fileBase64) {
        this.fileBase64 = fileBase64;
    }

    public long getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(long isFolder) {
        this.isFolder = isFolder;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Long getCreatedId() {
        return createdId;
    }

    public void setCreatedId(Long createdId) {
        this.createdId = createdId;
    }

    @Override
    public String toString() {
        return "CreateFolderOption{" +
            "fileName='" + fileName + '\'' +
            ", needEncrypt=" + needEncrypt +
            ", contentType='" + contentType + '\'' +
            ", fileBase64='" + fileBase64 + '\'' +
            ", isFolder=" + isFolder +
            ", parentItemId='" + parentId + '\'' +
            ", parentId=" + parentId +
            ", createdId=" + createdId +
            '}';
    }
}
