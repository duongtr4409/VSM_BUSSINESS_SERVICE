package com.vsm.business.service.authenicate;

import com.vsm.business.service.dto.UserInfoDTO;

public class JwtDTO {
    private String accessToken;
    private String refreshToken;
    private BasicUserInfoDTO userInfo;

    public JwtDTO(String accessToken, String refreshToken, BasicUserInfoDTO userInfo) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userInfo = userInfo;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public BasicUserInfoDTO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(BasicUserInfoDTO userInfo) {
        this.userInfo = userInfo;
    }
}
