package com.vsm.business.service.custom.file.bo;

import java.util.List;

public class ImageUploadOption {
    private String attachMetadataCommnetId;
    private String attachMetadataOriginalId;
    private List<String> imageExtractCommentIds;

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
}
