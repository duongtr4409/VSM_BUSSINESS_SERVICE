package com.vsm.business.service.custom.sign.bo;

public class SignResponseDTO {
    private Long requestDataId;
    private boolean resultSign;

    public SignResponseDTO() {
    }

    public SignResponseDTO(Long requestDataId, boolean resultSign) {
        this.requestDataId = requestDataId;
        this.resultSign = resultSign;
    }

    public Long getRequestDataId() {
        return requestDataId;
    }

    public void setRequestDataId(Long requestDataId) {
        this.requestDataId = requestDataId;
    }

    public boolean isResultSign() {
        return resultSign;
    }

    public void setResultSign(boolean resultSign) {
        this.resultSign = resultSign;
    }
}
