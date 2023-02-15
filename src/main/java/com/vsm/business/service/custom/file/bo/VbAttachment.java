package com.vsm.business.service.custom.file.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class VbAttachment implements Serializable {

    private String id;
    private String objectId;
    private Long objectType;
    private String fileName;
    private String fileExtention;
    private String filePath;
    private String note;
    private Long isSigned;
    private Long isEncrypt;
    private Long isDelete;
    private String creatorId;
    private String creatorName;
    private Date createTime;
    private String updatorId;
    private String updatorName;
    private Date updateTime;
    private String signedDocId;
    private String fileServiceId;
    private String storageType;
    private String contentType;
    private Long pdfAlready;
    private String tenantCode;

    public VbAttachment() {
    }

    public VbAttachment(String id, String objectId, Long objectType, String fileName, String fileExtention, String filePath, String note, Long isSigned, Long isEncrypt, Long isDelete, String creatorId, String creatorName, Date createTime, String updatorId, String updatorName, Date updateTime, String signedDocId, String fileServiceId, String storageType, String contentType, Long pdfAlready, String tenantCode) {
        this.id = id;
        this.objectId = objectId;
        this.objectType = objectType;
        this.fileName = fileName;
        this.fileExtention = fileExtention;
        this.filePath = filePath;
        this.note = note;
        this.isSigned = isSigned;
        this.isEncrypt = isEncrypt;
        this.isDelete = isDelete;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.createTime = createTime;
        this.updatorId = updatorId;
        this.updatorName = updatorName;
        this.updateTime = updateTime;
        this.signedDocId = signedDocId;
        this.fileServiceId = fileServiceId;
        this.storageType = storageType;
        this.contentType = contentType;
        this.pdfAlready = pdfAlready;
        this.tenantCode = tenantCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VbAttachment that = (VbAttachment) o;
        return Objects.equals(id, that.id) && Objects.equals(objectId, that.objectId) && Objects.equals(objectType, that.objectType) && Objects.equals(fileName, that.fileName) && Objects.equals(fileExtention, that.fileExtention) && Objects.equals(filePath, that.filePath) && Objects.equals(note, that.note) && Objects.equals(isSigned, that.isSigned) && Objects.equals(isEncrypt, that.isEncrypt) && Objects.equals(isDelete, that.isDelete) && Objects.equals(creatorId, that.creatorId) && Objects.equals(creatorName, that.creatorName) && Objects.equals(createTime, that.createTime) && Objects.equals(updatorId, that.updatorId) && Objects.equals(updatorName, that.updatorName) && Objects.equals(updateTime, that.updateTime) && Objects.equals(signedDocId, that.signedDocId) && Objects.equals(fileServiceId, that.fileServiceId) && Objects.equals(storageType, that.storageType) && Objects.equals(contentType, that.contentType) && Objects.equals(pdfAlready, that.pdfAlready) && Objects.equals(tenantCode, that.tenantCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, objectId, objectType, fileName, fileExtention, filePath, note, isSigned, isEncrypt, isDelete, creatorId, creatorName, createTime, updatorId, updatorName, updateTime, signedDocId, fileServiceId, storageType, contentType, pdfAlready, tenantCode);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Long getObjectType() {
        return objectType;
    }

    public void setObjectType(Long objectType) {
        this.objectType = objectType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtention() {
        return fileExtention;
    }

    public void setFileExtention(String fileExtention) {
        this.fileExtention = fileExtention;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getIsSigned() {
        return isSigned;
    }

    public void setIsSigned(Long isSigned) {
        this.isSigned = isSigned;
    }

    public Long getIsEncrypt() {
        return isEncrypt;
    }

    public void setIsEncrypt(Long isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

    public Long getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Long isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSignedDocId() {
        return signedDocId;
    }

    public void setSignedDocId(String signedDocId) {
        this.signedDocId = signedDocId;
    }

    public String getFileServiceId() {
        return fileServiceId;
    }

    public void setFileServiceId(String fileServiceId) {
        this.fileServiceId = fileServiceId;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getPdfAlready() {
        return pdfAlready;
    }

    public void setPdfAlready(Long pdfAlready) {
        this.pdfAlready = pdfAlready;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    @Override
    public String toString() {
        return "VbAttachment{" +
            "id='" + id + '\'' +
            ", objectId='" + objectId + '\'' +
            ", objectType=" + objectType +
            ", fileName='" + fileName + '\'' +
            ", fileExtention='" + fileExtention + '\'' +
            ", filePath='" + filePath + '\'' +
            ", note='" + note + '\'' +
            ", isSigned=" + isSigned +
            ", isEncrypt=" + isEncrypt +
            ", isDelete=" + isDelete +
            ", creatorId='" + creatorId + '\'' +
            ", creatorName='" + creatorName + '\'' +
            ", createTime=" + createTime +
            ", updatorId='" + updatorId + '\'' +
            ", updatorName='" + updatorName + '\'' +
            ", updateTime=" + updateTime +
            ", signedDocId='" + signedDocId + '\'' +
            ", fileServiceId='" + fileServiceId + '\'' +
            ", storageType='" + storageType + '\'' +
            ", contentType='" + contentType + '\'' +
            ", pdfAlready=" + pdfAlready +
            ", tenantCode='" + tenantCode + '\'' +
            '}';
    }
}
