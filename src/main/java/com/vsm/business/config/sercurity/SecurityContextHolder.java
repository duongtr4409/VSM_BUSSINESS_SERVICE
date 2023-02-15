package com.vsm.business.config.sercurity;

public class SecurityContextHolder {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setSecurityInfo(String securityInfo){
        threadLocal.set(securityInfo);
    }

    public static String getSecurityInfo(){
        return threadLocal.get();
    }

    public static void clearSecurityInfo(){
        threadLocal.remove();
    }
}
