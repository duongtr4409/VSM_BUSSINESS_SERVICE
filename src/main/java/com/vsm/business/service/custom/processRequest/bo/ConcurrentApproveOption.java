package com.vsm.business.service.custom.processRequest.bo;

import java.util.List;

public class ConcurrentApproveOption {

    private List<Long> listRequestDataId;         // id của phiếu đang thực hiện hành động
    private Long userId;                // id của người thực hiện hành động
    private ApproveOption.Action action;              // lại hành động
    private String reason;
    private List<Long> attachmentFileId;
    private String successMessageReturn;       // Message phản hồi khi thực hiện thành công
    private String errorMessageReturn;         // Message phản hồi khi thực thực hiện thất bại
    private Long statusId;

    public ConcurrentApproveOption() {
    }

    public ConcurrentApproveOption(List<Long> listRequestDataId, Long userId, ApproveOption.Action action, String reason, List<Long> attachmentFileId, String successMessageReturn, String errorMessageReturn, Long statusId) {
        this.listRequestDataId = listRequestDataId;
        this.userId = userId;
        this.action = action;
        this.reason = reason;
        this.attachmentFileId = attachmentFileId;
        this.successMessageReturn = successMessageReturn;
        this.errorMessageReturn = errorMessageReturn;
        this.statusId = statusId;
    }

    public List<Long> getListRequestDataId() {
        return listRequestDataId;
    }

    public void setListRequestDataId(List<Long> listRequestDataId) {
        this.listRequestDataId = listRequestDataId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ApproveOption.Action getAction() {
        return action;
    }

    public void setAction(ApproveOption.Action action) {
        this.action = action;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Long> getAttachmentFileId() {
        return attachmentFileId;
    }

    public void setAttachmentFileId(List<Long> attachmentFileId) {
        this.attachmentFileId = attachmentFileId;
    }

    public String getSuccessMessageReturn() {
        return successMessageReturn;
    }

    public void setSuccessMessageReturn(String successMessageReturn) {
        this.successMessageReturn = successMessageReturn;
    }

    public String getErrorMessageReturn() {
        return errorMessageReturn;
    }

    public void setErrorMessageReturn(String errorMessageReturn) {
        this.errorMessageReturn = errorMessageReturn;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}
