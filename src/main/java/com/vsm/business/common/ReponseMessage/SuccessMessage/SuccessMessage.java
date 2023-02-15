package com.vsm.business.common.ReponseMessage.SuccessMessage;

import com.vsm.business.common.ReponseMessage.ResponseMessage;

public class SuccessMessage extends ResponseMessage<Object> {
    public SuccessMessage(String message, Object data) {
        super(true, message, data);
    }
}
