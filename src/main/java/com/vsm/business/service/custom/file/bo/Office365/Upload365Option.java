package com.vsm.business.service.custom.file.bo.Office365;

public class Upload365Option {
    private String fileName;
    private Long fileTypeId;
    private Long requestDataId;
    private Long userId;
    private Long templateFormId;
    private Long requestProcessHisId;
    //    private Long outgoingDocId;
//    private Long incomingDocId;
    private Long officialDispatchId;
    private Long stepProcessId;
    private Long parentId;
    private String parentItemId;

    private String pathInHandBook;

    public Upload365Option() {
    }

//    public Upload365Option(String fileName, Long fileTypeId, Long requestDataId, Long userId, Long templateFormId, Long requestProcessHisId, Long outgoingDocId, Long incomingDocId, Long stepProcessId, Long parentId, String prarentItemId) {
//        this.fileName = fileName;
//        this.fileTypeId = fileTypeId;
//        this.requestDataId = requestDataId;
//        this.userId = userId;
//        this.templateFormId = templateFormId;
//        this.requestProcessHisId = requestProcessHisId;
//        this.outgoingDocId = outgoingDocId;
//        this.incomingDocId = incomingDocId;
//        this.stepProcessId = stepProcessId;
//        this.parentId = parentId;
//        this.parentItemId = prarentItemId;
//    }


    public Upload365Option(String fileName, Long fileTypeId, Long requestDataId, Long userId, Long templateFormId, Long requestProcessHisId, Long officialDispatchId, Long stepProcessId, Long parentId, String parentItemId, String pathInHandBook) {
        this.fileName = fileName;
        this.fileTypeId = fileTypeId;
        this.requestDataId = requestDataId;
        this.userId = userId;
        this.templateFormId = templateFormId;
        this.requestProcessHisId = requestProcessHisId;
        this.officialDispatchId = officialDispatchId;
        this.stepProcessId = stepProcessId;
        this.parentId = parentId;
        this.parentItemId = parentItemId;
        this.pathInHandBook = pathInHandBook;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileTypeId() {
        return fileTypeId;
    }

    public void setFileTypeId(Long fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    public Long getRequestDataId() {
        return requestDataId;
    }

    public void setRequestDataId(Long requestDataId) {
        this.requestDataId = requestDataId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTemplateFormId() {
        return templateFormId;
    }

    public void setTemplateFormId(Long templateFormId) {
        this.templateFormId = templateFormId;
    }

    public Long getRequestProcessHisId() {
        return requestProcessHisId;
    }

    public void setRequestProcessHisId(Long requestProcessHisId) {
        this.requestProcessHisId = requestProcessHisId;
    }

//    public Long getOutgoingDocId() {
//        return outgoingDocId;
//    }
//
//    public void setOutgoingDocId(Long outgoingDocId) {
//        this.outgoingDocId = outgoingDocId;
//    }
//
//    public Long getIncomingDocId() {
//        return incomingDocId;
//    }
//
//    public void setIncomingDocId(Long incomingDocId) {
//        this.incomingDocId = incomingDocId;
//    }


    public Long getOfficialDispatchId() {
        return officialDispatchId;
    }

    public void setOfficialDispatchId(Long officialDispatchId) {
        this.officialDispatchId = officialDispatchId;
    }

    public Long getStepProcessId() {
        return stepProcessId;
    }

    public void setStepProcessId(Long stepProcessId) {
        this.stepProcessId = stepProcessId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentItemId() {
        return parentItemId;
    }

    public void setParentItemId(String parentItemId) {
        this.parentItemId = parentItemId;
    }

    public String getPathInHandBook() {
        return pathInHandBook;
    }

    public void setPathInHandBook(String pathInHandBook) {
        this.pathInHandBook = pathInHandBook;
    }

    @Override
    public String toString() {
        return "Upload365Option{" +
            "fileName='" + fileName + '\'' +
            ", fileTypeId=" + fileTypeId +
            ", requestDataId=" + requestDataId +
            ", userId=" + userId +
            ", templateFormId=" + templateFormId +
            ", requestProcessHisId=" + requestProcessHisId +
            ", officialDispatchId=" + officialDispatchId +
            ", stepProcessId=" + stepProcessId +
            ", parentId=" + parentId +
            ", parentItemId='" + parentItemId + '\'' +
            '}';
    }
}
