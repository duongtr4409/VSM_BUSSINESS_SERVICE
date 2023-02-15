package com.vsm.business.common.ReponseMessage.SuccessMessage;

import com.vsm.business.common.AppConstant;

public class UpdatedMessage extends SuccessMessage{
    public UpdatedMessage(Object data) {
        super(AppConstant.Message.UPDATED, data);
    }
}
