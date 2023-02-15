package com.vsm.business.service.custom.mail.bo.response;

public class MailServiceResposeDTO {
    private boolean state = true;
    private String message = "";
    private Object data = new Object();

    public MailServiceResposeDTO(boolean state, String message, Object data) {
        this.state = state;
        this.message = message;
        this.data = data;
    }

    public MailServiceResposeDTO() {
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MailServiceResposeDTO{" +
            "state=" + state +
            ", message='" + message + '\'' +
            ", data=" + data +
            '}';
    }
}
