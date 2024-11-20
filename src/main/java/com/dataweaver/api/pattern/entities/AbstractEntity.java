package com.dataweaver.api.pattern.entities;

import com.dataweaver.api.service.AbstractService;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable {

    public abstract String getPortugueseClassName();

    public Class<? extends AbstractService> getServiceClass() {
        return null;
    }

    public String getObjectName() {
        return "";
    }

    public String getSchema() {
        return "public";
    }

}
