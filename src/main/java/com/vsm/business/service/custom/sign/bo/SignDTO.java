package com.vsm.business.service.custom.sign.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SignDTO {

    public enum SignType {
        Soft,               // ký mềm
        Sim,                // ký sim
        Token,              // ký token
    }

    private Long userId;
    private String reason;

    public List<Long> requestDataList;
    private String location;    // ký mềm
    private String password;    // ký mềm
    private SignType signType;  // ký mềm
    @JsonProperty(defaultValue = "")
    private String symbol;      // ký mềm

    private String msisdn;       // ký sim

    @JsonProperty(defaultValue = "Eoffice")
    private String prompt;       // ký sim

    private String fileDataBase64;      // ký token

    private Long attachmentFileId;      // ký token

    private Long requestDataId;         // ký token


    public SignDTO() {
    }

    public SignDTO(Long userId, String reason, List<Long> requestDataList, String location, String password, SignType signType, String symbol, String msisdn, String prompt, String fileDataBase64, Long attachmentFileId, Long requestDataId) {
        this.userId = userId;
        this.reason = reason;
        this.requestDataList = requestDataList;
        this.location = location;
        this.password = password;
        this.signType = signType;
        this.symbol = symbol;
        this.msisdn = msisdn;
        this.prompt = prompt;
        this.fileDataBase64 = fileDataBase64;
        this.attachmentFileId = attachmentFileId;
        this.requestDataId = requestDataId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Long> getRequestDataList() {
        return requestDataList;
    }

    public void setRequestDataList(List<Long> requestDataList) {
        this.requestDataList = requestDataList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SignType getSignType() {
        return signType;
    }

    public void setSignType(SignType signType) {
        this.signType = signType;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getFileDataBase64() {
        return fileDataBase64;
    }

    public void setFileDataBase64(String fileDataBase64) {
        this.fileDataBase64 = fileDataBase64;
    }

    public Long getAttachmentFileId() {
        return attachmentFileId;
    }

    public void setAttachmentFileId(Long attachmentFileId) {
        this.attachmentFileId = attachmentFileId;
    }

    public Long getRequestDataId() {
        return requestDataId;
    }

    public void setRequestDataId(Long requestDataId) {
        this.requestDataId = requestDataId;
    }

    @Override
    public String toString() {
        return "SignDTO{" +
            "userId=" + userId +
            ", reason='" + reason + '\'' +
            ", requestDataList=" + requestDataList +
            ", location='" + location + '\'' +
            ", password='" + password + '\'' +
            ", signType=" + signType +
            ", symbol='" + symbol + '\'' +
            ", msisdn='" + msisdn + '\'' +
            ", prompt='" + prompt + '\'' +
            ", fileDataBase64='" + fileDataBase64 + '\'' +
            ", attachmentFileId=" + attachmentFileId +
            ", requestDataId=" + requestDataId +
            '}';
    }
}
