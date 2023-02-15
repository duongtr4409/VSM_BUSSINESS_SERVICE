package com.vsm.business.common.ReponseMessage.ErrorMessage;

import com.vsm.business.common.AppConstant;

public class FailCreateMessage extends ErrorMessage {
    public FailCreateMessage(Object data) {
        super(AppConstant.Error.CREATE_FAIL, data);
    }

    public FailCreateMessage(String message, Object data) {
        super(message, data);
    }

    public static FailCreateMessage ExistId(Object data) {
        return new FailCreateMessage(AppConstant.Error.EXIST_ID, data);
    }
}
