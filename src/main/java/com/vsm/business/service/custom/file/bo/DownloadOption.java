package com.vsm.business.service.custom.file.bo;

public class DownloadOption {
    private String type;
    private String waterMarkText;
    private String vbAttachmentId;
    private String attachmentMetadataId;
    private Long pageNumber;
    private String imageStatus;
    private String includeAttachDocumentId;
    private boolean addQrCode;
    private boolean addHistory;
    private boolean addWaterMark;

    public String getAttachmentMetadataId() {
        return attachmentMetadataId;
    }

    public void setAttachmentMetadataId(String attachmentMetadataId) {
        this.attachmentMetadataId = attachmentMetadataId;
    }

    public String getVbAttachmentId() {
        return vbAttachmentId;
    }

    public void setVbAttachmentId(String vbAttachmentId) {
        this.vbAttachmentId = vbAttachmentId;
    }

    public String getWaterMarkText() {
        return waterMarkText;
    }

    public void setWaterMarkText(String waterMarkText) {
        this.waterMarkText = waterMarkText;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
    }

    public String getIncludeAttachDocumentId() {
        return includeAttachDocumentId;
    }

    public void setIncludeAttachDocumentId(String includeAttachDocumentId) {
        this.includeAttachDocumentId = includeAttachDocumentId;
    }

    public boolean getAddQrCode() {
        return addQrCode;
    }

    public void setAddQrCode(boolean addQrCode) {
        this.addQrCode = addQrCode;
    }

    public boolean getAddHistory() {
        return addHistory;
    }

    public void setAddHistory(boolean addHistory) {
        this.addHistory = addHistory;
    }

    public boolean getAddWaterMark() {
        return addWaterMark;
    }

    public void setAddWaterMark(boolean addWaterMark) {
        this.addWaterMark = addWaterMark;
    }
}
