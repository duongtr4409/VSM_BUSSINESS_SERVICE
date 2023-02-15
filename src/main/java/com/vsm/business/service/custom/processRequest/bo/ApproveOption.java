package com.vsm.business.service.custom.processRequest.bo;

import com.vsm.business.domain.AttachmentFile;

import java.util.List;
import java.util.Objects;

public class ApproveOption {

    public enum Action {
        Agree,              // đồng ý
        Refuse,             // từ chối
        Approve,            // phê duyệt
        Reject,             // trả lại
        Send,               // gửi phiếu yêu cầu
        CancelApprove,      // hủy phê duyệt
        Recall,             // Thu hồi
        CreaterReall        // Người tạo phiếu yêu cầu thu hồi
    }

    private Long requestDataId;         // id của phiếu đang thực hiện hành động
    private Long userId;                // id của người thực hiện hành động
    private Action action;              // loại hành động
    private String reason;
    private List<Long> attachmentFileId;
    private String successMessageReturn;       // Message phản hồi khi thực hiện thành công
    private String errorMessageReturn;         // Message phản hồi khi thực thực hiện thất bại
    private Long statusId;

    public ApproveOption() {
    }

    public ApproveOption(Long requestDataId, Long userId, Action action, String reason, List<Long> attachmentFileId, String successMessageReturn, String errorMessageReturn, Long statusId) {
        this.requestDataId = requestDataId;
        this.userId = userId;
        this.action = action;
        this.reason = reason;
        this.attachmentFileId = attachmentFileId;
        this.successMessageReturn = successMessageReturn;
        this.errorMessageReturn = errorMessageReturn;
        this.statusId = statusId;
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

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
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

    @Override
    public String toString() {
        return "ApproveOption{" +
            "requestDataId=" + requestDataId +
            ", userId=" + userId +
            ", action=" + action +
            ", reason='" + reason + '\'' +
            ", attachmentFileId=" + attachmentFileId +
            ", successMessageReturn='" + successMessageReturn + '\'' +
            ", errorMessageReturn='" + errorMessageReturn + '\'' +
            ", statusId='" + statusId + '\'' +
            '}';
    }
}
