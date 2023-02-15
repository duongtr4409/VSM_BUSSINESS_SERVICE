package com.vsm.business.service.authenicate;

import java.io.Serializable;

public class OAuthDTO implements Serializable {
    private String username;
    private String name;
    private String localAccountId;
    private String tenantId;
    private String nativeAccountId;
    private String environment;
    private String homeAccountId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalAccountId() {
        return localAccountId;
    }

    public void setLocalAccountId(String localAccountId) {
        this.localAccountId = localAccountId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getNativeAccountId() {
        return nativeAccountId;
    }

    public void setNativeAccountId(String nativeAccountId) {
        this.nativeAccountId = nativeAccountId;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getHomeAccountId() {
        return homeAccountId;
    }

    public void setHomeAccountId(String homeAccountId) {
        this.homeAccountId = homeAccountId;
    }
}
