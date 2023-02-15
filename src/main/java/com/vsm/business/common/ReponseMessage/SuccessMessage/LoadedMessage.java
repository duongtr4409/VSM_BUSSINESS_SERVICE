package com.vsm.business.common.ReponseMessage.SuccessMessage;

import com.vsm.business.common.AppConstant;

import java.io.Serializable;

public class LoadedMessage extends SuccessMessage implements Serializable {
    public LoadedMessage(Object data) {
        super(AppConstant.Message.LOADED, data);
    }
}
