package com.vsm.business.config.tennant;

public class TenantContextHolder {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setTenant(String tenantCode) {
        threadLocal.set(tenantCode);
    }

    public static String getTenant() {
        return threadLocal.get();
    }

    public static void clearTenant() {
        threadLocal.remove();
    }
}
