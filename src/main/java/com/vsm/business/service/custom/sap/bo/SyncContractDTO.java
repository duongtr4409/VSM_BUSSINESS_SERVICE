package com.vsm.business.service.custom.sap.bo;

import java.util.HashMap;
import java.util.Map;

public class SyncContractDTO {

    private Map<String, Object> headers = new HashMap<>();
    private Object body;
    private String httpMethod;
    private String url;

    public SyncContractDTO() {
    }

    public SyncContractDTO(Map<String, Object> headers, Object body, String httpMethod, String url) {
        this.headers = headers;
        this.body = body;
        this.httpMethod = httpMethod;
        this.url = url;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "SyncContractDTO{" +
            "headers=" + headers +
            ", body=" + body +
            ", httpMethod='" + httpMethod + '\'' +
            ", url='" + url + '\'' +
            '}';
    }
}
