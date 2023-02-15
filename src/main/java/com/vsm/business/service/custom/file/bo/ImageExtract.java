package com.vsm.business.service.custom.file.bo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ImageExtract implements Serializable {

    private String id;
    private String vbAttachmentId;
    private String attachmentMetadataId;
    private Long fileSize;
    private Long pageNumber;
    private String fileName;
    private String fileExtention;
    private String filePath;
    private String note;
    private Long isSigned;
    private Long isEncrypt;
    private String storageType;
    private String status;
    private Long isDelete;
    private String creatorId;
    private String creatorName;
    private Date createTime;
    private String updatorId;
    private String updatorName;
    private Date updateTime;
    private Long isEdited;
    private String tenantCode;

    public ImageExtract() {
    }

    public ImageExtract(String id, String vbAttachmentId, String attachmentMetadataId, Long fileSize, Long pageNumber, String fileName, String fileExtention, String filePath, String note, Long isSigned, Long isEncrypt, String storageType, String status, Long isDelete, String creatorId, String creatorName, Date createTime, String updatorId, String updatorName, Date updateTime, Long isEdited, String tenantCode) {
        this.id = id;
        this.vbAttachmentId = vbAttachmentId;
        this.attachmentMetadataId = attachmentMetadataId;
        this.fileSize = fileSize;
        this.pageNumber = pageNumber;
        this.fileName = fileName;
        this.fileExtention = fileExtention;
        this.filePath = filePath;
        this.note = note;
        this.isSigned = isSigned;
        this.isEncrypt = isEncrypt;
        this.storageType = storageType;
        this.status = status;
        this.isDelete = isDelete;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.createTime = createTime;
        this.updatorId = updatorId;
        this.updatorName = updatorName;
        this.updateTime = updateTime;
        this.isEdited = isEdited;
        this.tenantCode = tenantCode;
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

    public String getAttachmentMetadataId() {
        return attachmentMetadataId;
    }

    public void setAttachmentMetadataId(String attachmentMetadataId) {
        this.attachmentMetadataId = attachmentMetadataId;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
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

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public Long getIsEdited() {
        return isEdited;
    }

    public void setIsEdited(Long isEdited) {
        this.isEdited = isEdited;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageExtract that = (ImageExtract) o;
        return Objects.equals(id, that.id) && Objects.equals(vbAttachmentId, that.vbAttachmentId) && Objects.equals(attachmentMetadataId, that.attachmentMetadataId) && Objects.equals(fileSize, that.fileSize) && Objects.equals(pageNumber, that.pageNumber) && Objects.equals(fileName, that.fileName) && Objects.equals(fileExtention, that.fileExtention) && Objects.equals(filePath, that.filePath) && Objects.equals(note, that.note) && Objects.equals(isSigned, that.isSigned) && Objects.equals(isEncrypt, that.isEncrypt) && Objects.equals(storageType, that.storageType) && Objects.equals(status, that.status) && Objects.equals(isDelete, that.isDelete) && Objects.equals(creatorId, that.creatorId) && Objects.equals(creatorName, that.creatorName) && Objects.equals(createTime, that.createTime) && Objects.equals(updatorId, that.updatorId) && Objects.equals(updatorName, that.updatorName) && Objects.equals(updateTime, that.updateTime) && Objects.equals(isEdited, that.isEdited) && Objects.equals(tenantCode, that.tenantCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vbAttachmentId, attachmentMetadataId, fileSize, pageNumber, fileName, fileExtention, filePath, note, isSigned, isEncrypt, storageType, status, isDelete, creatorId, creatorName, createTime, updatorId, updatorName, updateTime, isEdited, tenantCode);
    }

    @Override
    public String toString() {
        return "ImageExtract{" +
            "id='" + id + '\'' +
            ", vbAttachmentId='" + vbAttachmentId + '\'' +
            ", attachmentMetadataId='" + attachmentMetadataId + '\'' +
            ", fileSize=" + fileSize +
            ", pageNumber=" + pageNumber +
            ", fileName='" + fileName + '\'' +
            ", fileExtention='" + fileExtention + '\'' +
            ", filePath='" + filePath + '\'' +
            ", note='" + note + '\'' +
            ", isSigned=" + isSigned +
            ", isEncrypt=" + isEncrypt +
            ", storageType='" + storageType + '\'' +
            ", status='" + status + '\'' +
            ", isDelete=" + isDelete +
            ", creatorId='" + creatorId + '\'' +
            ", creatorName='" + creatorName + '\'' +
            ", createTime=" + createTime +
            ", updatorId='" + updatorId + '\'' +
            ", updatorName='" + updatorName + '\'' +
            ", updateTime=" + updateTime +
            ", isEdited=" + isEdited +
            ", tenantCode='" + tenantCode + '\'' +
            '}';
    }
}
