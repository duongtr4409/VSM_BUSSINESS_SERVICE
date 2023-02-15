package com.vsm.business.service.custom.file.bo;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class AttachDocument implements Serializable {

    private String id;
    private String vbAttachmentId;
    private String fileName;
    private String fileCode;
    private String fileType;
    private Long fileSize;
    private Long isEncrypt;
    private Long isPdf;
    private String pdfAttachmentDocumentId;
    private Long isOriginal;
    private String description;
    private String filePath;
    private String contentType;
    private Long status;
    private Long isDelete;
    private String creatorId;
    private String creatorName;
    private Date createTime;
    private String updatorId;
    private String subFolder;
    private String updatorName;
    private Date updateTime;
    private String tenantCode;
    private String parentId;
    private int isFolder;

    public AttachDocument() {
    }

    public AttachDocument(String id, String vbAttachmentId, String fileName, String fileCode, String fileType, Long fileSize, Long isEncrypt, Long isPdf, String pdfAttachmentDocumentId, Long isOriginal, String description, String filePath, String contentType, Long status, Long isDelete, String creatorId, String creatorName, Date createTime, String updatorId, String subFolder, String updatorName, Date updateTime, String tenantCode, String parentId, int isFolder) {
        this.id = id;
        this.vbAttachmentId = vbAttachmentId;
        this.fileName = fileName;
        this.fileCode = fileCode;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.isEncrypt = isEncrypt;
        this.isPdf = isPdf;
        this.pdfAttachmentDocumentId = pdfAttachmentDocumentId;
        this.isOriginal = isOriginal;
        this.description = description;
        this.filePath = filePath;
        this.contentType = contentType;
        this.status = status;
        this.isDelete = isDelete;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.createTime = createTime;
        this.updatorId = updatorId;
        this.subFolder = subFolder;
        this.updatorName = updatorName;
        this.updateTime = updateTime;
        this.tenantCode = tenantCode;
        this.parentId = parentId;
        this.isFolder = isFolder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVbAttachmentId() {
        return vbAttachmentId;
    }

    public void setVbAttachmentId(String vbAttachmentId) {
        this.vbAttachmentId = vbAttachmentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getIsEncrypt() {
        return isEncrypt;
    }

    public void setIsEncrypt(Long isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

    public Long getIsPdf() {
        return isPdf;
    }

    public void setIsPdf(Long isPdf) {
        this.isPdf = isPdf;
    }

    public String getPdfAttachmentDocumentId() {
        return pdfAttachmentDocumentId;
    }

    public void setPdfAttachmentDocumentId(String pdfAttachmentDocumentId) {
        this.pdfAttachmentDocumentId = pdfAttachmentDocumentId;
    }

    public Long getIsOriginal() {
        return isOriginal;
    }

    public void setIsOriginal(Long isOriginal) {
        this.isOriginal = isOriginal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
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

    public String getSubFolder() {
        return subFolder;
    }

    public void setSubFolder(String subFolder) {
        this.subFolder = subFolder;
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

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(int isFolder) {
        this.isFolder = isFolder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachDocument that = (AttachDocument) o;
        return isFolder == that.isFolder && Objects.equals(id, that.id) && Objects.equals(vbAttachmentId, that.vbAttachmentId) && Objects.equals(fileName, that.fileName) && Objects.equals(fileCode, that.fileCode) && Objects.equals(fileType, that.fileType) && Objects.equals(fileSize, that.fileSize) && Objects.equals(isEncrypt, that.isEncrypt) && Objects.equals(isPdf, that.isPdf) && Objects.equals(pdfAttachmentDocumentId, that.pdfAttachmentDocumentId) && Objects.equals(isOriginal, that.isOriginal) && Objects.equals(description, that.description) && Objects.equals(filePath, that.filePath) && Objects.equals(contentType, that.contentType) && Objects.equals(status, that.status) && Objects.equals(isDelete, that.isDelete) && Objects.equals(creatorId, that.creatorId) && Objects.equals(creatorName, that.creatorName) && Objects.equals(createTime, that.createTime) && Objects.equals(updatorId, that.updatorId) && Objects.equals(subFolder, that.subFolder) && Objects.equals(updatorName, that.updatorName) && Objects.equals(updateTime, that.updateTime) && Objects.equals(tenantCode, that.tenantCode) && Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vbAttachmentId, fileName, fileCode, fileType, fileSize, isEncrypt, isPdf, pdfAttachmentDocumentId, isOriginal, description, filePath, contentType, status, isDelete, creatorId, creatorName, createTime, updatorId, subFolder, updatorName, updateTime, tenantCode, parentId, isFolder);
    }

    @Override
    public String toString() {
        return "AttachDocument{" +
            "id='" + id + '\'' +
            ", vbAttachmentId='" + vbAttachmentId + '\'' +
            ", fileName='" + fileName + '\'' +
            ", fileCode='" + fileCode + '\'' +
            ", fileType='" + fileType + '\'' +
            ", fileSize=" + fileSize +
            ", isEncrypt=" + isEncrypt +
            ", isPdf=" + isPdf +
            ", pdfAttachmentDocumentId='" + pdfAttachmentDocumentId + '\'' +
            ", isOriginal=" + isOriginal +
            ", description='" + description + '\'' +
            ", filePath='" + filePath + '\'' +
            ", contentType='" + contentType + '\'' +
            ", status=" + status +
            ", isDelete=" + isDelete +
            ", creatorId='" + creatorId + '\'' +
            ", creatorName='" + creatorName + '\'' +
            ", createTime=" + createTime +
            ", updatorId='" + updatorId + '\'' +
            ", subFolder='" + subFolder + '\'' +
            ", updatorName='" + updatorName + '\'' +
            ", updateTime=" + updateTime +
            ", tenantCode='" + tenantCode + '\'' +
            ", parentId='" + parentId + '\'' +
            ", isFolder=" + isFolder +
            '}';
    }
}
