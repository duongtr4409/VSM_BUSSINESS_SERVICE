package com.vsm.business.service.custom.processRequest.bo;

import com.vsm.business.service.dto.SignDataDTO;

import java.util.List;

public class ReSendOption {

    private Long requestDataId;
    private Long userId;
    private List<SignDataDTO> signDataList;
    private String reason;

    public ReSendOption() {
    }

    public ReSendOption(Long requestDataId, Long userId, List<SignDataDTO> signDataList, String reason) {
        this.requestDataId = requestDataId;
        this.userId = userId;
        this.signDataList = signDataList;
        this.reason = reason;
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

    public List<SignDataDTO> getSignDataList() {
        return signDataList;
    }

    public void setSignDataList(List<SignDataDTO> signDataList) {
        this.signDataList = signDataList;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "ReSendOption{" +
            "requestDataId=" + requestDataId +
            ", userId=" + userId +
            ", signDataList=" + signDataList +
            ", reason='" + reason + '\'' +
            '}';
    }
}
