package com.vsm.business.service.custom.processRequest.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class CustomerApproveOption {

    public enum Action {
        Agree,              // đồng ý
        Reject,             // trả lại
    }

    private String requestData;         // id của phiếu đang thực hiện hành động dạng hash
    private Action action;              // loại hành động
    private String reason;
    private String successMessageReturn;       // Message phản hồi khi thực hiện thành công
    private String errorMessageReturn;         // Message phản hồi khi thực thực hiện thất bại
    private Long statusId;
    @JsonIgnore
    private String otp;                 // otp
    @JsonIgnore
    private Long requestDataId;          // id của phiếu đang thực hiện hành động

    public CustomerApproveOption() {
    }

    public CustomerApproveOption(String requestData, Action action, String reason, String successMessageReturn, String errorMessageReturn, Long statusI, String otp, Long requestDataId) {
        this.requestData = requestData;
        this.action = action;
        this.reason = reason;
        this.successMessageReturn = successMessageReturn;
        this.errorMessageReturn = errorMessageReturn;
        this.statusId = statusId;
        this.otp = otp;
        this.requestDataId = requestDataId;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
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

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Long getRequestDataId() {
        return requestDataId;
    }

    public void setRequestDataId(Long requestDataId) {
        this.requestDataId = requestDataId;
    }

    @Override
    public String toString() {
        return "CustomerApproveOption{" +
            "requestDataId='" + requestDataId + '\'' +
            ", action=" + action +
            ", reason='" + reason + '\'' +
            ", successMessageReturn='" + successMessageReturn + '\'' +
            ", errorMessageReturn='" + errorMessageReturn + '\'' +
            '}';
    }
}
