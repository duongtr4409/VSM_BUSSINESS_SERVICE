package com.vsm.business.common.ReponseMessage;

public class BaseResponseMessage extends ResponseMessage<Object>{
    public BaseResponseMessage(boolean state, String message, Object data) {
        super(state, message, data);
    }
}
