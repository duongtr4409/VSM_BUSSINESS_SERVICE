package com.vsm.business.common.ReponseMessage.SuccessMessage;

import com.vsm.business.common.AppConstant;

public class CreatedMessage extends SuccessMessage{
    public CreatedMessage(Object data) {
        super(AppConstant.Message.CREATED, data);
    }
}
