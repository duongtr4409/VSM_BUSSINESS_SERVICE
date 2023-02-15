package com.vsm.business.service.custom.file.bo;

import java.util.Date;

public class AddBanHanhInfoForm {

    private String docCode;
    private String docNumber;
    private Date docDate;
    private String publisher;
    private UploadOption uploadOption;
    private String includeAttachDocumentId;

    public String getIncludeAttachDocumentId() {
        return includeAttachDocumentId;
    }

    public void setIncludeAttachDocumentId(String includeAttachDocumentId) {
        this.includeAttachDocumentId = includeAttachDocumentId;
    }

    public UploadOption getUploadOption() {
        return uploadOption;
    }

    public void setUploadOption(UploadOption uploadOption) {
        this.uploadOption = uploadOption;
    }

    public String getDocCode() {
        return docCode;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
