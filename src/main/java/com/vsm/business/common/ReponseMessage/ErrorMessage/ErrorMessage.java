package com.vsm.business.common.ReponseMessage.ErrorMessage;

import com.vsm.business.common.ReponseMessage.ResponseMessage;

public class ErrorMessage extends ResponseMessage<Object> {
    public ErrorMessage(String message, Object data) {
        super(false, message, data);
    }
}
