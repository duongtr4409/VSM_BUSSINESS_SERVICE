package com.vsm.business.common.ReponseMessage.ErrorMessage;

import com.vsm.business.common.AppConstant;

public class FailLoadMessage extends ErrorMessage {
    public FailLoadMessage(Object data) {
        super(AppConstant.Error.LOAD_FAIL, data);
    }
}
