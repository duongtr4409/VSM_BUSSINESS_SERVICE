package com.vsm.business.service.custom.file.bo;

import java.util.List;

public class AddChuKySoForm {

    private List<SignatureInfo> signatureInfoList;
    private UploadOption uploadOption;

    public List<SignatureInfo> getSignatureInfoList() {
        return signatureInfoList;
    }

    public void setSignatureInfoList(List<SignatureInfo> signatureInfoList) {
        this.signatureInfoList = signatureInfoList;
    }

    public UploadOption getUploadOption() {
        return uploadOption;
    }

    public void setUploadOption(UploadOption uploadOption) {
        this.uploadOption = uploadOption;
    }
}
