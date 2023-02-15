package com.vsm.business.service.custom.file.bo;

import java.util.List;
import java.util.Objects;

public class UploadOption {

    private Boolean needEncrypt;
    private Boolean needConvertPdf;
    private String contentType;
    private String fileBase64;
    private List<String> imageFileIds;
    private List<String> imageExtractIds;
    private String originalFileId;
    private Long pageNumber;
    private String attachMetadataCommnetId;
    private String attachMetadataOriginalId;
    private List<String> imageExtractCommentIds;
    private long isFolder;
    private String parentId;
//    private Long parentId;
    private Long createdId;

    public UploadOption() {
    }

    public UploadOption(Boolean needEncrypt, Boolean needConvertPdf, String contentType, String fileBase64, List<String> imageFileIds, List<String> imageExtractIds, String originalFileId, Long pageNumber, String attachMetadataCommnetId, String attachMetadataOriginalId, List<String> imageExtractCommentIds, long isFolder, String parentId, Long createdId) {
        this.needEncrypt = needEncrypt;
        this.needConvertPdf = needConvertPdf;
        this.contentType = contentType;
        this.fileBase64 = fileBase64;
        this.imageFileIds = imageFileIds;
        this.imageExtractIds = imageExtractIds;
        this.originalFileId = originalFileId;
        this.pageNumber = pageNumber;
        this.attachMetadataCommnetId = attachMetadataCommnetId;
        this.attachMetadataOriginalId = attachMetadataOriginalId;
        this.imageExtractCommentIds = imageExtractCommentIds;
        this.isFolder = isFolder;
        this.parentId = parentId;
        this.createdId = createdId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UploadOption that = (UploadOption) o;
        return isFolder == that.isFolder && Objects.equals(needEncrypt, that.needEncrypt) && Objects.equals(needConvertPdf, that.needConvertPdf) && Objects.equals(contentType, that.contentType) && Objects.equals(fileBase64, that.fileBase64) && Objects.equals(imageFileIds, that.imageFileIds) && Objects.equals(imageExtractIds, that.imageExtractIds) && Objects.equals(originalFileId, that.originalFileId) && Objects.equals(pageNumber, that.pageNumber) && Objects.equals(attachMetadataCommnetId, that.attachMetadataCommnetId) && Objects.equals(attachMetadataOriginalId, that.attachMetadataOriginalId) && Objects.equals(imageExtractCommentIds, that.imageExtractCommentIds) && Objects.equals(parentId, that.parentId) && Objects.equals(createdId, that.createdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(needEncrypt, needConvertPdf, contentType, fileBase64, imageFileIds, imageExtractIds, originalFileId, pageNumber, attachMetadataCommnetId, attachMetadataOriginalId, imageExtractCommentIds, isFolder, parentId, createdId);
    }

    public Boolean getNeedEncrypt() {
        return needEncrypt;
    }

    public void setNeedEncrypt(Boolean needEncrypt) {
        this.needEncrypt = needEncrypt;
    }

    public Boolean getNeedConvertPdf() {
        return needConvertPdf;
    }

    public void setNeedConvertPdf(Boolean needConvertPdf) {
        this.needConvertPdf = needConvertPdf;
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

    public List<String> getImageFileIds() {
        return imageFileIds;
    }

    public void setImageFileIds(List<String> imageFileIds) {
        this.imageFileIds = imageFileIds;
    }

    public List<String> getImageExtractIds() {
        return imageExtractIds;
    }

    public void setImageExtractIds(List<String> imageExtractIds) {
        this.imageExtractIds = imageExtractIds;
    }

    public String getOriginalFileId() {
        return originalFileId;
    }

    public void setOriginalFileId(String originalFileId) {
        this.originalFileId = originalFileId;
    }

    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getAttachMetadataCommnetId() {
        return attachMetadataCommnetId;
    }

    public void setAttachMetadataCommnetId(String attachMetadataCommnetId) {
        this.attachMetadataCommnetId = attachMetadataCommnetId;
    }

    public String getAttachMetadataOriginalId() {
        return attachMetadataOriginalId;
    }

    public void setAttachMetadataOriginalId(String attachMetadataOriginalId) {
        this.attachMetadataOriginalId = attachMetadataOriginalId;
    }

    public List<String> getImageExtractCommentIds() {
        return imageExtractCommentIds;
    }

    public void setImageExtractCommentIds(List<String> imageExtractCommentIds) {
        this.imageExtractCommentIds = imageExtractCommentIds;
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
        return "UploadOption{" +
            "needEncrypt=" + needEncrypt +
            ", needConvertPdf=" + needConvertPdf +
            ", contentType='" + contentType + '\'' +
            ", fileBase64='" + fileBase64 + '\'' +
            ", imageFileIds=" + imageFileIds +
            ", imageExtractIds=" + imageExtractIds +
            ", originalFileId='" + originalFileId + '\'' +
            ", pageNumber=" + pageNumber +
            ", attachMetadataCommnetId='" + attachMetadataCommnetId + '\'' +
            ", attachMetadataOriginalId='" + attachMetadataOriginalId + '\'' +
            ", imageExtractCommentIds=" + imageExtractCommentIds +
            ", isFolder=" + isFolder +
            ", parentItemId='" + parentId + '\'' +
            ", parentId=" + parentId +
            ", createdId=" + createdId +
            '}';
    }


}
