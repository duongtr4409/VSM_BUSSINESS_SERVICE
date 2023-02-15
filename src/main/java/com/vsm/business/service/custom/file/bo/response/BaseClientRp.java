package com.vsm.business.service.custom.file.bo.response;

public class BaseClientRp<T> {

    String code;
    Boolean state;
    String message;
    T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseClientRp{" +
                "code='" + code + '\'' +
                ", state='" + state + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}

