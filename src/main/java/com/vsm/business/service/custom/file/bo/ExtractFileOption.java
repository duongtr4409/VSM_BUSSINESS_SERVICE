package com.vsm.business.service.custom.file.bo;

public class ExtractFileOption {

    private Boolean needEncrypt;
    private String attachmentId;
    private String vbAttachmentId;
    private String contentType;
    private String objectId;
    private String objectType;


    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Boolean getNeedEncrypt() {
        return needEncrypt;
    }

    public void setNeedEncrypt(Boolean needEncrypt) {
        this.needEncrypt = needEncrypt;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getVbAttachmentId() {
        return vbAttachmentId;
    }

    public void setVbAttachmentId(String vbAttachmentId) {
        this.vbAttachmentId = vbAttachmentId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
}
