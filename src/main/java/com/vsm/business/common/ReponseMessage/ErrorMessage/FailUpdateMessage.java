package com.vsm.business.common.ReponseMessage.ErrorMessage;

import com.vsm.business.common.AppConstant;

public class FailUpdateMessage extends ErrorMessage {
    public FailUpdateMessage(Object data) {
        super(AppConstant.Error.UPDATE_FAIL, data);
    }

    public FailUpdateMessage(String message, Object data) {
        super(message, data);
    }

    public static FailUpdateMessage MissingParameter(Object data) {
        return new FailUpdateMessage(AppConstant.Error.MISSING_PARAMETER, data);
    }

    public static FailUpdateMessage MatchingFail(Object data) {
        return new FailUpdateMessage(AppConstant.Error.MATCHING_FAIL, data);
    }

    public static FailUpdateMessage DidNotExist(Object data){
        return new FailUpdateMessage(AppConstant.Error.DID_NOT_EXIST, data);
    }
}
