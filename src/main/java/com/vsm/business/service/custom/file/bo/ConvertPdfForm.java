package com.vsm.business.service.custom.file.bo;


import java.util.Objects;

public class ConvertPdfForm {
    String vbAttachmentId;

    public ConvertPdfForm() {
    }

    public ConvertPdfForm(String vbAttachmentId) {
        this.vbAttachmentId = vbAttachmentId;
    }

    public String getVbAttachmentId() {
        return vbAttachmentId;
    }

    public void setVbAttachmentId(String vbAttachmentId) {
        this.vbAttachmentId = vbAttachmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConvertPdfForm that = (ConvertPdfForm) o;
        return Objects.equals(vbAttachmentId, that.vbAttachmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vbAttachmentId);
    }

    @Override
    public String toString() {
        return "ConvertPdfForm{" +
            "vbAttachmentId='" + vbAttachmentId + '\'' +
            '}';
    }
}
