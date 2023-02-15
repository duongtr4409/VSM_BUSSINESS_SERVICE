package com.vsm.business.common.ReponseMessage.ErrorMessage;

import com.vsm.business.common.AppConstant;

public class FailDeleteMessage extends ErrorMessage{
    public FailDeleteMessage(Object data) {
        super(AppConstant.Error.DELETE_FAIL, data);
    }
}
