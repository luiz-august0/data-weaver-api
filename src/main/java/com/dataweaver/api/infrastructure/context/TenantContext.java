package com.dataweaver.api.infrastructure.context;

import com.dataweaver.api.utils.StringUtil;

public class TenantContext {

    public final static String DEFAULT_TENANT = "public";

    private static final ThreadLocal<String> currentTenant = ThreadLocal.withInitial(() -> DEFAULT_TENANT);

    public static void setCurrentTenant(String tenant) {
        if (StringUtil.isNotNullOrEmpty(tenant)) {
            currentTenant.set(tenant);
        } else {
            currentTenant.set(DEFAULT_TENANT);
        }
    }

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove();
    }

}