package com.vsm.business.service.authenicate;

import java.io.Serializable;

public class OAuthTokenDTO implements Serializable {
    private String token;
    private OAuthDTO oAuthDTO;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public OAuthDTO getoAuthDTO() {
        return oAuthDTO;
    }

    public void setoAuthDTO(OAuthDTO oAuthDTO) {
        this.oAuthDTO = oAuthDTO;
    }
}
