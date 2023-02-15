package com.vsm.business.common.ReponseMessage.SuccessMessage;

import com.vsm.business.common.AppConstant;

public class DeletedMessage extends SuccessMessage{
    public DeletedMessage(Object data) {
        super(AppConstant.Message.DELETED, data);
    }
}
