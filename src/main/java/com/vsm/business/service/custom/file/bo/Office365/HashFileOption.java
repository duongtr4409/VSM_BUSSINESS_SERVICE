package com.vsm.business.service.custom.file.bo.Office365;

public class HashFileOption {

    private String fileName;

    private Long requestData;

    private String base64Data;

    public HashFileOption() {
    }

    public HashFileOption(String fileName, Long requestData, String base64Data) {
        this.fileName = fileName;
        this.requestData = requestData;
        this.base64Data = base64Data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getRequestData() {
        return requestData;
    }

    public void setRequestData(Long requestData) {
        this.requestData = requestData;
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    @Override
    public String toString() {
        return "HashFileOption{" +
            "fileName='" + fileName + '\'' +
            ", requestData=" + requestData +
            ", base64Data='" + base64Data + '\'' +
            '}';
    }
}
