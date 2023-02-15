package com.vsm.business.common.ReponseMessage;

public abstract class ResponseMessage<T> implements IResponseMessage {
    boolean state = true;
    String message = "";
    private T data;

    public ResponseMessage(boolean state, String message, T data) {
        this.state = state;
        this.message = message;
        this.data = data;
    }

    public boolean getState() {
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
