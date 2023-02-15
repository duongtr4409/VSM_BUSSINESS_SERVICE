package com.vsm.business.service.custom.file.bo.Office365;

public class Download365Option {
    private String contentType;     // content-type của file
    private boolean isPDF;          // có download dạng PDF hay không
    private String itemId;          // id của item trên fileservice
    private Long attachmentFileId;  // id của attahmentFile trong DB
    private boolean addQrCode;
    private boolean addWatermark;
    private boolean addHistory;

    private Long userId;            // id của người thực hiện xuất file

    public Download365Option() {
    }

    public Download365Option(String contentType, boolean isPDF, String itemId, Long attachmentFileId, boolean addQrCode, boolean addWatermark, boolean addHistory, Long userId) {
        this.contentType = contentType;
        this.isPDF = isPDF;
        this.itemId = itemId;
        this.attachmentFileId = attachmentFileId;
        this.addQrCode = addQrCode;
        this.addWatermark = addWatermark;
        this.addHistory = addHistory;
        this.userId = userId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean isPDF() {
        return isPDF;
    }

    public void setPDF(boolean PDF) {
        isPDF = PDF;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Long getAttachmentFileId() {
        return attachmentFileId;
    }

    public void setAttachmentFileId(Long attachmentFileId) {
        this.attachmentFileId = attachmentFileId;
    }

    public boolean isAddQrCode() {
        return addQrCode;
    }

    public void setAddQrCode(boolean addQrCode) {
        this.addQrCode = addQrCode;
    }

    public boolean isAddWatermark() {
        return addWatermark;
    }

    public void setAddWatermark(boolean addWatermark) {
        this.addWatermark = addWatermark;
    }

    public boolean isAddHistory() {
        return addHistory;
    }

    public void setAddHistory(boolean addHistory) {
        this.addHistory = addHistory;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Download365Option{" +
            "contentType='" + contentType + '\'' +
            ", isPDF=" + isPDF +
            ", itemId='" + itemId + '\'' +
            ", attachmentFileId=" + attachmentFileId +
            ", addQrCode=" + addQrCode +
            ", addWatermark=" + addWatermark +
            ", addHistory=" + addHistory +
            ", userId=" + userId +
            '}';
    }
}
